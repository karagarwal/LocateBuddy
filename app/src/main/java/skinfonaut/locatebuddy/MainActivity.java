package skinfonaut.locatebuddy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {
 private static int SPLASH_TIMEOUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

           new Handler().postDelayed(new Runnable(){
             @Override
             public void run() {
                 Intent homeIntent = new Intent(MainActivity.this, LoginActivity.class);
                 startActivity(homeIntent);
                 finish();
             }
         },SPLASH_TIMEOUT);
    }

 }
