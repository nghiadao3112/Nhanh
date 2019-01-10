package cc.ccds.testsql.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ohoussein.playpause.PlayPauseView;

import java.util.List;

import cc.ccds.testsql.Adapter.CustomAdapter;
import cc.ccds.testsql.Data.DBManager;
import cc.ccds.testsql.Music.myMusic;
import cc.ccds.testsql.Quiz.Question;
import cc.ccds.testsql.R;

public class MainActivity extends Activity  {

    private Button buttonPlay;
    private Button buttonCreate;
    private Button buttonScore;
    //private PlayPauseView buttonMusic;

    private boolean musicStatus=false;

    public static final String CHOOSE_MODE="MODE";

    private Animation mShake;
    private byte checkButton=0;
    private myMusic mySong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_screen_v2);

        createItems();

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton=1;
                buttonPlay.startAnimation(mShake);

            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton=2;
                buttonCreate.startAnimation(mShake);

            }
        });
        buttonScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton=3;
                buttonScore.startAnimation(mShake);

            }
        });

        /*buttonMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicStatus==true){
                    mySong.pause();
                    musicStatus=false;
                }
                else {
                    mySong.start();
                    musicStatus=true;
                }
                buttonMusic.toggle();
            }
        });*/

    }
    private void createItems(){
        mShake= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shakeanim);
        mShake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (checkButton==1){
                    Intent intent=new Intent(MainActivity.this,ChooseActivity.class);
                    intent.putExtra(CHOOSE_MODE,(byte) 1);
                    startActivity(intent);
                }
                else if(checkButton==2){
                    Intent intent=new Intent(MainActivity.this,ChooseActivity.class);
                    intent.putExtra(CHOOSE_MODE,(byte) 2);
                    startActivity(intent);
                }
                else if(checkButton==3){
                    Intent intent=new Intent(MainActivity.this,ScoreActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        buttonPlay=(Button) findViewById(R.id.btn_play);
        buttonCreate=(Button) findViewById(R.id.btn_create);
        //buttonMusic=(PlayPauseView) findViewById(R.id.btn_play_pause_music);
        buttonScore=(Button) findViewById(R.id.btn_score);
        mySong.setContext(this);
        mySong.playMusic(R.raw.menu_sound);
    }

    /*private void loopSong(){
        if (mySong==null){
            mySong= MediaPlayer.create(MainActivity.this, R.raw.menu_sound);
            mySong.setLooping(true);
            mySong.start();
        }
    }*/
}
