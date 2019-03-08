package com.ub.quiz_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Recycle_Adapter extends RecyclerView.Adapter<Recycle_Adapter.myViewHolder>
{
    private ActivityAdapterInterface mActivityAdapterInterface;
    List<Content_Model> l1;
    Context context;
    public Recycle_Adapter(List<Content_Model> l1, Context context)
    {
        this.l1 = l1;
        this.context = context;
    }

    public Recycle_Adapter(ActivityAdapterInterface mActivityAdapterInterface)
    {
        this.mActivityAdapterInterface = mActivityAdapterInterface;

    }

    @NonNull
    @Override
    public Recycle_Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater LI=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=LI.inflate(R.layout.holder,viewGroup,false);
        myViewHolder myviewholder=new myViewHolder(v,context,l1);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Recycle_Adapter.myViewHolder myViewHolder, int i)
    {
        Content_Model CM=l1.get(i);
        myViewHolder.IV.setImageResource(CM.getId());
        myViewHolder.TV.setText(CM.getSubject());
    }

    @Override
    public int getItemCount() {
        return l1.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView IV;
        TextView TV;
        List<Content_Model> l1=new ArrayList<>();
        Context context;
        public static final int REQUEST_CODE_QUIZ = 1;
        public static final String SHARED_PREFS = "sharedPrefs";
        public static final String KEY_HIGHSCORE = "heyHighscore";
        public static final String Extra_CategoryId="extracategoryid";


        public myViewHolder(@NonNull View itemView,Context context,List<Content_Model> l1)
        {
            super(itemView);
            this.l1=l1;
            this.context=context;
            itemView.setOnClickListener(this);//to resister onclick listener to card view
            String[] CategoryId=Question.getAllCategoryId();

            IV=(ImageView)itemView.findViewById(R.id.my_image);
            TV=(TextView)itemView.findViewById(R.id.my_text);

        }
        @Override
        public void onClick(View v)
        {
            startQuiz();

        }
        private void startQuiz()
        {
            int position=getAdapterPosition();
            String Category= String.valueOf(position+1);
            Content_Model content_model=this.l1.get(position);
            Intent intent=new Intent(context,QuizActivity.class);
            intent.putExtra("img_id",content_model.getId());
            intent.putExtra("Subject",content_model.getSubject());
            intent.putExtra(Extra_CategoryId,Category);
            ((Activity) context).startActivityForResult(intent,REQUEST_CODE_QUIZ);
        }
    }
}
