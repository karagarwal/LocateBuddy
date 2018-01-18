package skinfonaut.locatebuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class user_feedback extends AppCompatActivity {
EditText subj,messg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_feedback);
        subj = (EditText) findViewById(R.id.etsubject);
        messg = (EditText) findViewById(R.id.Message);
    }
    public void onSend(View view){


          String s1=subj.getText().toString();
           String m1=messg.getText().toString();

           if(s1.isEmpty() || m1.isEmpty()){
               Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show();
           }else{
               String type = "User_Feedback";
               LatLngWork latLangWork = new LatLngWork(this);
               latLangWork.execute(type,s1,m1);
               finish();
           }



        //TODO: send feedback

    }

    public void onDiscard(View view){
        messg.getText().clear();
        subj.getText().clear();
    }
}




