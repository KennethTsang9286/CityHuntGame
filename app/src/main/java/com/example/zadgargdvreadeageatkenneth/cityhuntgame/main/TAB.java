package com.example.zadgargdvreadeageatkenneth.cityhuntgame.main;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.R;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.cookie;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.tab1.TabFragment1;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.tab3.TabFragment3;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.tab3.cardInfo;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.TabFragment2;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by TKenneth on 1/3/16.
 */
public class TAB extends Cover implements NavigationView.OnNavigationItemSelectedListener{
    //static public List result = new ArrayList();
    //private RequestQueue marker_requestQueue;
    private RequestQueue done_marker_requestQueue;
    ProgressDialog PD;
    public static final String position = "position";
    private MenuItem menuItem;
    //private static final String position1 = "position1";



    protected void initializeContent() {
        final ViewGroup viewGroup = (ViewGroup) findViewById(R.id.forContent);
        if(viewGroup != null) {
            viewGroup.removeAllViews();
        }
        viewGroup.addView(View.inflate(this, R.layout.content_main, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //askTime();

        initializeContent();


        marker_requestQueue = Volley.newRequestQueue(this);
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

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        //TextView user = (TextView) findViewById(R.id.user);
        //user.setText(cookie.teamName);

        getdata getmydata = new getdata();
        getmydata.togetdata();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Timer"));
        tabLayout.addTab(tabLayout.newTab().setText("News Feed"));
        //tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        int position = getIntent().getIntExtra("position",0);
        if(position == 1){
            tabLayout.getTabAt(1).select();
        }else if(position ==0){tabLayout.getTabAt(0).select();}


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult!=null){
            PD.show();
            String scanContent=scanningResult.getContents();
            //String scanFormat=scanningResult.getFormatName();
            final String markerName = scanContent;
            //Log.v("markerName",markerName);
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
                    //Toast.makeText(getApplicationContext(),
                    //        "failed to connect", Toast.LENGTH_SHORT).show();
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawer_timer) {
            Intent intent = new Intent(this, TAB.class);
            intent.putExtra(TAB.position,0);
            this.finish();
            startActivity(intent);
        }
        else if(id==R.id.drawer_news) {
            Intent intent = new Intent(this, TAB.class);
            intent.putExtra(TAB.position, 1);
            this.finish();
            startActivity(intent);
        } else if (id == R.id.drawer_map){
                Intent intent = new Intent(this, TabFragment2.class);
                startActivityForResult(intent, 0);
        }
        //else if (id == R.id.nav_camera) {
            // Handle the camera action
       // } else if (id == R.id.nav_gallery) {

        //}
        else if  (id == R.id.nav_manage) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                getdata getmydata = new getdata();
                getmydata.togetdata();
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



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            cookie.teamName = "";
            cookie.teamID = 1;
            cookie.eventID = 1;
            cookie.OrganizerID = 1;
            cookie.timeLength = 1;
            cookie.marker_array_length = 0 ;
        }
    }



/**
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_introduction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings)
        {
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
**/

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0:
                    TabFragment1 tab1 = new TabFragment1();
                    return tab1;
                case 1:
                    TabFragment3 tab2 = new TabFragment3();
                    return tab2;
               // case 2:
               //     TabFragment2 tab3 = new TabFragment2();
               //     return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }



}
    //public static int marker_array_length = getdata().array_lenght[0];

