package skinfonaut.locatebuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class ChangePass extends Activity {
    String data;
    EditText editText1,editText2;
    String string1,string2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_change_pass);



        editText1 = (EditText)findViewById(R.id.Pass);
        editText2 = (EditText)findViewById(R.id.CPass);
    }


    public void go_change(View view){


        string1 = editText1.getText().toString();
        string2 = editText2.getText().toString();

       if(string1.isEmpty() || string2.isEmpty()){
            Toast.makeText(ChangePass.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
        }
        else if(string1.length()<5 || string2.length()<5){
           Toast.makeText(ChangePass.this,"Atleast 5 Character Long",Toast.LENGTH_SHORT).show();
       } else
           if (!string1.equals(string2)){
           Toast.makeText(ChangePass.this,"Password not Match",Toast.LENGTH_SHORT).show();
        }
        else {
           // TODO : change Pass IN DB
                  NewPass newPass = new NewPass(this);
                     newPass.execute(string1,data);
        }
    }


    class NewPass extends AsyncTask<String,Void,String> {

        private ProgressDialog progressDialog;
        AlertDialog alertDialog;
        Context context;

        NewPass (Context ctx)
        {
            context = ctx;
        }

       @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Changing Password");
            progressDialog.setMessage("Please Wait...."); // message
            progressDialog.setCancelable(false);

            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            data =  getIntent().getStringExtra("mobile");

            String F_Pass = "http://skinfonauts.com/LB/Newpass.php";
            try {
                String pass = params[0];
                String mob = params[1];


                URL url1 = new URL (F_Pass);

                HttpURLConnection httpURLConnection1 = (HttpURLConnection)url1.openConnection();
                httpURLConnection1.setRequestMethod("POST");
                httpURLConnection1.setDoOutput(true);
                httpURLConnection1.setDoInput(true);

                OutputStream outputStream1 = httpURLConnection1.getOutputStream();
                BufferedWriter bufferedWriter1 = new BufferedWriter(new OutputStreamWriter(outputStream1,"UTF-8"));
                String post_data1 = URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+"&"+
                                    URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(data,"UTF-8");

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
                Toast.makeText(ChangePass.this, "Something went Wrong. Please Try Again Later!!", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.contains("Successfully Change")){

                progressDialog.dismiss();
                context.startActivity(new Intent(context,LoginActivity.class));
                Toast.makeText(ChangePass.this,"Password Changed Successfully",Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
            }
            else{
                Toast.makeText(ChangePass.this,s,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
    }

}
