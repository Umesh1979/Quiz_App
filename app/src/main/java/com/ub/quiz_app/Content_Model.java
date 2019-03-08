package com.ub.quiz_app;

import java.util.ArrayList;
import java.util.List;

public class Content_Model
{
String subject;
int id;

    public String getSubject()
    {
        return subject;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public static List<Content_Model> getData()
    {
        List<Content_Model> list= new ArrayList<>();
        int[] img_id=get_Images();
        String[] subject_name=get_Subject();

        for(int i=0;i<img_id.length;i++)
        {
            Content_Model obj=new Content_Model();
            obj.setId(img_id[i]);
            obj.setSubject(subject_name[i]);
            list.add(obj);
        }
        return list;
    }
    private static int[] get_Images()
    {
        int[] images={R.drawable.android, R.drawable.android, R.drawable.android, R.drawable.android,
                R.drawable.android, R.drawable.android, R.drawable.android, R.drawable.android,
                R.drawable.android, R.drawable.android};
        return images;
    }
    private static String[] get_Subject()
    {
        String[] subject={"Basic and History", "Control Statements", "Array", "OOPs Concept",
                "Exception Handling", "String and Wrapper Classes",
                "Inner Classes", "Multi-Threading", "Collection", "JDBC"};
        return subject;

    }

}
