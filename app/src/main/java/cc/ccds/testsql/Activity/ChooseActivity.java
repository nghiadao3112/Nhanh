package cc.ccds.testsql.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import cc.ccds.testsql.Adapter.CustomAdapterForDatabase;
import cc.ccds.testsql.Data.DBAccess;
import cc.ccds.testsql.R;

import static cc.ccds.testsql.Activity.MainActivity.*;

public class ChooseActivity extends Activity {
    private Button buttonCreate;
    private ListView listViewDatabase;
    private CustomAdapterForDatabase customAdapterForDatabase;
    private List<String> databaseList;
    private DBAccess dbAccess;
    private Byte chooseMode;
    private Boolean checkDelete=false;
    private Animation mShake;
    private Animation mBlink;
    public static final String DATABASE_NAME = "databaseName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_screen);
        createItems();

        databaseList=dbAccess.getAllDatabases();
        setAdapter();

        if (chooseMode==2){
            buttonCreate.setEnabled(true);
        }

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBlink.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent =new Intent(ChooseActivity.this,DatabaseActivity.class);
                        int temp=dbAccess.getID(databaseList.get(databaseList.size()-1));
                        String newName="Quest"+temp;
                        intent.putExtra(DATABASE_NAME,newName);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                v.startAnimation(mBlink);
            }
        });
        listViewDatabase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos=position;
                mShake.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (chooseMode==1) {
                            Intent intent = new Intent(ChooseActivity.this, PlayActivity.class);
                            intent.putExtra(DATABASE_NAME, databaseList.get(pos));
                            startActivity(intent);
                        }
                        else {
                            AlertDialog.Builder alertDialog=new AlertDialog.Builder(ChooseActivity.this   );
                            alertDialog
                                    .setTitle("Go Go Go!!!!!!!!!")
                                    .setMessage("Do some thingggggggg?????????????")
                                    .setPositiveButton("Fix this !!!!!!", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ChooseActivity.this, DatabaseActivity.class);
                                            intent.putExtra(DATABASE_NAME, databaseList.get(pos));
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("Cancel!!!!!", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setNeutralButton("Delete this!!!!!!!!!", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String string =databaseList.get(pos);
                                            dbAccess.deleteDatabaseName(string);
                                            updateListViewDatabase();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(mShake);

            }
        });

    }

    private void createItems(){
        mShake= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shakeanim);
        mBlink= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinkanim);
        buttonCreate=(Button) findViewById(R.id.btn_create_database);

        listViewDatabase=(ListView) findViewById(R.id.lv_all_databases);
        dbAccess=dbAccess.getInstance(getApplicationContext(),"manager_databases.db");
        Intent intent=getIntent();
        chooseMode=intent.getByteExtra(MainActivity.CHOOSE_MODE,(byte) 0);
        if (chooseMode==1){
            buttonCreate.setVisibility(View.GONE);
        }


    }

    private void setAdapter(){
        if (customAdapterForDatabase==null){
            customAdapterForDatabase=new CustomAdapterForDatabase(this,R.layout.list_view_database,databaseList);
            listViewDatabase.setAdapter(customAdapterForDatabase);
        }
        else {
            customAdapterForDatabase.notifyDataSetChanged();
            listViewDatabase.setSelection(customAdapterForDatabase.getCount()-1);
        }
    }
    private void updateListViewDatabase(){
        databaseList.clear();
        databaseList.addAll(dbAccess.getAllDatabases());
        if (customAdapterForDatabase!=null){
            customAdapterForDatabase.notifyDataSetChanged();
        }

    }
}
