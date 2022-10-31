package com.arkapps.onlinetestapp.adp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arkapps.onlinetestapp.databinding.SlidersBinding;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.ImageSliderViewHolder> {
    ArrayList<String> arrayList;
    Context context;


    public ImageSliderAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public ImageSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        if (context== null){
            context = parent.getContext();
        }
        return new ImageSliderViewHolder(SlidersBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(ImageSliderViewHolder viewHolder, int position) {
    String imageUrl = arrayList.get(position);
        Glide.with(context).load(imageUrl).into(viewHolder.binding.imageSlider);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    class ImageSliderViewHolder extends SliderViewAdapter.ViewHolder{
        SlidersBinding binding;
        public ImageSliderViewHolder(SlidersBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
