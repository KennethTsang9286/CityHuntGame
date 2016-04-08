package com.example.zadgargdvreadeageatkenneth.cityhuntgame.main;

/**
 * Created by TKenneth on 1/3/16.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.cookie;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.TAB;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.tab3.InfoAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.Map;

import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.R;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.GoogleApiAvailability;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.tab3.cardInfo;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class TabFragment2 extends Cover implements NavigationView.OnNavigationItemSelectedListener
        ,/*LocationListener,*/ GoogleMap.OnMapLoadedCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener


{

    private RequestQueue done_marker_requestQueue;
    ProgressDialog PD;
    cookie cookie = new cookie();
    public GoogleMap mMap;
    public MapView mapView;
    RequestQueue requestQueue;
    public List<cardInfo> list;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mCurrentLocation;
    protected LocationRequest mLocationRequest;
    protected String mLastUpdateTime;
    String url = "http://cityunt.16mb.com/putGPSdata_ken.php";

    private MenuItem menuItem;
    //public int isGooglePlayServicesAvailable(context);

    protected void initializeContent() {
        final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.forContent);
        if(viewGroup != null) {
            viewGroup.removeAllViews();
        }
        viewGroup.addView(View.inflate(this, R.layout.tab_fragment_2, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initializeContent();
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        marker_requestQueue = Volley.newRequestQueue(this);
        getdata getmydata = new getdata();
        getmydata.togetdata();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView user = (TextView) headerView.findViewById(R.id.user);
        user.setText(cookie.teamName);
        navigationView.setNavigationItemSelectedListener(this);


        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            Log.e("TAG", "NONONO");
            this.finish();
        }
        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);
        requestQueue = Volley.newRequestQueue(this);
        this.list = TAB.result;





        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi( LocationServices.API )
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();






        refreshmap(mapView);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        MapsInitializer.initialize(this);

        cookie.setmListener(new cookie.mListener() {
            @Override
            public void onValueChanged(int newValue) {
                refreshmap(mapView);
            }

            });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_introduction, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:

                menuItem =item;
                menuItem.setActionView(R.layout.actionbar_indeterminate_progress);
                menuItem.expandActionView();
                refreshmap(mapView);
                TestTask task = new TestTask();
                task.execute("test");

                break;

        }
        return super.onOptionsItemSelected(item);
    }




    private class TestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // Simulate something long running
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            menuItem.collapseActionView();
            menuItem.setActionView(null);
        }
    };



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawer_timer) {
            Intent intent = new Intent(this, TAB.class);
            intent.putExtra(TAB.position,0);
            startActivityForResult(intent, 0);

        } else if (id == R.id.drawer_news) {
            Intent intent = new Intent(this, TAB.class);
            intent.putExtra(TAB.position,1);
            startActivityForResult(intent, 0);



        }
        else if (id == R.id.drawer_map){
            Intent intent = new Intent(this, TabFragment2.class);
            this.finish();
            startActivity(intent);}
       /** else if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }*/ else if  (id == R.id.nav_manage) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult!=null){
            PD.show();
            String scanContent=scanningResult.getContents();
            final String markerName = scanContent;
            String url = "http://cityunt.16mb.com/android_complete_task.php";
            done_marker_requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    PD.dismiss();
                    try {
                        Log.v("response", response);
                        String rr = response.replaceAll("\\s", "");
                        int r = Integer.parseInt(rr);
                        if (r == 0) {
                            Toast.makeText(getApplicationContext(),"failed to find", Toast.LENGTH_SHORT).show();
                        }else{Toast.makeText(getApplicationContext(),"YOU CAN GO TO NEXT POINT NOW", Toast.LENGTH_LONG).show();
                            getdata getmydata = new getdata();
                            getmydata.togetdata();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PD.dismiss();
                    Toast.makeText(getApplicationContext(),
                            "failed to connect", Toast.LENGTH_SHORT).show();
                }
            }){@Override
               protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String,String>();
                params.put("markerName", markerName.trim());
                return params;
            }};
            done_marker_requestQueue.add(request);

        }else{
            Toast.makeText(getApplicationContext(),"No QR code",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void refreshmap(MapView mapView) {

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if(mCurrentLocation!= null){
                    sendGPS(mCurrentLocation);
                }
                setUpMap(googleMap);

            }
        });

    }


    private void setUpMap(GoogleMap map) {

        mMap = map;
        int length = 0;


        double sum_marker_lat = 0;
        double sum_marker_lng = 0;
        //while(list.size() == 0){
            this.list = TAB.result;
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(22.336206, 114.173049), 12);
        mMap.animateCamera(cameraUpdate1);

        while(length !=cookie.marker_array_length || length == 0 && cookie.marker_array_length !=0) {
            length = cookie.marker_array_length;
            for (int i = 0; i < length; i++) {
                cardInfo ci = list.get(i);
                sum_marker_lat += ci.marker_lat;
                sum_marker_lng += ci.marker_lng;
                if(i!=0){
                    mMap.addMarker(new MarkerOptions().position(new LatLng(ci.marker_lat, ci.marker_lng)).title(ci.location).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_grey)));
                    //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(ci.marker_lat, ci.marker_lng), 12);
                    //mMap.animateCamera(cameraUpdate);

                }else{

                mMap.addMarker(new MarkerOptions().position(new LatLng(ci.marker_lat, ci.marker_lng)).title(ci.location).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(sum_marker_lat / length, sum_marker_lng / length), 12);
                //mMap.animateCamera(cameraUpdate);
                }
            }
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(sum_marker_lat / length, sum_marker_lng / length), 12);
            mMap.animateCamera(cameraUpdate);
        }

    }
    ////////////////GPS//////////////////////////////////


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("onConnected", "hi");
        refreshmap(mapView);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation( mGoogleApiClient );
        initCamera(mCurrentLocation);
        sendGPS(mCurrentLocation);
    }

    @Override
    public void onMapLoaded() {

    }

    /*
    @Override
    public void onLocationChanged(Location location) {

        Log.v("LOG","FIRE location");
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        sendGPS(location);
        Log.v("mycurrentlat", String.valueOf(latitude));
        Log.v("mycurrentlng",String.valueOf(longitude));
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }


    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
        Log.e("TAG", "onProviderDisabled");
       Toast.makeText(this,"I can't find UUUUUUUUUUU",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }**/

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }



    private void initCamera( Location location ) {

        //mMap.setTrafficEnabled(true);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (location != null) {
            sendGPS(location);
            Log.v("mycurrentlat", String.valueOf(location.getLatitude()));
            Log.v("mycurrentlng", String.valueOf(location.getLongitude()));
            CameraPosition position = CameraPosition.builder()
                    .target(new LatLng(location.getLatitude(),
                            location.getLongitude()))
                    .zoom(16f)
                    .bearing(0.0f)
                    .tilt(0.0f)
                    .build();


            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), null);

        }else{Toast toast =  Toast.makeText(this,"I can't find UUUUUUUUUU",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        ViewGroup group = (ViewGroup) toast.getView();
            TextView msg = (TextView) group.getChildAt(0);
            msg.setTextSize(50);
            toast.show();
        }


        //mMap.setTrafficEnabled(true);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("no internet", "no internet");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("TAG", "Connection failed: " + connectionResult.toString());
    }

    public void sendGPS(final Location location){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("GPSok?",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "failed update your location", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("team_lat", String.valueOf(location.getLatitude()));
                params.put("team_lng",String.valueOf(location.getLongitude()));
                params.put("teamID", String.valueOf(cookie.teamID));
                params.put("eventID", String.valueOf(cookie.eventID));
                params.put("OrganizerID",String.valueOf(cookie.OrganizerID));
                return params;
            }
        };

        requestQueue.add(request);
    }



}

