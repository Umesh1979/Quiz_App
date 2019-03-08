package com.ub.quiz_app;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

public class Question implements Parcelable
{
    public static final String category_1="1";
    public static final String category_2="2";
    public static final String category_3="3";
    public static final String category_4="4";
    public static final String category_5="5";
    public static final String category_6="6";
    public static final String category_7="7";
    public static final String category_8="8";
    public static final String category_9="9";
    public static final String category_10="10";

    private String Question;
    private String ans1;
    private String ans2;
    private String ans3;
    int answerNr;
    private String Category_Id;



    public Question()
    {

    }

    public Question(String question, String ans1, String ans2, String ans3, int answerNr,String CategoryId)
    {
        this.Question = question;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.answerNr = answerNr;
        this.Category_Id=CategoryId;
    }

    protected Question(Parcel in) {
        Question = in.readString();
        ans1 = in.readString();
        ans2 = in.readString();
        ans3 = in.readString();
        answerNr = in.readInt();
        Category_Id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Question);
        dest.writeString(ans1);
        dest.writeString(ans2);
        dest.writeString(ans3);
        dest.writeInt(answerNr);
        dest.writeString(Category_Id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestion()
    {
        return Question;
    }

    public void setQuestion(String question)
    {
        Question = question;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1)
    {
        this.ans1 = ans1;
    }

    public String getAns2()
    {
        return ans2;
    }

    public void setAns2(String ans2)
    {
        this.ans2 = ans2;
    }

    public String getAns3()
    {
        return ans3;
    }

    public void setAns3(String ans3)
    {
        this.ans3 = ans3;
    }

    public int getAnswerNr()
    {
        return answerNr;
    }

    public void setAnswerNr(int answerNr)
    {
        this.answerNr = answerNr;
    }

    public String getCategory_Id() {
        return Category_Id;
    }

    public void setCategory_Id(String category_Id) {
        Category_Id = category_Id;
    }
    public static String[] getAllCategoryId()
    {
        return new String[]{
                category_1,category_2,category_3,category_4,category_5,
                category_6,category_7,category_8,category_9,category_10};
    }
}
