package com.arkapps.onlinetestapp.cls;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class ResultData implements Parcelable {

    private int totalQuestion,duration,userTimeInSecond,notAttendedQuestion,rightAnswer,wrongAnswer,
            userMarks,totalMarks;
    private String userId,id,quizId,quizTitle;
    private Timestamp attemptedAt;

    public ResultData (){}

    protected ResultData(Parcel in) {
        totalQuestion = in.readInt();
        duration = in.readInt();
        userTimeInSecond = in.readInt();
        notAttendedQuestion = in.readInt();
        rightAnswer = in.readInt();
        wrongAnswer = in.readInt();
        userMarks = in.readInt();
        totalMarks = in.readInt();
        userId = in.readString();
        id = in.readString();
        quizId = in.readString();
        quizTitle = in.readString();
        attemptedAt = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public static final Creator<ResultData> CREATOR = new Creator<ResultData>() {
        @Override
        public ResultData createFromParcel(Parcel in) {
            return new ResultData(in);
        }

        @Override
        public ResultData[] newArray(int size) {
            return new ResultData[size];
        }
    };

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getUserTimeInSecond() {
        return userTimeInSecond;
    }

    public void setUserTimeInSecond(int userTimeInSecond) {
        this.userTimeInSecond = userTimeInSecond;
    }

    public int getNotAttendedQuestion() {
        return notAttendedQuestion;
    }

    public void setNotAttendedQuestion(int notAttendedQuestion) {
        this.notAttendedQuestion = notAttendedQuestion;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public int getWrongAnswer() {
        return wrongAnswer;
    }

    public void setWrongAnswer(int wrongAnswer) {
        this.wrongAnswer = wrongAnswer;
    }

    public int getUserMarks() {
        return userMarks;
    }

    public void setUserMarks(int userMarks) {
        this.userMarks = userMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Timestamp getAttemptedAt() {
        return attemptedAt;
    }

    public void setAttemptedAt(Timestamp attemptedAt) {
        this.attemptedAt = attemptedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalQuestion);
        dest.writeInt(duration);
        dest.writeInt(userTimeInSecond);
        dest.writeInt(notAttendedQuestion);
        dest.writeInt(rightAnswer);
        dest.writeInt(wrongAnswer);
        dest.writeInt(userMarks);
        dest.writeInt(totalMarks);
        dest.writeString(userId);
        dest.writeString(id);
        dest.writeString(quizId);
        dest.writeString(quizTitle);
        dest.writeParcelable(attemptedAt, flags);
    }
}
