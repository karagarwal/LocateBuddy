package skinfonaut.locatebuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RegisterActivity extends Activity {
    EditText Name,userName, passWord,CpassWord,Contact,Email;
    Button regs,bck;
    String NameEntered,userNameEntered,passWordEntered,CpassWordEntered,contactEntered,EmailEntered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        //ASSIGN IDs
        Name = (EditText)  findViewById(R.id.editName);
        userName = (EditText)  findViewById(R.id.editUser);
        passWord = (EditText)  findViewById(R.id.editPass);
        CpassWord = (EditText)  findViewById(R.id.editCPass);
        Contact = (EditText)  findViewById(R.id.editCont);
        Email = (EditText)  findViewById(R.id.editmail);
        regs = (Button)  findViewById(R.id.RegisBt);
        bck = (Button) findViewById(R.id.BackBt);

        //Perform operation on click button
            bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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


    public void onReg(View view){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        NameEntered=Name.getText().toString();
        userNameEntered=userName.getText().toString();
        passWordEntered=passWord.getText().toString();
        CpassWordEntered=CpassWord.getText().toString();
        contactEntered=Contact.getText().toString();
        EmailEntered=Email.getText().toString();
        String type = "register";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);

        if(NameEntered.isEmpty() || userNameEntered.isEmpty() || passWordEntered.isEmpty() || CpassWordEntered.isEmpty() || contactEntered.isEmpty() || EmailEntered.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
        }
        else{
            if(NameEntered.contains("'")){
                Toast.makeText(RegisterActivity.this, "Invalid Name", Toast.LENGTH_SHORT).show();
            }else
            if(userNameEntered.length()<5){
                Toast.makeText(RegisterActivity.this, "Username atleast 5 character long", Toast.LENGTH_SHORT).show();
            }else
            if(userNameEntered.contains("'") || userNameEntered.contains(" ")){
                Toast.makeText(RegisterActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
            }else
            if(passWordEntered.length()<5){
                Toast.makeText(RegisterActivity.this, "Password atleast 5 character long", Toast.LENGTH_SHORT).show();
            }else
            if(!passWordEntered.equals(CpassWordEntered)){
                Toast.makeText(RegisterActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            }else {
                if (contactEntered.length() != 10) {
                    Toast.makeText(RegisterActivity.this, "Invalid Contact", Toast.LENGTH_SHORT).show();
                }if(!EmailEntered.matches(emailPattern)){
                    Toast.makeText(RegisterActivity.this, "Invalid Mail ID", Toast.LENGTH_SHORT).show();
                } else {
                         /* TODO: OTP validation work pending*/

                    backgroundWorker.execute(type, NameEntered, userNameEntered, passWordEntered, contactEntered,EmailEntered);
                }
            }
            }
    }
}
