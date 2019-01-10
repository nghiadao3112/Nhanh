package cc.ccds.testsql.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cc.ccds.testsql.Quiz.Question;
import cc.ccds.testsql.Score.score;

public class DBScoreManager  {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private final String DATABASE_NAME;
    private static DBAccess instance;
    private static final String SCORE="score";
    private static final String TIMEPLAY="timePlay";


    public DBScoreManager(Context context){
        this.openHelper=new DBOpenHelper(context,"high_score.db");
       this.DATABASE_NAME ="high_score.db";
    }
    private void open(){
        this.db=openHelper.getWritableDatabase();
    }

    private void close(){
        if (db!=null) {
            this.db.close();
        }
    }

    public List<score> getAllScore()  {
        List<score> highScore=new ArrayList<>();
        String temp;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String selectQuery="select * from MyTable order by score desc,timePlay asc,Timestamp asc";
        open();
        Cursor cursor=db.rawQuery(selectQuery,null);// cursor dung de nhan du lieu tu database
        if (cursor.moveToFirst()){   //kiem tra xem co question nao khong
            do {

                score score=new score();
                score.setId(cursor.getInt(0));
                temp=cursor.getString(3);
                try {
                    date= (Date) dateFormat.parse(temp);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                score.setDate(date);
                score.setScore(cursor.getInt(1));
                score.setTimePlay(cursor.getInt(2));

                highScore.add(score);

            }while (cursor.moveToNext());// kiem tra xem mang question con phan tu nao khong
        }
        close();
        cursor.close();
        return highScore;
    }

    public void updateScore(int score,int timePlay){
        open();
        ContentValues values =new ContentValues();
        values.put(SCORE,score);
        values.put(TIMEPLAY,timePlay);
        db.insert("MyTable",null,values);
        close();
    }



}
