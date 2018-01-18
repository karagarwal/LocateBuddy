package skinfonaut.locatebuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends Activity {
EditText userName,passWord;
    boolean log;
    String passwd,uname;
    private static Context mCtx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        if (NetworkCheck.isConnected(LoginActivity.this)==false){
            buildDialog(LoginActivity.this).show();
        }
        else {
            /*-----------------------------Shared Pref------------------------------*/
            log = SharedPrefManager.getInstance(LoginActivity.this).isLoggedin();
            if(log==true){

                uname=SharedPrefManager.getInstance(LoginActivity.this).getuname();
                passwd=SharedPrefManager.getInstance(LoginActivity.this).getpasswd();
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute("login",uname,passwd);

                //Toast.makeText(LoginActivity.this,"Welcome "+uname,Toast.LENGTH_SHORT).show();
            }

            /*-----------------------------------------------------------------*/
        }

        //ASSIGN IDs
        userName = (EditText) findViewById(R.id.etUsername);
        passWord = (EditText) findViewById(R.id.etPassword);
        TextView RegisterUser = (TextView) findViewById(R.id.RegisID);
        TextView ForgotPass = (TextView) findViewById(R.id.forgetID);
        Button login = (Button) findViewById(R.id.LoginBt);
        Button clos = (Button) findViewById(R.id.closeBt);


        //Perform operation on click button

        RegisterUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent moveToRegis = new Intent(LoginActivity.this, RegisterActivity.class);
                moveToRegis.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(moveToRegis);
                LoginActivity.this.finish();
            }
        });

        ForgotPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent moveToRegis = new Intent(LoginActivity.this, ForgetPass.class);
                moveToRegis.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(moveToRegis);
                LoginActivity.this.finish();
            }
        });

        clos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*finish();
                System.exit(0);*/
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("EXIT");
                builder.setMessage("Are you sure want to exit? ");
                builder.setCancelable(false);
                builder.setIcon(R.drawable.exit);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT >= 21)
                            finishAndRemoveTask();
                        else
                            finish();
                        System.exit(0);

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }




    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            android.net.NetworkInfo bt = cm.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()) || (bt != null && bt.isConnectedOrConnecting()))
                return true;
            else
                return false;
        } else
        return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please enable WiFi or Mobile Data to Continue.");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.no_network);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= 21)
                    finishAndRemoveTask();
                else
                    finish();
            }
        });
        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (NetworkCheck.isConnected(LoginActivity.this)==false) {
                    buildDialog(LoginActivity.this).show();
                }
                else{
                     /*-----------------------------Shared Pref------------------------------*/
                    log = SharedPrefManager.getInstance(LoginActivity.this).isLoggedin();
                    if(log==true){

                        uname=SharedPrefManager.getInstance(LoginActivity.this).getuname();
                        passwd=SharedPrefManager.getInstance(LoginActivity.this).getpasswd();
                        BackgroundWorker backgroundWorker = new BackgroundWorker(LoginActivity.this);
                        backgroundWorker.execute("login",uname,passwd);

                        //Toast.makeText(LoginActivity.this,"Welcome "+uname,Toast.LENGTH_SHORT).show();
                    }

            /*-----------------------------------------------------------------*/
                }
            }
        });

        return builder;
    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("EXIT");
        builder.setMessage("Are you sure want to exit ? ");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.exit);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onLogin(View view){

         String uname = userName.getText().toString();
         String passwd = passWord.getText().toString();
         String type = "login";

                if(uname.isEmpty() || passwd.isEmpty()){
                    Toast.makeText(this,"Please provide your Username and Password",Toast.LENGTH_SHORT).show();
                }
                else {
            /*Sending data to sharedPref file*/
                    SharedPrefManager.getInstance(LoginActivity.this).userLogin(uname,passwd);
            /*----------------------*/
                    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                    backgroundWorker.execute(type,uname,passwd);
                }
    }
}
