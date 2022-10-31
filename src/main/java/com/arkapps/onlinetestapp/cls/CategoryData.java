package com.arkapps.onlinetestapp.cls;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryData implements Parcelable {

    private String mainImage,name,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CategoryData() {}

    protected CategoryData(Parcel in) {
        mainImage = in.readString();
        name = in.readString();
        id = in.readString();
    }

    public static final Creator<CategoryData> CREATOR = new Creator<CategoryData>() {
        @Override
        public CategoryData createFromParcel(Parcel in) {
            return new CategoryData(in);
        }

        @Override
        public CategoryData[] newArray(int size) {
            return new CategoryData[size];
        }
    };

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mainImage);
        dest.writeString(name);
        dest.writeString(id);
    }
}
