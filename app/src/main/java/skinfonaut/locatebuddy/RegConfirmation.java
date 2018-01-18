package skinfonaut.locatebuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Process;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class RegConfirmation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reg_confirmation);
        Button clos = (Button) findViewById(R.id.closeBt);

        clos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*finish();
                System.exit(0);*/
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegConfirmation.this);
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

                       if (Build.VERSION.SDK_INT >= 21){
                            finishAndRemoveTask();
                       }
                        else{
                            finish();
                        }
                       }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });


    }
    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(RegConfirmation.this);
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
                if (Build.VERSION.SDK_INT >= 21)
                    finishAndRemoveTask();
                else
                    finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void goLogin(View view){

            Intent intent = new Intent(RegConfirmation.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            RegConfirmation.this.finish();

    }

}
