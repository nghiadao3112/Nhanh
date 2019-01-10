package cc.ccds.testsql.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.List;
import java.util.Random;

//import cc.ccds.testsql.Algorithm.MyQuickSort;
import cc.ccds.testsql.Data.DBAccess;
import cc.ccds.testsql.Data.DBManager;
import cc.ccds.testsql.Data.DBScoreManager;
import cc.ccds.testsql.Quiz.Question;
import cc.ccds.testsql.R;

public class PlayActivity extends Activity {

    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    private Button buttonStart;

    private TextView textViewShowQuest;

    private ProgressBar progressBarTrueAnswer;
    private ArcProgress progressBarShowTime;

    private String[] testString=new String[4];
    private List<Question> questions;
    private DBAccess dbAccess ;
    private int position=0;

    private DBScoreManager dbScoreManager;


    private int count=0;
    private int max=0;
    private MyCountDownTimer myCountDownTimer;

    private Animation mBlink;
    private byte checkAnswer=0;
    private boolean checkState=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.play_screen_v2);


        createItems();
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                turnOn();
                buttonStart.setEnabled(false);
                createAMultipleChoice();
                myCountDownTimer = new MyCountDownTimer(30000, 1000);
                myCountDownTimer.start();
            }
        });

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer=1;
                buttonA.startAnimation(mBlink);
            }
        });
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer=2;
                buttonB.startAnimation(mBlink);
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer=3;
                buttonC.startAnimation(mBlink);

            }
        });
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer=4;
                buttonD.startAnimation(mBlink);
            }
        });





    }




    private void checkA(){
        if (String.valueOf(buttonA.getText())==testString[0]){
            count++;
            if (count>max){
                max=count;
            }
            progressBarTrueAnswer.setProgress(progressBarTrueAnswer.getProgress()+10);
            if (max==10){
                myCountDownTimer.cancel();
                myCountDownTimer.onFinish();
            }
        }
        else {
            progressBarTrueAnswer.setProgress(0);
            count=0;
        }
    }
    private void checkB(){
        if (String.valueOf(buttonB.getText())==testString[0]){
            count++;
            if (count>max){
                max=count;
            }
            progressBarTrueAnswer.setProgress(progressBarTrueAnswer.getProgress()+10);
            if (max==10){
                myCountDownTimer.cancel();
                myCountDownTimer.onFinish();
            }
        }
        else {
            progressBarTrueAnswer.setProgress(0);
            count=0;
        }
    }
    private void checkC(){
        if (String.valueOf(buttonC.getText())==testString[0]){
            count++;
            if (count>max){
                max=count;
            }
            progressBarTrueAnswer.setProgress(progressBarTrueAnswer.getProgress()+10);
            if (max==10){
                turnOff();
                myCountDownTimer.cancel();
                myCountDownTimer.onFinish();
            }
        }
        else {
            progressBarTrueAnswer.setProgress(0);
            count=0;
        }
    }
    private void checkD(){
        if (String.valueOf(buttonD.getText())==testString[0]){
            count++;
            if (count>max){
                max=count;
            }
            progressBarTrueAnswer.setProgress(progressBarTrueAnswer.getProgress()+10);
            if (max==10){
                turnOff();
                myCountDownTimer.cancel();
                myCountDownTimer.onFinish();
            }
        }
        else {
            progressBarTrueAnswer.setProgress(0);
            count=0;
        }
    }


    private void createItems(){
        mBlink= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinkanim);
        mBlink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                buttonA.setEnabled(false);
                buttonB.setEnabled(false);
                buttonD.setEnabled(false);
                buttonC.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (checkAnswer==1){
                    checkA();
                }
                else if (checkAnswer==2){
                   checkB();
                }
                else if (checkAnswer==3){
                    checkC();
                }
                else if (checkAnswer==4){
                   checkD();
                }
                position++;
                Boolean bool=createAMultipleChoice();
                if(bool==false) {
                    textViewShowQuest.setText("Out of questions");
                }
                else {
                        turnOn();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        buttonA=(Button) findViewById(R.id.btn_A);
        buttonB=(Button) findViewById(R.id.btn_B);
        buttonC=(Button) findViewById(R.id.btn_C);
        buttonD=(Button) findViewById(R.id.btn_D);
        buttonStart=(Button) findViewById(R.id.btn_start);

        textViewShowQuest=(TextView) findViewById(R.id.tv_ShowQuest);

        progressBarTrueAnswer=(ProgressBar)findViewById(R.id.pB_True_Answer);

        progressBarShowTime=(ArcProgress)findViewById(R.id.pB_Show_Time);
        progressBarShowTime.setProgress(0);
        progressBarShowTime.setMax(30);

        dbScoreManager=new DBScoreManager(getApplicationContext());

        Intent intent =getIntent();
        String databaseName=intent.getStringExtra(ChooseActivity.DATABASE_NAME);
        dbAccess=dbAccess.getInstance(getApplicationContext(),databaseName);
        questions=dbAccess.getAllQuestion();
        createRandomListQuest();
    }

    private boolean createAMultipleChoice(){

        if (position<(questions.size()-1) ) {

            Random random = new Random();
            Question question;
            question = questions.get(position);

            int i, j, temp;
            testString[0] = (String) question.getTrueAnswer();
            testString[1] = (String) question.getWrongAnswer1();
            testString[2] = (String) question.getWrongAnswer2();
            testString[3] = (String) question.getWrongAnswer3();
            int[] randomPostion = {0, 1, 2, 3};
            int[] randomValues = new int[4];

            for (i = 0; i < 4; i++) {
                randomValues[i] = random.nextInt();
            }

            for (i = 0; i < 3; i++) {
                for (j = i + 1; j < 4; j++) {
                    if (randomValues[i] > randomValues[j]) {
                        temp = randomValues[i];
                        randomValues[i] = randomValues[j];
                        randomValues[j] = temp;
                        temp = randomPostion[i];
                        randomPostion[i] = randomPostion[j];
                        randomPostion[j] = temp;
                    }
                }
            }

            textViewShowQuest.setText((String)question.getQuestion());
            buttonA.setText(testString[randomPostion[0]]);
            buttonB.setText(testString[randomPostion[1]]);
            buttonC.setText(testString[randomPostion[2]]);
            buttonD.setText(testString[randomPostion[3]]);
            return true;
        }
        return false;
    }

    private void turnOff(){
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
    }

    private void turnOn(){
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
    }

    private void exchangeQuestion(int i,int j){
        Question temp=questions.get(i);
        questions.set(i,questions.get(j));
        questions.set(j,temp);
    }


    private void createRandomListQuest(){
        Random random=new Random();
        int i=0;
        int minPosition;
        int maxPosition=questions.size()-1;
        int swapPostion;
        while (i<questions.size()-2) {
            minPosition = i + 1;
            swapPostion = minPosition + random.nextInt(maxPosition - minPosition);
            exchangeQuestion(i, swapPostion);
            i++;
        }
    }


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);
            progressBarShowTime.setProgress(progress);

        }

        @Override
        public void onFinish() {
            String title;
            String messageDialog;
            if (max==10){
                title="Very Impressive 10 true answers!!!!!!!!!!!!!!!!!!!!!!!!!";
                messageDialog="Another game right :) ?????????????";

            }
            else {
                title="End Time!!!!!!!!!!!!!!!!!!!!";
                messageDialog="You have "+(max)+" true answer, Continue or Not ???";
            }
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(PlayActivity.this   );
            alertDialog
                    .setTitle(title)
                    .setMessage(messageDialog)
                    .setPositiveButton("I need some water!!!!!!!!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            position++;
                            int timePlay=31-progressBarShowTime.getProgress();
                            if (max!=0) {
                                dbScoreManager.updateScore(max, timePlay);
                            }
                            PlayActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("Let's fight!!!!!!!!!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            position++;
                            turnOff();
                            int timePlay=31-progressBarShowTime.getProgress();
                            if (max!=0) {
                                dbScoreManager.updateScore(max, timePlay);
                            }
                            progressBarShowTime.setProgress(0);
                            progressBarTrueAnswer.setProgress(0);
                            max=0;
                            count=0;
                            buttonStart.setEnabled(true);
                        }
                    });

            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }


}




