package com.ub.quiz_app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity
{
    public static final String EXTRA_SCORE="extraScore";
    public static final String KEY_SCORE="keyScore";
    public static final String KEY_QUESTION_COUNT="keyQuestionCount";
    public static final String KEY_MILLIS_LEFT="keyMillisLeft";
    public static final String KEY_ANSWERED="keyAnsered";
    public static final String KEY_QUESTION_LIST="keyQuestionList";

    Toolbar toolbar1;
    String subject;
    private ArrayList<Question> question_list;
    Context context;
    private RadioButton RB1,RB2,RB3;
    private RadioGroup radioGroup;
    private TextView textViewCountDown,myheader,TV1,TV2,TV3;
    private Button btn1;
    private ColorStateList textColorDefaultRb,tintColorDefaultRb;
    private ColorStateList textColorDefaultCd;
    private int questionCounter,questionCounterTotal;
    private Question currentQuestion;
    private int score;
    private int maxScore;
    private boolean answered;
    private static final long COUNTDOWN_IN_MILLS=30000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private long backPressedTime;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        radioGroup= findViewById(R.id.my_radio_group);
        RB1= findViewById(R.id.my_radio1);
        RB2= findViewById(R.id.my_radio2);
        RB3= findViewById(R.id.my_radio3);
        myheader= findViewById(R.id.my_header);
        textViewCountDown= findViewById(R.id.count_down);
        TV1= findViewById(R.id.my_Score);
        TV2= findViewById(R.id.my_Question_count);
        TV3= findViewById(R.id.my_question);
        btn1= findViewById(R.id.btn_confirm);

        textColorDefaultRb=RB1.getTextColors();
        tintColorDefaultRb=RB1.getButtonTintList();
        textColorDefaultCd=textViewCountDown.getTextColors();

        Intent intent=getIntent();
        subject=intent.getStringExtra("Subject");
        String Category_Selected=intent.getStringExtra("extracategoryid");
        //toolbar
        toolbar1= findViewById(R.id.toolbar2);
        myheader.setText("Chapter : "+subject);
        setSupportActionBar(toolbar1);//to load toolbar in fragment
        if(savedInstanceState==null)
        {
            QuizDbHelper db_helper=new QuizDbHelper(this);
            question_list=db_helper.getQuestions(Category_Selected);
            questionCounterTotal=question_list.size();
            Collections.shuffle(question_list);
            showNextQuestion();
        }
        else
        {
            question_list=savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            if(question_list==null)
            {
                finish();
            }
            questionCounterTotal=question_list.size();
            questionCounter=savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion=question_list.get(questionCounter-1);
            score=savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis=savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered=savedInstanceState.getBoolean(KEY_ANSWERED);

            if(!answered)
            {
                startCountDown();
            }
            else
            {
                updateCountDownText();
                showSolution();
            }
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!answered)
                {
                    if(RB1.isChecked()||RB2.isChecked()||RB3.isChecked())
                    {
                        checkAnswer();
                    }
                    else
                    {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    showNextQuestion();
                }
            }
        });

    }
    private void showNextQuestion()
    {
        RB1.setTextColor(textColorDefaultRb);
        RB2.setTextColor(textColorDefaultRb);
        RB3.setTextColor(textColorDefaultRb);
        RB1.setButtonTintList(tintColorDefaultRb);
        RB2.setButtonTintList(tintColorDefaultRb);
        RB3.setButtonTintList(tintColorDefaultRb);
        radioGroup.clearCheck();
        RB1.setTextSize(20);
        RB2.setTextSize(20);
        RB3.setTextSize(20);
        if(questionCounter<questionCounterTotal)
        {
            currentQuestion=question_list.get(questionCounter);
            TV3.setText(currentQuestion.getQuestion());
            RB1.setText(currentQuestion.getAns1());
            RB2.setText(currentQuestion.getAns2());
            RB3.setText(currentQuestion.getAns3());

            questionCounter++;
            TV2.setText("Question: "+questionCounter+"/"+questionCounterTotal);
            answered=false;
            btn1.setText("Confirm");

            timeLeftInMillis=COUNTDOWN_IN_MILLS;
            startCountDown();
            maxScore=questionCounterTotal;
        }
        else
        {
            finishQuiz();
        }
    }
    private void startCountDown()
    {
        countDownTimer=new CountDownTimer(timeLeftInMillis,1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timeLeftInMillis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish()
            {
                timeLeftInMillis=0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }
    private void updateCountDownText()
    {
        int minutes=(int)(timeLeftInMillis/1000)/60;
        int seconds=(int)(timeLeftInMillis/1000)%60;

        String timeFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        textViewCountDown.setText(timeFormatted);

        if(timeLeftInMillis<10000)
        {
            textViewCountDown.setTextColor(Color.RED);
            Animation animation1 =AnimationUtils.loadAnimation(getApplicationContext(),R.anim.timer_blink);
            textViewCountDown.startAnimation(animation1);
        }
        else
        {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }
    private void checkAnswer()
    {
        answered=true;
        countDownTimer.cancel();
        RadioButton rbSelected=findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNr=radioGroup.indexOfChild(rbSelected)+1;
        if(answerNr==currentQuestion.getAnswerNr())
        {
            score++;
            TV1.setText("Score: "+score);
        }
        showSolution();
    }
    private void showSolution()
    {
        RB1.setTextColor(Color.RED);
        RB2.setTextColor(Color.RED);
        RB3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr())
        {
            case 1:
                RB1.setTextColor(Color.GREEN);
                RB1.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                RB1.setChecked(true);
                RB1.setTextSize(25);
                break;
            case 2:
                RB2.setTextColor(Color.GREEN);
                RB2.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                RB2.setChecked(true);
                RB2.setTextSize(25);
                break;
            case 3:
                RB3.setTextColor(Color.GREEN);
                RB3.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                RB3.setChecked(true);
                RB3.setTextSize(25);
                break;
        }
        if(questionCounter < questionCounterTotal)
        {
            btn1.setText("Next");
        }
        else
        {
            btn1.setText("Finish");
        }
    }
    private void finishQuiz()
    {
        Intent resultIntent=new Intent();
        resultIntent.putExtra(EXTRA_SCORE,score);//what u want to send back to the activity it is being called i.e score
        setResult(RESULT_OK,resultIntent);
        Log.d("HighScoreQuiz", String.valueOf(score));
        finish();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(countDownTimer!=null)
        {
            countDownTimer.cancel();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE,score);
        outState.putInt(KEY_QUESTION_COUNT,questionCounter);
        outState.putLong(KEY_MILLIS_LEFT,timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED,answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST,question_list);

    }

    @Override
    public void onBackPressed()
    {
        if(backPressedTime + 2000 > System.currentTimeMillis())
        {
            LayoutInflater LI=LayoutInflater.from(this);
            View prompt_view=LI.inflate(R.layout.alignlayout,null);
            final AlertDialog AD=new AlertDialog.Builder(this).create();
            TextView title=(TextView)prompt_view.findViewById(R.id.text_title);
            TextView message=(TextView)prompt_view.findViewById(R.id.text_score);
            Button btn_ok=(Button)prompt_view.findViewById(R.id.btn_OK);
            message.setText((score + " / " +maxScore));
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //AD.dismiss();
                    finishQuiz();
                    QuizActivity.super.onBackPressed();
                }
            });
            AD.setView(prompt_view);
            AD.show();
        }
        else
        {
            Toast.makeText(QuizActivity.this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTime=System.currentTimeMillis();

    }
}
