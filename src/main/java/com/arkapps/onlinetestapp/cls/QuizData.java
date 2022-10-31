package com.arkapps.onlinetestapp.cls;

import android.os.Parcel;
import android.os.Parcelable;

public class QuizData implements Parcelable {

    private String category,description,subCategory,name,id;
    private int duration,totalMarks,totalQuestion,price;

    public QuizData() {
    }

    protected QuizData(Parcel in) {
        category = in.readString();
        description = in.readString();
        subCategory = in.readString();
        name = in.readString();
        duration = in.readInt();
        totalMarks = in.readInt();
        totalQuestion = in.readInt();
        price = in.readInt();
        id  = in.readString();
    }

    public static final Creator<QuizData> CREATOR = new Creator<QuizData>() {
        @Override
        public QuizData createFromParcel(Parcel in) {
            return new QuizData(in);
        }

        @Override
        public QuizData[] newArray(int size) {
            return new QuizData[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(subCategory);
        dest.writeString(name);
        dest.writeInt(duration);
        dest.writeInt(totalMarks);
        dest.writeInt(totalQuestion);
        dest.writeInt(price);
        dest.writeString(id);
    }
}
