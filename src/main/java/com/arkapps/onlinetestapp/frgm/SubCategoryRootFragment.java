package com.arkapps.onlinetestapp.frgm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arkapps.onlinetestapp.MyApplication;
import com.arkapps.onlinetestapp.R;
import com.arkapps.onlinetestapp.adp.ViewPagerAdapter;
import com.arkapps.onlinetestapp.cls.CategoryData;
import com.arkapps.onlinetestapp.cls.SubCategoryData;
import com.arkapps.onlinetestapp.databinding.FragmentSubCategoryRootBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class SubCategoryRootFragment extends Fragment {


    private FragmentSubCategoryRootBinding binding;
    private ViewPagerAdapter adapter;
    private ArrayList<SubCategoryData> arrayList;

    public SubCategoryRootFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSubCategoryRootBinding.inflate(inflater, container, false);

        CategoryData data = SubCategoryRootFragmentArgs.fromBundle(getArguments()).getCategory();
        init();
        loadSubCatData(data.getId());
        Toast.makeText(getContext(), "Name : " + data.getName(), Toast.LENGTH_SHORT).show();

        return binding.getRoot();
    }

    private void init() {
        arrayList = new ArrayList<>();
        adapter = new ViewPagerAdapter(this, arrayList);
        binding.pager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.pager,
                (tab, position) -> tab.setText(arrayList.get(position).getName())
        ).attach();
    }


    private void loadSubCatData(String categoryId) {
        if (categoryId == null) {
            return;
        }
        MyApplication.firestore.collection("quizSubcategory")
                .whereEqualTo("categoryId", categoryId)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    SubCategoryData data = documentSnapshot.toObject(SubCategoryData.class);
                    data.setId(documentSnapshot.getId());
                    arrayList.add(data);
                    adapter.notifyDataSetChanged();
                    Log.d("TAG", "onSuccess: Data Name : " + data.getName());

                }

            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Error is : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}