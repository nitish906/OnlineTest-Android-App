package com.arkapps.onlinetestapp.cls;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class QuestionData implements Parcelable {

    private String answer,question,quizId,id;
    private int order;
    private ArrayList<String> options;
    private String userAnswer;

    public QuestionData (){}

    protected QuestionData(Parcel in) {
        answer = in.readString();
        question = in.readString();
        quizId = in.readString();
        id = in.readString();
        order = in.readInt();
        options = in.createStringArrayList();
        userAnswer = in.readString();
    }

    public static final Creator<QuestionData> CREATOR = new Creator<QuestionData>() {
        @Override
        public QuestionData createFromParcel(Parcel in) {
            return new QuestionData(in);
        }

        @Override
        public QuestionData[] newArray(int size) {
            return new QuestionData[size];
        }
    };

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answer);
        dest.writeString(question);
        dest.writeString(quizId);
        dest.writeString(id);
        dest.writeInt(order);
        dest.writeStringList(options);
        dest.writeString(userAnswer);
    }
}
