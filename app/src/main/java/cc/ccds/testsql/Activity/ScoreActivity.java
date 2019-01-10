package cc.ccds.testsql.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cc.ccds.testsql.Data.DBScoreManager;
import cc.ccds.testsql.Quiz.Question;
import cc.ccds.testsql.R;
import cc.ccds.testsql.Score.score;

public class ScoreActivity extends Activity {

    TextView stP;
    TextView ndP;
    TextView thP;
    TextView stP1;
    TextView ndP1;
    TextView thP1;
    TextView stP2;
    TextView ndP2;
    TextView thP2;
    DBScoreManager dbScoreManager;
    List<score> highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.score_screen);
        createItems();


    }

    void createItems(){
        stP=(TextView) findViewById(R.id.tV_st);
        ndP=(TextView) findViewById(R.id.tV_snd);
        thP=(TextView) findViewById(R.id.tV_th);
        stP1=(TextView) findViewById(R.id.tV_st1);
        ndP1=(TextView) findViewById(R.id.tV_snd1);
        thP1=(TextView) findViewById(R.id.tV_th1);
        stP2=(TextView) findViewById(R.id.tV_st2);
        ndP2=(TextView) findViewById(R.id.tV_snd2);
        thP2=(TextView) findViewById(R.id.tV_th2);
        dbScoreManager=new DBScoreManager(getApplicationContext());
        highscore=dbScoreManager.getAllScore();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        if (highscore.size()==0){
            stP.setText("<Empty>");
            ndP.setText("<Empty>");
            thP.setText("<Empty>");
            stP1.setText("<Empty>");
            ndP1.setText("<Empty>");
            thP1.setText("<Empty>");
            stP2.setText("<Empty>");
            ndP2.setText("<Empty>");
            thP2.setText("<Empty>");
        }
        else if(highscore.size()==1){
            stP.setText(String.valueOf(highscore.get(0).getScore()));
            ndP.setText("<Empty>");
            thP.setText("<Empty>");
            stP1.setText(String.valueOf(highscore.get(0).getTimePlay()));
            ndP1.setText("<Empty>");
            thP1.setText("<Empty>");
            String temp=dateFormat.format(highscore.get(0).getDate());
            stP2.setText(temp);
            ndP2.setText("<Empty>");
            thP2.setText("<Empty>");
        }
        else if(highscore.size()==2){
            stP.setText(String.valueOf(highscore.get(0).getScore()));
            ndP.setText(String.valueOf(highscore.get(1).getScore()));
            thP.setText("<Empty>");

            stP1.setText(String.valueOf(highscore.get(0).getTimePlay()));
            ndP1.setText(String.valueOf(highscore.get(1).getTimePlay()));
            thP1.setText("<Empty>");

            String temp=dateFormat.format(highscore.get(0).getDate());
            stP2.setText(temp);
            temp=dateFormat.format(highscore.get(1).getDate());
            ndP2.setText(temp);
            thP2.setText("<Empty>");
        }
        else {
            stP.setText(String.valueOf(highscore.get(0).getScore()));
            ndP.setText(String.valueOf(highscore.get(1).getScore()));
            thP.setText(String.valueOf(highscore.get(2).getScore()));

            stP1.setText(String.valueOf(highscore.get(0).getTimePlay()));
            ndP1.setText(String.valueOf(highscore.get(1).getTimePlay()));
            thP1.setText(String.valueOf(highscore.get(2).getTimePlay()));

            String temp=dateFormat.format(highscore.get(0).getDate());
            stP2.setText(temp);
            temp=dateFormat.format(highscore.get(1).getDate());
            ndP2.setText(temp);
            temp=dateFormat.format(highscore.get(2).getDate());
            thP2.setText(temp);
        }
    }
}
