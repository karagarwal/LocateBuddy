package skinfonaut.locatebuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by AGARWAL-PC on 25-06-2017.
 */

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    String UserName;
    String user_name;
        Context context;
            private  ProgressDialog progressDialog;
            AlertDialog alertDialog;
            private static String title;

           public static boolean flg;

        BackgroundWorker (Context ctx)
        {
            context = ctx;
        }

        private void showProgressDialog(String mgs)
        {
            progressDialog = new ProgressDialog(context);
            if(this.context instanceof LoginActivity){
                progressDialog.setTitle("Login Status");
                progressDialog.setIcon(R.drawable.singin);
            }else
            if(this.context instanceof RegisterActivity){
                progressDialog.setTitle("Register Status");
                progressDialog.setIcon(R.drawable.regsiter);
            }


        progressDialog.setMessage(mgs); // message
        progressDialog.setCancelable(false);

        progressDialog.show();
    }

    @Override
        protected String doInBackground(String... params) {
        String type = params[0];

          String login_url = "http://skinfonauts.com/LB/login.php";
          String register_url = "http://skinfonauts.com/LB/register.php";
          String locate_url = "http://skinfonauts.com/LB/locate.php";

           if(type.equals("login")){
               try {

                      user_name = params[1];

                     String password = params[2];

                     URL url = new URL (login_url);

                     HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                     httpURLConnection.setRequestMethod("POST");
                     httpURLConnection.setDoOutput(true);
                     httpURLConnection.setDoInput(true);

                     OutputStream outputStream = httpURLConnection.getOutputStream();

                     BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                     String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                             +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

                       bufferedWriter.write(post_data);
                       bufferedWriter.flush();
                       bufferedWriter.close();
                       outputStream.close();

                   InputStream inputStream = httpURLConnection.getInputStream();
                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                   String result="";
                   String line="";
                   while((line = bufferedReader.readLine())!=null){
                       result += line;
                   }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                  return result;

               } catch (MalformedURLException e) {
                   e.printStackTrace();
               }
               catch (IOException e){
                   e.printStackTrace();
               }
           }
           else if(type.equals("register")){
               try {
                   String name = params[1];
                   String user_name = params[2];
                   UserName = user_name;
                   String password = params[3];
                   String contact = params[4];
                   String mail_id = params[5];

                   //TODO : otp validation

                        URL url1 = new URL (register_url);
                         title = "reg";
                         HttpURLConnection httpURLConnection1 = (HttpURLConnection)url1.openConnection();
                         httpURLConnection1.setRequestMethod("POST");
                         httpURLConnection1.setDoOutput(true);
                         httpURLConnection1.setDoInput(true);

                         OutputStream outputStream1 = httpURLConnection1.getOutputStream();
                         BufferedWriter bufferedWriter1 = new BufferedWriter(new OutputStreamWriter(outputStream1,"UTF-8"));
                         String post_data1 = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                                 +URLEncoder.encode("uname","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                                 +URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                                 +URLEncoder.encode("conct","UTF-8")+"="+URLEncoder.encode(contact,"UTF-8")+"&"
                                 +URLEncoder.encode("mailid","UTF-8")+"="+URLEncoder.encode(mail_id,"UTF-8");

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
           } else if(type.equals("newLogin")){
               try {

                   String latitude = params[1];
                   String longitude = params[2];


                   URL url = new URL (locate_url);

                   HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                   httpURLConnection.setRequestMethod("POST");
                   httpURLConnection.setDoOutput(true);
                   httpURLConnection.setDoInput(true);

                   OutputStream outputStream = httpURLConnection.getOutputStream();

                   BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                   String post_data = URLEncoder.encode("latitude","UTF-8")+"="+URLEncoder.encode(latitude,"UTF-8")+"&"
                           +URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8")+"&"
                           +URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");

                   bufferedWriter.write(post_data);
                   bufferedWriter.flush();
                   bufferedWriter.close();
                   outputStream.close();

                   InputStream inputStream = httpURLConnection.getInputStream();
                   BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                   String result="";
                   String line="";
                   while((line = bufferedReader.readLine())!=null){
                       result += line;
                   }

                   bufferedReader.close();
                   inputStream.close();
                   httpURLConnection.disconnect();

                   return result;

               } catch (MalformedURLException e) {
                   e.printStackTrace();
               }
               catch (IOException e){
                   e.printStackTrace();
               }
           }

        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
      //  alertDialog.setTitle("Login Status ");
          showProgressDialog("This may take a while Please Wait...");
    }

    @Override

    protected void onPostExecute(String result) {

        alertDialog.setMessage(result);
        alertDialog.show();

        if(progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
        if(result.contains("welcome")){
                alertDialog.dismiss();

            Toast.makeText(context,"Welcome "+SharedPrefManager.getInstance(context).getuname(),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context,UsersArea.class);
            intent.putExtra("user",user_name);
            context.startActivity(intent);
            ((Activity)context).finish();
          }
         else if(result.contains("Successfully")){
            alertDialog.dismiss();

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
                    String username = UserName;
                    /*----------------------------------------*/
                    String type = "otp";
                    LatLngWork latLangWork = new LatLngWork(context);
                    latLangWork.execute(type,username,code);

                }
            });

            final AlertDialog alertDialog = alert.create();
            alertDialog.setCancelable(false);
            alertDialog.show();

        }
        else if(result.contains("http")){

            alertDialog.dismiss();
            alertDialog.setMessage("Something Went wrong. Try again later!!");
            alertDialog.setTitle("Error");
            alertDialog.show();
        }else if(result.contains("Invalid Password for user")){

            String uname=SharedPrefManager.getInstance(context).getuname();
             EditText userName = (EditText) ((Activity)context).findViewById(R.id.etUsername);
            EditText passwd = (EditText) ((Activity)context).findViewById(R.id.etPassword);
         userName.setText(uname);
            passwd.requestFocus();

        }else if(result.contains("")){

        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

