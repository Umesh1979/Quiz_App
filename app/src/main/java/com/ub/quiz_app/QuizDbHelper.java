package com.ub.quiz_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ub.quiz_app.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME="MyQuiz.db";
    private static final int DATABASE_VERSION=3;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        this.db=db;
        final String sql_create_question_table="CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " TEXT" +
                ")";
        db.execSQL(sql_create_question_table);
        fillQuestionsTable();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    private void fillQuestionsTable()
    {
        Question q1=new Question("Java Programming was designed by","Microsoft","Sun Microsystems","Google",2,"1");
        addQuestion(q1);
        Question q2=new Question("In order to fetch stream of data from network or file, following data types is used","byte","int","char",1,"1");
        addQuestion(q2);
        Question q3=new Question("Which of the following is smallest integer data types","long","short","byte",3,"1");
        addQuestion(q3);
        Question q4=new Question("Java was publicly released in _______________.","May 27, 1992","May 27, 1994","May 27, 1995",3,"1");
        addQuestion(q4);
        Question q5=new Question("After first public release java was targeted at ______________________.","Internet development","Desktop Development","Search Engine Development",1,"1");
        addQuestion(q5);
        Question q6=new Question("Applet was having early support from ___________________.","Opera Mini","Google","Netscape Communications",3,"1");
        addQuestion(q6);
        Question q7=new Question("which kind of language java is ?","Object Oriented","Event Driven","Procedural",1,"1");
        addQuestion(q7);
        Question q8=new Question("The first public implementation was _____________.","Java 0.1","Java 1.1","Java 1.0",3,"1");
        addQuestion(q8);
        Question q9=new Question("In the beginning , Java was created in order to -","Create high performance OS","Connect many household machines","Create Strong Programming alternative to C++",2,"1");
        addQuestion(q9);
        Question q10=new Question("Earlier name of Java Programming language was -","OAK","D","Netbean",1,"1");
        addQuestion(q10);
        Question q11=new Question("What is true about a break?","Break stops the execution of entire program","Break halts the execution and forces the control out of the loop","Break halts the execution of the loop for certain time frame",2,"2");
        addQuestion(q11);
        Question q12=new Question("What is true about do statement?","do statement executes the code of a loop at least once","do statement checks the condition at the beginning of the loop","do statement executes the code more than once always",1,"2");
        addQuestion(q12);
        Question q13=new Question("Which of the following is not a valid flow control statement?","exit()","break","return",1,"2");
        addQuestion(q13);
        Question q14=new Question("Which of these are selection statements in java","if()","for()","break",1,"2");
        addQuestion(q14);
        Question q15=new Question("The conditional statement,__can only test for equality, whereas__ can evaluate any type of Boolean expression.","if,switch","switch,if","while,if",2,"2");
        addQuestion(q15);
        Question q16=new Question("Which of the following is used with the switch statement?","Continue","break","Exit",2,"2");
        addQuestion(q16);
        Question q17=new Question("What is the valid data type for variable “a” to print “Hello World”?switch(a)\n" +
                "{\n" +
                "   System.out.println(\"Hello World\");\n" +
                "}","int and float","byte and char","byte and short",2,"2");
        addQuestion(q17);
        Question q18=new Question("Which of the following is not a decision making statement?","if","switch","do-while",3,"2");
        addQuestion(q18);
        Question q19=new Question("Which of the following is not a valid jump statement?","goto","break","return",1,"2");
        addQuestion(q19);
        Question q20=new Question("From where break statement causes an exit?","Only from innermost switch","Terminates a program","From innermost loops or switches",3,"2");
        addQuestion(q20);
        Question q21=new Question("Which of these operators is used to allocate memory to array variable in Java?","alloc","malloc","new",3,"3");
        addQuestion(q21);
        Question q22=new Question("Which of these is an incorrect array declaration?","int arr[] = int [5] new","int [] arr = new int[5]","int arr[] = new int[5]",1,"3");
        addQuestion(q22);
        Question q23=new Question("What will this code print?  int arr[] = new int [5];\n" +
                "    System.out.print(arr);","0","00000","Class name@ hashcode in hexadecimal form",3,"3");
        addQuestion(q23);
        Question q24=new Question("Which of these is necessary to specify at time of array initialization?","Column","Row","Both Row & Column",2,"3");
        addQuestion(q24);
        Question q25=new Question("Which of these is an incorrect Statement?","Array can be initialized when they are declared","It is necessary to use new operator to initialize an array","None of the above",2,"3");
        addQuestion(q25);
        Question q26=new Question("Which of the following is not OOPS concept in Java?","Compilation","Polymorphism","Encapsulation",1,"4");
        addQuestion(q26);
        Question q27=new Question("Which of the following is a type of polymorphism in Java?","Execution time polymorphism","Multiple polymorphism","Compile time polymorphism",3,"4");
        addQuestion(q27);
        Question q28=new Question("When does method overloading is determined?","At run time","At compile time","At execution time",2,"4");
        addQuestion(q28);
        Question q29=new Question("What is it called if an object has its own lifecycle and there is no owner?","Encapsulation","Aggregation","Association",3,"4");
        addQuestion(q29);
        Question q30=new Question("What is it called where child object gets killed if parent object is killed?","Composition","Encapsulation","Aggregation",1,"4");
        addQuestion(q30);

    }
    private void addQuestion(Question question)
    {
        ContentValues CV=new ContentValues();
        CV.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        CV.put(QuestionsTable.COLUMN_OPTION1, question.getAns1());
        CV.put(QuestionsTable.COLUMN_OPTION2, question.getAns2());
        CV.put(QuestionsTable.COLUMN_OPTION3, question.getAns3());
        CV.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        CV.put(QuestionsTable.COLUMN_CATEGORY_ID,question.getCategory_Id());
        db.insert(QuestionsTable.TABLE_NAME,null,CV);

    }
    public ArrayList<Question> getAllQuestions()
    {
        ArrayList<Question> question_list=new ArrayList<>();
        db=getReadableDatabase();
        Cursor c= db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME,null);
        if(c.moveToFirst())
        {
            do
            {
                Question question =new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAns1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setAns2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setAns3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setCategory_Id(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                question_list.add(question);

            }while(c.moveToNext());
        }
        c.close();
        return question_list;
    }
    public ArrayList<Question> getQuestions(String Category_Id)
    {
        ArrayList<Question> question_list=new ArrayList<>();
        db=getReadableDatabase();
        String[] selectionargs=new String[]{Category_Id};
        Cursor c= db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_CATEGORY_ID + " = ?",selectionargs);
        if(c.moveToFirst())
        {
            do
            {
                Question question =new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setAns1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setAns2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setAns3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setCategory_Id(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                question_list.add(question);

            }while(c.moveToNext());
        }
        c.close();
        return question_list;
    }

}
