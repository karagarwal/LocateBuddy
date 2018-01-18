package skinfonaut.locatebuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by AGARWAL-PC on 25-06-2017.
 */

public class LatLngWork extends AsyncTask<String,Void,String> {

    Context context;
    String[] numb_arr;
    String MobileNumb;
    String[] phone_arr;

    private static String title;


    LatLngWork(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String locate_url = "http://skinfonauts.com/LB/locate.php";
        String confirm_url = "http://skinfonauts.com/LB/confirm.php";
        String help_url = "http://skinfonauts.com/LB/help.php";
        String contact_url = "http://skinfonauts.com/LB/contact.php";
        String Feed_url = "http://skinfonauts.com/LB/feedback.php";
        String FPass_url = "http://skinfonauts.com/LB/Fpass.php";
        String location_url = "http://skinfonauts.com/LB/location.php";
        String logout_url = "http://skinfonauts.com/LB/logout.php";

        if (type.equals("newLogin")) {              //new Login Condition
            try {

                String latitude = params[1];
                String longitude = params[2];
                String username= params[3];

                URL url = new URL(locate_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));



                String post_data = URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8")+"&"
                        + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8")+"&"
                        + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
        if (type.equals("logout")) {                    //Logout Condition
            try {

                String username= params[1];

                URL url = new URL(logout_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         else if(type.equals("otp")){                           //OTP Request on Registration
            try {

                String username = params[1];
                String code = params[2];

                URL url2 = new URL(confirm_url);
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
                httpURLConnection2.setRequestMethod("POST");
                httpURLConnection2.setDoOutput(true);
                httpURLConnection2.setDoInput(true);

                OutputStream outputStream2 = httpURLConnection2.getOutputStream();

                BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(outputStream2, "UTF-8"));

                String post_data2 = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("otp", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8");

                bufferedWriter2.write(post_data2);
                bufferedWriter2.flush();
                bufferedWriter2.close();
                outputStream2.close();

                InputStream inputStream1 = httpURLConnection2.getInputStream();
                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1, "iso-8859-1"));

                String result1 = "";
                String line1 = "";
                while ((line1 = bufferedReader1.readLine()) != null) {
                    result1 += line1;
                }

                bufferedReader1.close();
                inputStream1.close();
                httpURLConnection2.disconnect();

                return result1;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
            }catch (Exception e){
                Log.d("phperror",Log.getStackTraceString(e));
            }

        }else
             if(type.equals("Fotp")){               //Forget Password
                 try {

                     String code = params[1];
                     MobileNumb = params[2];

                     URL url2 = new URL(FPass_url);
                     HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
                     httpURLConnection2.setRequestMethod("POST");
                     httpURLConnection2.setDoOutput(true);
                     httpURLConnection2.setDoInput(true);

                     OutputStream outputStream2 = httpURLConnection2.getOutputStream();

                     BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(outputStream2, "UTF-8"));

                     String post_data2 = URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8") + "&"
                             + URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(MobileNumb, "UTF-8");

                     bufferedWriter2.write(post_data2);
                     bufferedWriter2.flush();
                     bufferedWriter2.close();
                     outputStream2.close();

                     InputStream inputStream1 = httpURLConnection2.getInputStream();
                     BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1, "iso-8859-1"));

                     String result1 = "";
                     String line1 = "";
                     while ((line1 = bufferedReader1.readLine()) != null) {
                         result1 += line1;
                     }

                     bufferedReader1.close();
                     inputStream1.close();
                     httpURLConnection2.disconnect();

                     return result1;
                 } catch (UnsupportedEncodingException e) {
                     e.printStackTrace();
                 } catch (ProtocolException e) {
                     e.printStackTrace();
                 } catch (MalformedURLException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                 }catch (Exception e){
                     Log.d("phperror",Log.getStackTraceString(e));
                 }
             }
        else
            if(type.equals("contact")){         //Current Login Contact
                try{

                    String username = params[1];

                    URL url2 = new URL(contact_url);
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
                    httpURLConnection2.setRequestMethod("POST");
                    httpURLConnection2.setDoOutput(true);
                    httpURLConnection2.setDoInput(true);

                    OutputStream outputStream2 = httpURLConnection2.getOutputStream();

                    BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(outputStream2, "UTF-8"));

                    String post_data2 = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");

                    bufferedWriter2.write(post_data2);
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                    outputStream2.close();

                    InputStream inputStream1 = httpURLConnection2.getInputStream();
                    BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1, "iso-8859-1"));

                    String result1 = "";
                    String line1 = "";
                    while ((line1 = bufferedReader1.readLine()) != null) {
                        result1 += line1;
                    }

                    bufferedReader1.close();
                    inputStream1.close();
                    httpURLConnection2.disconnect();


                    return result1;

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
            if(type.equals("markBlue")){         //Getting location for Blue Marker of common contact
                try{

                    String phone = params[1];

                    URL url2 = new URL(location_url);
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
                    httpURLConnection2.setRequestMethod("POST");
                    httpURLConnection2.setDoOutput(true);
                    httpURLConnection2.setDoInput(true);

                    OutputStream outputStream2 = httpURLConnection2.getOutputStream();

                    BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(outputStream2, "UTF-8"));

                    String post_data2 = URLEncoder.encode("phonenum", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");

                    bufferedWriter2.write(post_data2);
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                    outputStream2.close();

                    InputStream inputStream1 = httpURLConnection2.getInputStream();
                    BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1, "iso-8859-1"));

                    String result1 = "";
                    String line1 = "";
                    while ((line1 = bufferedReader1.readLine()) != null) {
                        result1 += line1;
                    }

                    bufferedReader1.close();
                    inputStream1.close();
                    httpURLConnection2.disconnect();


                    return result1;

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (type.equals("User_Feedback")) {              //new Login Condition
                try {

                    String subj = params[1];
                    String messg = params[2];


                    URL url = new URL(Feed_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));



                    String post_data = URLEncoder.encode("subj", "UTF-8") + "=" + URLEncoder.encode(subj, "UTF-8")+"&"
                            + URLEncoder.encode("feedbck", "UTF-8") + "=" + URLEncoder.encode(messg, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if (type.equals("type1") || type.equals("type2") || type.equals("type3")) {              //Help Message
                try {

                    String ask_by = params[1];
                    String send_to = params[2];
                    String conct_to = params[3];


                    URL url = new URL(help_url);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")+"&"
                            + URLEncoder.encode("askby", "UTF-8") + "=" + URLEncoder.encode(ask_by, "UTF-8")+"&"
                            + URLEncoder.encode("sendto", "UTF-8") + "=" + URLEncoder.encode(send_to, "UTF-8")+"&"
                            + URLEncoder.encode("conctto", "UTF-8") + "=" + URLEncoder.encode(conct_to, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }



        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equalsIgnoreCase("success otp")){
            context.startActivity(new Intent(context,RegConfirmation.class));
            Toast.makeText(context,"OTP Match",Toast.LENGTH_SHORT).show();
            ((Activity)context).finish();
        }
        else if (result.equalsIgnoreCase("OTP Mismatch")){
            Toast.makeText(context,"OTP Mismatch Try again",Toast.LENGTH_SHORT).show();
        }
       else if(result.contains("numbers")){
            super.onPostExecute(result);

        }
        else
            if(result.contains("change Pass")){
                Intent intent = new Intent(context,ChangePass.class);
                intent.putExtra("mobile",MobileNumb);
                context.startActivity(intent);
                Toast.makeText(context,"OTP Match",Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
            }else
                 if(result.contains("feedbackPass")){
                     Toast.makeText(context,"Feedback Send",Toast.LENGTH_SHORT).show();
                 }
            else
                if(result.contains("feedbackFail")){
                     Toast.makeText(context,"Something Went Wrong, Please try again Later",Toast.LENGTH_SHORT).show();
                 }
            else
                if(result.contains("Error in help send")){
                    Toast.makeText(context,"Something Went Wrong, Please try again Later",Toast.LENGTH_LONG).show();
                }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}