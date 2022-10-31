package com.arkapps.onlinetestapp.adp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arkapps.onlinetestapp.cls.QuizData;
import com.arkapps.onlinetestapp.databinding.QuizListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private ArrayList<QuizData> arrayList;
    private OnStartQuizClickListener clickListener;

    public interface OnStartQuizClickListener{
        void onStartClick(int position);
    }


    public QuizAdapter(ArrayList<QuizData> arrayList, OnStartQuizClickListener clickListener) {
        this.arrayList = arrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @NotNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new QuizViewHolder(QuizListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull QuizViewHolder holder, int position) {
        QuizData data = arrayList.get(position);
        if (data == null) {
            return;
        }

        holder.binding.quizName.setText(data.getName());
        String duration = data.getDuration() + " min";
        holder.binding.duration.setText(duration);
        holder.binding.totalQuestion.setText(String.valueOf(data.getTotalQuestion()));

        holder.binding.startButton.setOnClickListener(v -> {
            clickListener.onStartClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class QuizViewHolder extends RecyclerView.ViewHolder {

        QuizListBinding binding;

        public QuizViewHolder(@NonNull @NotNull QuizListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
