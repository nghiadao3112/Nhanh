package cc.ccds.testsql.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cc.ccds.testsql.Adapter.CustomAdapter;
import cc.ccds.testsql.Data.DBAccess;
import cc.ccds.testsql.Data.DBManager;
import cc.ccds.testsql.Quiz.Question;
import cc.ccds.testsql.R;

public class DatabaseActivity extends Activity {

    //***********************MAIN ACTIVITY****************************
    private EditText editTextID;
    private EditText editTextQuest;
    private EditText editTextTrue;
    private EditText editTextWrongAnswer1;
    private EditText editTextWrongAnswer2;
    private EditText editTextWrongAnswer3;
    private Button buttonSave;
    private Button buttonUpdate;
    private Button buttonBack;
    private List<String> dbNames;
    //*****************************************************************


    //***********************LIST VIEW*********************************
    private ListView listViewQuest;
    private CustomAdapter customAdapter;
    //*****************************************************************



    //***********************DATABASE**********************************
    private List<Question> questions;
    private DBManager dbManager ;
    private DBAccess dbAccess;
    //*****************************************************************



    /*************************DIALOG***********************************
     private Dialog dialog=new Dialog(MainActivity.this);
     private Button buttonYes = (Button) dialog.findViewById(R.id.btn_yes);
     private Button buttonNo = (Button) dialog.findViewById(R.id.btn_no);
     /******************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        //dbManager.hello();
        createItems();
        questions= dbManager.getAllQuestion();
        setAdapter();


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DatabaseActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question question=createQuestion();
                if (question!=null){
                    dbManager.addQuestion(question);
                }
                updateListView();
                setAdapter();
            }
        });
        listViewQuest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question question=questions.get(position);
                editTextID.setText(String.valueOf(question.getiD()));
                editTextQuest.setText(question.getQuestion());
                editTextTrue.setText(question.getTrueAnswer());
                editTextWrongAnswer1.setText(question.getWrongAnswer1());
                editTextWrongAnswer2.setText(question.getWrongAnswer2());
                editTextWrongAnswer3.setText(question.getWrongAnswer3());
                buttonSave.setEnabled(false);
                buttonUpdate.setEnabled(true);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question question=new Question();
                question.setiD(Integer.parseInt(String.valueOf(editTextID.getText())));
                question.setQuestion(String.valueOf(editTextQuest.getText()));
                question.setTrueAnswer(String.valueOf(editTextTrue.getText()));
                question.setWrongAnswer1(String.valueOf(editTextWrongAnswer1.getText()));
                question.setWrongAnswer2(String.valueOf(editTextWrongAnswer2.getText()));
                question.setWrongAnswer3(String.valueOf(editTextWrongAnswer3.getText()));
                int count =dbManager.updateQuestion(question);
                if (count>0){
                    updateListView();
                }
                buttonSave.setEnabled(true);
                buttonUpdate.setEnabled(false);
            }
        });

        listViewQuest.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Question question =questions.get(position);
                int count=dbManager.deleteQuestion(question.getiD());
                if (count>0){
                    Toast.makeText(DatabaseActivity.this,"Delete successfully",Toast.LENGTH_SHORT).show();
                    updateListView();
                }
                else {
                    Toast.makeText(DatabaseActivity.this,"Delete fales",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    public Question createQuestion(){
        String quest=editTextQuest.getText().toString();
        String trueAnswer=editTextTrue.getText().toString();
        String fales=editTextWrongAnswer1.getText().toString();
        String fales1=editTextWrongAnswer2.getText().toString();
        String fales2=editTextWrongAnswer3.getText().toString();
        Question question=new Question(quest,trueAnswer,fales,fales1,fales2);
        return question;
    }

    public void createItems(){
        editTextID=(EditText) findViewById(R.id.edt_id);
        editTextQuest=(EditText) findViewById(R.id.edt_ques);
        editTextTrue=(EditText) findViewById(R.id.edt_true);
        editTextWrongAnswer1=(EditText) findViewById(R.id.edt_wrong_1);
        editTextWrongAnswer2=(EditText) findViewById(R.id.edt_wrong_2);
        editTextWrongAnswer3=(EditText) findViewById(R.id.edt_wrong_3);

        buttonSave=(Button) findViewById(R.id.btn_save);
        buttonUpdate=(Button) findViewById(R.id.btn_update);
        buttonBack=(Button)findViewById(R.id.btn_back);

        listViewQuest=(ListView)findViewById(R.id.lv_all_question);

        //Khoi tao va truy cap database
        Intent intent=getIntent();
        String temp=intent.getStringExtra(ChooseActivity.DATABASE_NAME);
        dbManager=new DBManager(DatabaseActivity.this,temp);
        dbAccess=dbAccess.getInstance(getApplicationContext(),"manager_databases.db");
        dbNames=dbAccess.getAllDatabases();

        Boolean checkDB=false;
        for (int i=0;i<dbNames.size();i++){
            if (dbNames.get(i).equals(temp)==true){
                checkDB=true;
                break;
            }
        }
        if (checkDB==false){
            dbAccess.addDatabaseName(temp);
            dbManager.deleteAll();
        }
    }

    private void setAdapter(){
        if (customAdapter==null) {
            customAdapter=new CustomAdapter(this,R.layout.list_view_question,questions);
            listViewQuest.setAdapter(customAdapter);
        }
        else{
            customAdapter.notifyDataSetChanged();
            listViewQuest.setSelection(customAdapter.getCount()-1);
        }


    }

    private void updateListView(){
        questions.clear();
        questions.addAll(dbManager.getAllQuestion());
        if (customAdapter!=null){
            customAdapter.notifyDataSetChanged();
        }

    }





    /*private void myDialog(){
        dialog.setTitle("System");
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.show();
    }*/

}
