package cc.ccds.testsql.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cc.ccds.testsql.Quiz.Question;

import static android.content.ContentValues.TAG;

public class DBAccess  {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private final String DATABASE_NAME;
    private static DBAccess instance;


    private DBAccess(Context context,String databaseName){
        this.openHelper=new DBOpenHelper(context,databaseName);
        DATABASE_NAME =databaseName;
    }

    public static  DBAccess getInstance(Context context,String database_name){
        /*if(instance==null){
            instance=new DBAccess(context,database_name);
        }*/
        instance=new DBAccess(context,database_name);
        return instance;
    }


    private void open(){
        this.db=openHelper.getWritableDatabase();
    }

    private void close(){
        if (db!=null) {
            this.db.close();
        }
    }

    public List<Question> getAllQuestion(){
        List<Question> questionList=new ArrayList<>();
        String selectQuery ="SELECT * FROM "+"questionaire";
        open();
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
        close();
        cursor.close();
        Log.i(TAG,"get all question OK");
        return questionList;
    }

    public List<String> getAllDatabases(){
        List<String> databaseList= new ArrayList<>();
        String selectQuery="SELECT * FROM databaselist";
        open();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                String databaseName=cursor.getString(1);
                databaseList.add(databaseName);
            }while (cursor.moveToNext());
        }
        close();
        cursor.close();
        return databaseList;
    }

    public void addDatabaseName(String string){
        open();
        ContentValues values =new ContentValues();
        values.put("database_name",string);
        db.insert("databaselist",null,values);
        close();
        Log.i(TAG,"add question OK");
    }

    public void deleteDatabaseName(String string){
        open();
        db.delete("databaselist","database_name=?",new String[]{string});
        close();
    }

    public int getID(String string){
        int id=-1;
        String selectQuery="SELECT id FROM databaselist where database_name=\""+string+"\"";
        open();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            id=cursor.getInt(0);
        }
        close();
        cursor.close();
        return id;
    }

}
