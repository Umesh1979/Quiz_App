package com.ub.quiz_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Content_frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Content_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Content_frag extends Fragment implements ActivityAdapterInterface
{
    public static final int REQUEST_CODE_QUIZ = 1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "heyHighscore";
    TextView myhighestscore;
    private int highscore;

    Toolbar toolbar;
    TextView myheader;
    RecyclerView RV;

        public static final String Extra_CategoryId="extracategoryid";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Content_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Content_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Content_frag newInstance(String param1, String param2) {
        Content_frag fragment = new Content_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_content_frag, container, false);


        //toolbar
        //toolbar=(Toolbar)v.findViewById(R.id.toolbar);

        //toolbar.setTitleTextColor(Color.WHITE);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        myheader=(TextView)v.findViewById(R.id.my_header);
        myheader.setText("Java Quiz");
        myhighestscore=(TextView)v.findViewById(R.id.highscore_text);
        loadHighscore();

        //Recyclerview
        RV=(RecyclerView)v.findViewById(R.id.my_recycler_view);
        Recycle_Adapter RA=new Recycle_Adapter(Content_Model.getData(), getContext());
        RV.setAdapter(RA);
        LinearLayoutManager LLM=new LinearLayoutManager(getContext());
        LLM.setOrientation(LinearLayoutManager.VERTICAL);
        RV.setLayoutManager(LLM);
        RV.setItemAnimator(new DefaultItemAnimator());
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("HighScore3-onActivity", String.valueOf(highscore));
        if(requestCode==REQUEST_CODE_QUIZ)
        {
            if(resultCode==RESULT_OK)
            {
                int score=data.getIntExtra(QuizActivity.EXTRA_SCORE,0);//to save the updated scores in integer from QuizActivity
                if(score>highscore)
                {
                    updateHighscore(score);
                }
            }
        }
    }
    private void loadHighscore()
    {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getContext());
        highscore=prefs.getInt(KEY_HIGHSCORE,0);
        myhighestscore.setText("Highest Score : "+highscore);
    }

    private void updateHighscore(int highscoreNew)
    {
        highscore=highscoreNew;
        myhighestscore.setText("Highest Score : "+highscore);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt(KEY_HIGHSCORE,highscore);
        editor.apply();
    }

}
