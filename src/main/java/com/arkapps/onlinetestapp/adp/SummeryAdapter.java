package com.arkapps.onlinetestapp.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arkapps.onlinetestapp.R;
import com.arkapps.onlinetestapp.databinding.SummeryListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SummeryAdapter extends RecyclerView.Adapter<SummeryAdapter.SummeryViewHolder> {

    private ArrayList<Boolean> arrayList;
    Context context;
    private OnNumberClickListener clickListener;

    public interface OnNumberClickListener{
        void onNumberClick(int position);
    }

    public SummeryAdapter(ArrayList<Boolean> arrayList, OnNumberClickListener clickListener) {
        this.arrayList = arrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @NotNull
    @Override
    public SummeryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        return new SummeryViewHolder(SummeryListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SummeryViewHolder holder, int position) {
         boolean value = arrayList.get(position);

         int currentNumber = position +1;

         holder.binding.questionNumber.setText(String.valueOf(currentNumber));

         if(value){
             holder.binding.questionNumber.setBackground(context.getDrawable(R.drawable.green_circle));
         }else {
             holder.binding.questionNumber.setBackground(context.getDrawable(R.drawable.circle));

         }

         holder.binding.questionNumber.setOnClickListener(v -> {
             clickListener.onNumberClick(position);
         });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class SummeryViewHolder extends RecyclerView.ViewHolder{

        SummeryListBinding binding;
        public SummeryViewHolder(@NonNull @NotNull SummeryListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
