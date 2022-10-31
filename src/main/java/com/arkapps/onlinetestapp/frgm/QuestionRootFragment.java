package com.arkapps.onlinetestapp.frgm;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arkapps.onlinetestapp.MainActivity;
import com.arkapps.onlinetestapp.MyApplication;
import com.arkapps.onlinetestapp.R;
import com.arkapps.onlinetestapp.adp.QuestionAdapter;
import com.arkapps.onlinetestapp.cls.QuestionData;
import com.arkapps.onlinetestapp.cls.QuizData;
import com.arkapps.onlinetestapp.cls.ResultData;
import com.arkapps.onlinetestapp.databinding.FragmentQuestionRootBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class QuestionRootFragment extends Fragment implements QuestionAdapter.OnOptionClickListener {

    private FragmentQuestionRootBinding binding;
    private QuizData quizData;
    private QuestionAdapter adapter;
    private ArrayList<QuestionData> arrayList;
    private int userTime = 0;

    public QuestionRootFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQuestionRootBinding.inflate(inflater, container, false);
        quizData = QuestionRootFragmentArgs.fromBundle(getArguments()).getQuizData();
        init();
        loadQuestionData(quizData.getId());

        binding.submit.setOnClickListener(v -> {
            saveResult();
        });

        binding.next.setOnClickListener(v -> {

            if (binding.viewPager.getCurrentItem() < arrayList.size() - 1) {
                int position = binding.viewPager.getCurrentItem() + 1;
                binding.viewPager.setCurrentItem(position);
            } else {
                Toast.makeText(getContext(), "Can not go next", Toast.LENGTH_SHORT).show();
            }
        });

        binding.previous.setOnClickListener(v -> {
            if (binding.viewPager.getCurrentItem() > 0) {
                int position = binding.viewPager.getCurrentItem() - 1;
                binding.viewPager.setCurrentItem(position);

            } else {
                Toast.makeText(getContext(), "Can not go previous", Toast.LENGTH_SHORT).show();
            }
        });

        binding.summery.setOnClickListener(v -> {
            onSummeryClick();
        });


        return binding.getRoot();

    }

    private void init() {
        arrayList = new ArrayList<>();
        adapter = new QuestionAdapter(arrayList, this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setKeepScreenOn(true);


        if (getActivity() != null) {
            MainActivity.summeryViewModel.getSelectedPosition().observe(getActivity(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer selectedPosition) {

                    binding.viewPager.setCurrentItem(selectedPosition);
                }
            });
        }

    }

    private void loadQuestionData(String quizId) {
        if (quizId == null) {
            return;
        }

        MyApplication.firestore.collection("questions")
                .whereEqualTo("quizId", quizId).orderBy("order", Query.Direction.ASCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots != null) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        QuestionData data = documentSnapshot.toObject(QuestionData.class);
                        data.setId(documentSnapshot.getId());
                        arrayList.add(data);
                        adapter.notifyDataSetChanged();

                    }
                    startCountDown(quizData.getDuration());
                }
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Error is : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


    private void startCountDown(int durationInMin) {
        long durationInMilli = (long) (durationInMin * 60) * 1000;
        new CountDownTimer(durationInMilli, 1000) {

            public void onTick(long millisUntilFinished) {
                int sec = (int) millisUntilFinished / 1000;
                int min = sec / 60;
                int seconds = sec % 60;

                String currentTime = min + " min  " + seconds + " sec";
                binding.countDown.setText(currentTime);
                userTime = userTime + 1;

            }

            public void onFinish() {
                binding.countDown.setText("Time Over !");
                Toast.makeText(getContext(), "Time Over !", Toast.LENGTH_SHORT).show();
                showTimeOverDialog();
            }

        }.start();
    }


    private void saveResult() {
        ResultData data = getResultData();
        WriteBatch batch = MyApplication.firestore.batch();

        DocumentReference resultRef = MyApplication.firestore.collection("results").document();
        CollectionReference resultHistoryColRef =  resultRef.collection("solution");

        batch.set(resultRef,data);

        for (QuestionData questionData : arrayList){
            DocumentReference resultHistoryReference = resultHistoryColRef.document(questionData.getId());
            batch.set(resultHistoryReference,questionData);
        }

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                data.setId(resultRef.getId());
                NavDirections directions = QuestionRootFragmentDirections.actionQuestionRootFragmentToResultFragment(data);
//                        Navigation.findNavController(getView()).navigate(directions);
                NavHostFragment.findNavController(QuestionRootFragment.this).navigate(directions);

            }
        })
              .addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Error is : " + e.toString(), Toast.LENGTH_SHORT).show();
        });


    }

    private ResultData getResultData() {

        ResultData data = new ResultData();
        data.setUserId(MyApplication.firebaseAuth.getUid());
        data.setQuizId(quizData.getId());
        data.setQuizTitle(quizData.getName());
        data.setDuration(quizData.getDuration());
        data.setTotalQuestion(quizData.getTotalQuestion());
        data.setTotalMarks(quizData.getTotalMarks());
        data.setUserTimeInSecond(userTime);

        int rightAns = 0;
        int wrongAns = 0;
        int noAns = 0;
        int userMarks = 0;

        for (QuestionData questionData : arrayList) {
            if (questionData.getUserAnswer() == null) {
                // user ne ans nhi diya
                noAns = noAns + 1;
            } else if (questionData.getAnswer().equals(questionData.getUserAnswer())) {
                // user ne right ans diya
                rightAns = rightAns + 1;
            } else {
                wrongAns = wrongAns + 1;
            }

        }

        int singleQueMark = quizData.getTotalQuestion() / quizData.getTotalMarks();
        userMarks = rightAns * singleQueMark;

        data.setRightAnswer(rightAns);
        data.setWrongAnswer(wrongAns);
        data.setNotAttendedQuestion(noAns);
        data.setUserMarks(userMarks);
        data.setAttemptedAt(Timestamp.now());

        return data;
    }

    private void showTimeOverDialog() {
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle("Time Over !")
                .setMessage("Do you want to save the result")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        saveResult();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Navigation.findNavController(getView()).popBackStack();
            }
        }).create().show();
    }


    @Override
    public void onOptionClick(int position, String userAnswer) {
        QuestionData data = arrayList.get(position);
        data.setUserAnswer(userAnswer);
    }


    @Override
    public void onClearClick(int position) {
        QuestionData data = arrayList.get(position);
        data.setUserAnswer(null);
    }

    private void onSummeryClick() {
        MainActivity.summeryList.clear();
        for (QuestionData data : arrayList) {
            if (data.getUserAnswer() == null) {
                // User ne ans nahi diya hai
                MainActivity.summeryList.add(false);
            } else {
                // User ne ans de diya hai
                MainActivity.summeryList.add(true);
            }
        }
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(R.id.summeryFragment);

        }


    }
}