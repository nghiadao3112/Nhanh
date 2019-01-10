package cc.ccds.testsql.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cc.ccds.testsql.Quiz.Question;

import static android.content.ContentValues.TAG;

public class DBManager extends SQLiteOpenHelper {

    private final String DATABASE_NAME;
    private static final String TABLE_NAME="questionaire";
    private static final String ID="id";
    private static final String QUESTION="question";
    private static final String TRUE_ANSWER="true_answer";
    private static final String WRONG_ANSWER_1="wrong_answer1";
    private static final String WRONG_ANSWER_2="wrong_answer2";
    private static final String WRONG_ANSWER_3="wrong_answer3";
    private Context context;

    private String SQLquery="CREATE TABLE "+TABLE_NAME+" ("+
            ID+" integer primary key, "+
            QUESTION+" TEXT, "+
            TRUE_ANSWER+" TEXT, "+
            WRONG_ANSWER_1+" TEXT, "+
            WRONG_ANSWER_2+" TEXT, "+
            WRONG_ANSWER_3+" TEXT);";


    public DBManager(Context context,String name) {
        super(context, name, null, 1);
        this.DATABASE_NAME=name;
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLquery);
        Log.i(TAG,"create database OK");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




    public void addQuestion(Question question){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();  // Contentvalues de gui du lieu toi database


        values.put(QUESTION,question.getQuestion());
        values.put(TRUE_ANSWER,question.getTrueAnswer());
        values.put(WRONG_ANSWER_1,question.getWrongAnswer1());
        values.put(WRONG_ANSWER_2,question.getWrongAnswer2());
        values.put(WRONG_ANSWER_3,question.getWrongAnswer3());


        db.insert(TABLE_NAME,null,values);
        db.close();
        Log.i(TAG,"add question OK");
    }

    public List<Question> getAllQuestion(){
        List<Question> questionList=new ArrayList<>();
        String selectQuery ="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);// cursor dung de nhan du lieu tu database
        if (cursor.moveToFirst()){   //kiem tra xem co question nao khong
            do {

                Question question=new Question();
                question.setiD(cursor.getInt(0));
                question.setQuestion(cursor.getString(1));
                question.setTrueAnswer(cursor.getString(2));
                question.setWrongAnswer1(cursor.getString(3));
                question.setWrongAnswer2(cursor.getString(4));
                question.setWrongAnswer3(cursor.getString(5));
                questionList.add(question);

            }while (cursor.moveToNext());// kiem tra xem mang question con phan tu nao khong
        }
        db.close();
        cursor.close();
        Log.i(TAG,"get all question OK");
        return questionList;
    }

    public Question getQuestionByID(int iD){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,QUESTION,TRUE_ANSWER,WRONG_ANSWER_1,WRONG_ANSWER_2,WRONG_ANSWER_3},ID +"=?",
        new String[]{String.valueOf(ID)},null,null,null,null);
        Question question=new Question(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        cursor.close();
        db.close();
        return question;
    }

    public int updateQuestion(Question question){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(QUESTION,question.getQuestion());
        contentValues.put(TRUE_ANSWER,question.getTrueAnswer());
        contentValues.put(WRONG_ANSWER_1,question.getWrongAnswer1());
        contentValues.put(WRONG_ANSWER_2,question.getWrongAnswer2());
        contentValues.put(WRONG_ANSWER_3,question.getWrongAnswer3());
        int count= db.update(TABLE_NAME,contentValues,"ID ="+question.getiD(),null);//ten bang, content, dieu kien update,
        //db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(question.getiD())});// cau lenh tuong duong:
        if (count>0){
            Log.d(TAG,"update OK");
        }
        else {
            Log.d(TAG,"update False");
        }
        db.close();
        return count;
    }

    public int deleteQuestion(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        return  db.delete(TABLE_NAME,ID+"=?",new String[]{String.valueOf(id)});
    }

    public void deleteAll(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

}
