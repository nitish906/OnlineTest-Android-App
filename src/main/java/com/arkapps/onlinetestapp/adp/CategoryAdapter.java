package com.arkapps.onlinetestapp.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arkapps.onlinetestapp.cls.CategoryData;
import com.arkapps.onlinetestapp.databinding.CategoryListBinding;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<CategoryData> arrayList;
    private Context context;
    private onCategoryClickListener categoryClickListener;

    public CategoryAdapter(ArrayList<CategoryData> arrayList,  onCategoryClickListener categoryClickListener) {
        this.arrayList = arrayList;

        this.categoryClickListener = categoryClickListener;
    }

    public interface onCategoryClickListener{
        void onCategoryClick(int position,View view);
    }


    @NonNull
    @NotNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if (context==null){
            context = parent.getContext();
        }
        return new CategoryViewHolder(CategoryListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryViewHolder holder, int position) {

        CategoryData data = arrayList.get(position);
        if (data == null){
            return;
        }

        holder.binding.name.setText(data.getName());
        Glide.with(context).load(data.getMainImage()).into(holder.binding.categoryImage);
        holder.binding.categoryCard.setOnClickListener(v -> {
            categoryClickListener.onCategoryClick(position,v);
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
       CategoryListBinding binding;
        public CategoryViewHolder(@NonNull @NotNull CategoryListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }
    }
}
