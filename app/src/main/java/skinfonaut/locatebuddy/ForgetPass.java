package skinfonaut.locatebuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

public class ForgetPass extends Activity {

     EditText number;
    String numb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_forget_pass);

        number =(EditText)findViewById(R.id.mobile);

    }

    public void FPass_Action(View view){

        numb=number.getText().toString();

                if(numb.isEmpty() || numb.length()<10){
                    Toast.makeText(ForgetPass.this,"Please Enter Correct Mobile Number",Toast.LENGTH_SHORT).show();
                }
                else {
                    FPass fpass = new FPass(this);
                    fpass.execute(numb);
                }
    }




    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPass.this);
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



    class FPass extends AsyncTask<String,Void,String>{

        private  ProgressDialog progressDialog;
        AlertDialog alertDialog;
        Context context;

        FPass (Context ctx)
        {
            context = ctx;
        }

        private void showProgressDialog(String mgs)
        {

        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Sending Request");
            progressDialog.setMessage("Please Wait"); // message
            progressDialog.setCancelable(false);

            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String F_Pass = "http://skinfonauts.com/LB/pass.php";
                try {
                    String numb = params[0];

                    //TODO : otp validation


                    URL url1 = new URL (F_Pass);

                    HttpURLConnection httpURLConnection1 = (HttpURLConnection)url1.openConnection();
                    httpURLConnection1.setRequestMethod("POST");
                    httpURLConnection1.setDoOutput(true);
                    httpURLConnection1.setDoInput(true);

                    OutputStream outputStream1 = httpURLConnection1.getOutputStream();
                    BufferedWriter bufferedWriter1 = new BufferedWriter(new OutputStreamWriter(outputStream1,"UTF-8"));
                    String post_data1 = URLEncoder.encode("numb","UTF-8")+"="+URLEncoder.encode(numb,"UTF-8");

                    bufferedWriter1.write(post_data1);
                    bufferedWriter1.flush();
                    bufferedWriter1.close();
                    outputStream1.close();

                    InputStream inputStream1 = httpURLConnection1.getInputStream();
                    BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1,"iso-8859-1"));
                    String result1="";
                    String line1="";
                    while((line1 = bufferedReader1.readLine())!=null){
                        result1 += line1;
                    }
                    bufferedReader1.close();
                    inputStream1.close();
                    httpURLConnection1.disconnect();
                    return result1;

                    /*----------------------------------------*/

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                catch (Exception e){
                    Toast.makeText(ForgetPass.this, "Something went Wrong. Please Try Again Later!!", Toast.LENGTH_SHORT).show();
                }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.contains("Successfully")){
                progressDialog.dismiss();

            /*-----------------------------------*/

                LayoutInflater li = LayoutInflater.from(context);
                View confirmDialog = li.inflate(R.layout.activity_dialg_confirm, null);
                Button btn = (Button)confirmDialog.findViewById(R.id.butConfirm);
                final EditText otp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setView(confirmDialog);


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String code = otp.getText().toString();

                    /*----------------------------------------*/

                        if(code.isEmpty() || code.length()<6){
                            Toast.makeText(ForgetPass.this,"Enter Correct Otp",Toast.LENGTH_SHORT).show();
                        }
                        else{
                        String type = "Fotp";
                        LatLngWork latLangWork = new LatLngWork(context);
                        latLangWork.execute(type,code,numb);
                        }

                    }
                });

                final AlertDialog alertDialog = alert.create();
                alertDialog.setCancelable(false);
                alertDialog.show();

            }
            else{
                Toast.makeText(ForgetPass.this,s,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
    }
}


