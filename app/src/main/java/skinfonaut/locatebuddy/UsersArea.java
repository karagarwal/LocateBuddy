package skinfonaut.locatebuddy;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class UsersArea extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_PHONE_CALL = 1;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public GoogleMap mMap,mMap2;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;

    LocationManager locationManager;
    String log,U_a,U_s;
    String voice="";
    String server_contact="";
    String cont1[];
    Double answer;
    String cont2[];
    String nmlaln[]=null;
    String locate;
    ZoomControls zoom;
    List<String> conct = new ArrayList<String>();
    Button chngView;
    Button clos;
    private Intent intent_call;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){

                case R.id.action_refresh:

                            return true;
                case R.id.action_settings:
                        startActivity(new Intent(this,SettingsActivity.class));
                            return true;

                case R.id.action_logout:

                    final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
                    builder.setTitle("Logout");
                    builder.setMessage("Are you sure want to logout? ");
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
                           UsersArea.this.finish();
                            user_logout(log);
                             startActivity(new Intent(UsersArea.this,LoginActivity.class));

                           /*
                           * TODO: clear Shared Pref..
                           * */
                           SharedPrefManager.getInstance(UsersArea.this).logout();
                        }
                    });
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                            return true;
                case R.id.action_feedback:
                    startActivity(new Intent(this,user_feedback.class));
                    return true;

                case R.id.action_about:
                        startActivity(new Intent(this,AboutUs.class));
                        return true;

             }
        return super.onOptionsItemSelected(item);
    }

    /*------------------------Voice SOS Start------------------------------*/
    private void promptSpeechInput() {

          Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");

        //Constants for supporting speech recognition through starting an Intent

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something");

        try{
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            Toast.makeText(UsersArea.this,"Speak HELP/FIRE/MEDICAL",Toast.LENGTH_SHORT).show();
        }
        catch (ActivityNotFoundException a){
            Toast.makeText(this,"Your Device does not support Google Voice Input",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQ_CODE_SPEECH_INPUT:
            {
                if (resultCode == RESULT_OK && null != data)
                {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    voice = result.get(0);
                      if(voice.contains("help")){
                          final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
                          builder.setTitle("SOS Calls");
                          builder.setMessage("your 'HELP' call detected would you like me to place call to nearest Police Station ?");
                          builder.setIcon(R.drawable.sos);
                          builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialogInterface, int i) {
                                  dialogInterface.cancel();
                              }
                          });
                          builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialogInterface, int i) {
                              Toast.makeText(UsersArea.this,"Calling to nearest Police Station",Toast.LENGTH_SHORT).show();
                               /* TODO: Find nearest Police Station
                                *
                                *
                                *
                                * */
                                  call("02942655316");
                              }
                          });
                          final AlertDialog alertDialog = builder.create();
                          alertDialog.show();
                      }
                else
                    if(voice.contains("medical")){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
                        builder.setTitle("SOS Calls");
                        builder.setMessage("Your 'Medical' call detected would you like me to place call to nearest Hospital?");
                        builder.setIcon(R.drawable.sos);
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(UsersArea.this,"Calling to nearest Hospital",Toast.LENGTH_SHORT).show();
                                /* TODO: Find nearest Hospital
                                *
                                *
                                *
                                * */
                                call("9461208086");
                            }
                        });
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else
                    if(voice.contains("fire")){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
                        builder.setTitle("SOS Calls");
                        builder.setMessage("Your 'Fire' call detected would you like me to place call to nearest Fire Station?");
                        builder.setIcon(R.drawable.sos);
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(UsersArea.this,"Calling to nearest Fire Station",Toast.LENGTH_SHORT).show();
                                /* TODO: Find nearest Fire Station
                                *
                                *
                                *
                                * */

                                call("02942414111");
                            }
                        });
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                 else{
                        Toast.makeText(this,"No SOS call Detected",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }

        }
    }
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (intent == null) {
            intent = new Intent();
        }
        super.startActivityForResult(intent, requestCode);
    }
    private void call(String str) {
        intent_call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+str));
        try {
             if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent_call);
            } else {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
            }
        }
         catch (android.content.ActivityNotFoundException ex){
              Toast.makeText(this,"Calling Not supported",Toast.LENGTH_SHORT).show();
           }
           catch(Exception ex){
               ex.printStackTrace();
             /*  final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
               builder.setMessage(ex.toString());
               AlertDialog alertDialog = builder.create();
               alertDialog.show();*/

           }
    }
    /*------------------------Voice SOS END------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String username  = getIntent().getStringExtra("user");
        U_a  = getIntent().getStringExtra("user");

      log  = getIntent().getStringExtra("user");
          //Log.d("user_prob",username);
        setContentView(R.layout.activity_users_area);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        zoom = (ZoomControls) findViewById(R.id.zcZoom);
        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        zoom.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

          /*-----------------contact fetch (phone)---------------------------*/
    int index=0;
    try {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[]{id}, null);
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

               String a =   PhoneNumberUtils.stripSeparators(phoneNumber);
               String phone="";
                if(a.length()>10)
                    phone = a.substring(a.length() - 10);
                else
                    phone = a;
                  if(!conct.contains(phone) && !phone.startsWith("140")) {
                      conct.add(index,phone);
                      index++;
                     }
               }
        }
        cont2 = conct.toArray(new String[0]);
        Arrays.sort(cont2);
    }
    catch (Exception ex){
        Log.e("errorcontact", "I got an error", ex);
    }


        chngView = (Button) findViewById(R.id.btSatellite);
        chngView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    chngView.setText(" Normal ");
                } else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    chngView.setText(" Satellite ");

                }
            }
        });

        clos = (Button) findViewById(R.id.btClose);
        clos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
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
                        user_logout(log);
                        if (Build.VERSION.SDK_INT >= 21) {
                            finishAndRemoveTask();
                        } else {
                            finish();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Button soss = (Button) findViewById(R.id.btSOS);

            soss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    promptSpeechInput();
                }
            });

    }

        /*--------------------------Updating Current location in DB-------------------------------------*/
    private void update(double Latitude, double Longtitide, String user) {
         String type = "newLogin";
        LatLngWork latLangWork = new LatLngWork(this);
        latLangWork.execute(type,String.valueOf(Latitude),String.valueOf(Longtitide),user);
    }
    /*---------------------------------delete record on logout-------------------------------------------*/
    private void user_logout(String user) {
        String type = "logout";
        LatLngWork latLangWork = new LatLngWork(this);
        latLangWork.execute(type,user);
    }

    /*---------------------------------Back key pressed------------------------------------------------------*/
    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
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

                user_logout(log);

                if (Build.VERSION.SDK_INT >= 21)
                    finishAndRemoveTask();
                else
                    finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /*--------------------distance calculator---------------*/

    public double distance(Double lat1,Double lon1,Double lat2,Double lon2) {
        Double radlat1 = Math.PI * lat1/180;
        Double radlat2 = Math.PI * lat2/180;
        Double theta = lon1-lon2;
        Double radtheta = Math.PI * theta/180;
        Double dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
        dist = Math.acos(dist);
		
        dist = dist * 180/Math.PI;
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; //km convert

          return dist;
    }

    /*-------------------------------------------------------*/


    @Override
    public void onMapReady(GoogleMap googleMap) {

        final String username  = getIntent().getStringExtra("user");
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION},                       MY_PERMISSION_FINE_LOCATION);
            }
        }

        /*-----------------------default location-------------------*/
        LatLng lat = new LatLng(20.5937, 78.9629);    // INDIA
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat,3.0f));

        /*---------------------------------------------------------*/

        /*------------------self location---------------*/
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,2000,50, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double My_Latitude = location.getLatitude();
                    double My_Longitude = location.getLongitude();

                    LatLng latLng = new LatLng(My_Latitude, My_Longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                            List<Address> addressList = geocoder.getFromLocation(My_Latitude, My_Longitude, 1);
                            String str = addressList.get(0).getSubLocality();
                            str += " ," + addressList.get(0).getLocality();

                            mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location:")).setSnippet(str);
                            frnd(My_Latitude,My_Longitude);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.5f));


                            update(My_Latitude,My_Longitude,username);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (Exception ex){
                        ex.printStackTrace();
                        /*final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
                        builder.setMessage(ex.toString());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();*/
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        //mMap.addMarker(new MarkerOptions().position(new LatLng(24.5572701,73.7184645)).title("manual").snippet("location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,50, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double My_Latitude = location.getLatitude();
                    double My_Longitude = location.getLongitude();

                    LatLng latLng = new LatLng(My_Latitude, My_Longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(My_Latitude, My_Longitude, 1);
                        String str = addressList.get(0).getSubLocality();
                        str += " ," + addressList.get(0).getLocality();
                        mMap.clear();
                        if(str.contains("null")) str="undefined";
                        mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location(GPS):").snippet(str));
                        frnd(My_Latitude,My_Longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.5f));

                        update(My_Latitude,My_Longitude,username);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            });
        }





        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                Double la = mMap.getMyLocation().getLatitude();
                Double lo = mMap.getMyLocation().getLongitude();


                Geocoder geocoder = new Geocoder(getApplicationContext());

                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocation(la,lo,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String str = addressList.get(0).getSubLocality();
                str += " ," + addressList.get(0).getLocality();


                LatLng LaLo = new LatLng(la,lo);
                  LaLo = new LatLng(la,lo);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(LaLo).title("Your Location")).setSnippet(str);
                frnd(la,lo);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LaLo,14.5f));
                update(la,lo,username);
                return false;
            }
        });

    }

    /*------------contact sync (server)---------------------*/
    private void frnd(Double Lat, Double Lon) {
        final String username  = getIntent().getStringExtra("user");
        String type = "contact";
        int found, count = 0;
        Log.d("number", type);
        LatLngWork latLangWork = new LatLngWork(this);
        try {
            server_contact = latLangWork.execute(type, username).get();
            if (server_contact.contains("numbers")) {
                server_contact = server_contact.replace("numbers", "");
                cont1 = server_contact.split(" ");

                for (int k = 0; k < cont1.length; k++) {
                    //  Log.d("FinalContactServer",cont1[k]);
                    found = Arrays.binarySearch(cont2, cont1[k]);
                    if (found >= 0) {
                        count++;

                        markfriend(cont1[k],Lat,Lon);
                    }
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
           /* final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
            builder.setMessage(ex.toString());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();*/
        }

    }

    /*------------------------------Marking Friends Location---------------------------------------*/
    private void markfriend(String mob,Double Lat, Double Lon) {
        String type = "markBlue";
        LatLngWork latLangWork = new LatLngWork(this);
        Geocoder geocoder = new Geocoder(getApplicationContext());

        try {
            locate=latLangWork.execute(type,mob).get();
            nmlaln=null;
            nmlaln = locate.split(" ");
            answer=null;
            answer=distance(Double.parseDouble(nmlaln[1]),Double.parseDouble(nmlaln[2]),Lat,Lon);

       /*     final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
            builder.setMessage(answer.toString());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();*/

          if(answer<=10.00d) {

             final String dst = String.format("%.2f", answer);
              final Double dist = Double.parseDouble(dst);
              List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(nmlaln[1]), Double.parseDouble(nmlaln[2]), 1);
              String str = addressList.get(0).getSubLocality();
              str += " ," + addressList.get(0).getLocality();
              LatLng latLng = new LatLng(Double.parseDouble(nmlaln[1]), Double.parseDouble(nmlaln[2]));
             // mMap.addMarker(new MarkerOptions().position(latLng).title(nmlaln[0] + "'s location").snippet(str).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
              mMap.addMarker(new MarkerOptions().position(latLng).title(nmlaln[0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).showInfoWindow();

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    // Getting view from the layout file info_window_layout
                    View v = getLayoutInflater().inflate(R.layout.information, null);

                    // Getting the position from the marker
                    LatLng latLng = marker.getPosition();


                    TextView nm = (TextView) v.findViewById(R.id.name);
                    TextView ds = (TextView) v.findViewById(R.id.dist);


                    final Button send = (Button) v.findViewById(R.id.butConfirm);

                    // Setting the info
                    nm.setText(nmlaln[0] + "'s location");
                    ds.setText("Distance: "+dist+" km");


                     mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
                        @Override
                        public void onInfoWindowLongClick(Marker marker) {
                            U_s=nmlaln[3];
                            final String cont=nmlaln[4];


                            //TODO: 3 buttons start

                            AlertDialog.Builder build = new AlertDialog.Builder(UsersArea.this);
                            build.setTitle("Troubled Detect!!");
                            build.setMessage("Select Message Type");
                            build.setIcon(R.drawable.trouble);

                            build.setNegativeButton("Type 1", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);

                                   LatLngWork latLangWork = new LatLngWork(UsersArea.this);
                                   latLangWork.execute("type1",U_a,U_s,cont);

                                    builder.setMessage("Type 1 Help Send Successfully !!!");
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });
                            build.setNeutralButton("Type 2", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);

                                    LatLngWork latLangWork = new LatLngWork(UsersArea.this);
                                    latLangWork.execute("type2",U_a,U_s,cont);

                                    builder.setMessage("Type 2 Help Send Successfully !!!");
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });
                            build.setPositiveButton("Type 3", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);

                                    LatLngWork latLangWork = new LatLngWork(UsersArea.this);
                                    latLangWork.execute("type3",U_a,U_s,cont);

                                    builder.setMessage("Type 3 Help Send Successfully !!!");
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });

                                build.show();


                            //TODO: 3button task end
                        }
                    });


                    // Returning the view containing InfoWindow contents
                    return v;
                }
            });
          }

            //  Log.d("locateBlue",locate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
          /*  final AlertDialog.Builder builder = new AlertDialog.Builder(UsersArea.this);
            builder.setMessage(ex.toString());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();*/
        }

    }
    /*----------------------------------Android Version >= Android M Runtime Permission results--------------------------------------------*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                          mMap.setMyLocationEnabled(true);
                    }
                    }
                    else {
                    Toast.makeText(getApplicationContext(),"This app Require location permission to be granted",Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAndRemoveTask();
                    }else {
                        finish();
                    }
                }
                  break;
            case REQUEST_PHONE_CALL:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(intent_call);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"This app Require Call permission to be granted",Toast.LENGTH_LONG).show();
                }
                break;
          }
    }
}
