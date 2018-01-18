package skinfonaut.locatebuddy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AGARWAL-PC on 02-07-2017.
 */

public class SharedPrefManager {
        private  static SharedPrefManager mInstances;
        private static Context mCtx;




                private SharedPrefManager(Context context){
                    mCtx = context;
                }

                public static synchronized SharedPrefManager getInstance(Context context){
                    if (mInstances == null){
                        mInstances = new SharedPrefManager(context);
                    }
                    return  mInstances;
                }

                public boolean userLogin(String username,String pass){
                    SharedPreferences sharedPreferences = mCtx.getSharedPreferences("UserLogInfo",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username",username);
                    editor.putString("password",pass);
                    editor.apply();
                    return  true;
                }
                public  boolean isLoggedin(){
                    SharedPreferences sharedPreferences = mCtx.getSharedPreferences("UserLogInfo",Context.MODE_PRIVATE);
                    if(sharedPreferences.getString("username",null)!=null){
                            return true;
                    }
                    return false;
                }
                public boolean logout(){
                    SharedPreferences sharedPreferences = mCtx.getSharedPreferences("UserLogInfo",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    return true;
                }

    public String getuname() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences("UserLogInfo",Context.MODE_PRIVATE);
        String un=sharedPreferences.getString("username","");;
        return  un;
    }

    public String getpasswd(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences("UserLogInfo",Context.MODE_PRIVATE);
        String pw=sharedPreferences.getString("password","");
        return pw;

    }
}
