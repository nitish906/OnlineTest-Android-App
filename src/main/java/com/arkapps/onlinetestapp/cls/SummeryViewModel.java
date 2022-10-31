package com.arkapps.onlinetestapp.cls;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SummeryViewModel extends ViewModel {

    private MutableLiveData<Integer> selectedPosition;

    public MutableLiveData<Integer> getSelectedPosition(){
        if (selectedPosition == null){
            selectedPosition = new MutableLiveData<>();
        }
        return selectedPosition;
    }

    public void setSelectedPosition(int position){
        if (selectedPosition != null) {
            selectedPosition.setValue(position);
        }
    }

}
