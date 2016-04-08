package com.example.zadgargdvreadeageatkenneth.cityhuntgame.main;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.cookie;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.tab3.cardInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TKenneth on 2/4/16.
 */
public class Cover extends AppCompatActivity {

    private  int tabnumber = 0;
    static public List result = new ArrayList();
    public RequestQueue marker_requestQueue;
    public void prep(){
        marker_requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    final public class getdata {

        public void togetdata() {

            Map<String, String> params = new HashMap<String, String>();
            params.put("teamID", String.valueOf(cookie.teamID));
            params.put("eventID", String.valueOf(cookie.eventID));
            params.put("OrganizerID",String.valueOf(cookie.OrganizerID));


            CustomRequest jsonObjectRequest = new CustomRequest(Request.Method.POST, "http://cityunt.16mb.com/android_getMarker.php",params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.v("success", response.getString("success"));
                                if(Integer.parseInt(response.getString("success"))==0){
                                    Toast toast = Toast.makeText(getApplicationContext(),"Oops, nothing to show",Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER,0,0);
                                    toast.show();
                                }else{
                                    JSONArray jsonArray = response.getJSONArray("u112713487_apps");
                                    int length = jsonArray.length();
                                    result.clear();

                                    for (int i = 0; i < length; i++) {

                                        JSONObject marker = jsonArray.getJSONObject(i);
                                        cardInfo ci = new cardInfo();
                                        ci.location = marker.getString("markerName");
                                        ci.description = marker.getString("marker_description");
                                        ci.marker_lat = marker.getDouble("marker_lat");
                                        ci.marker_lng = marker.getDouble("marker_lng");
                                        Log.v("data_" + String.valueOf(i), ci.location);
                                        System.out.println();
                                        this.getClass();
                                        result.add(ci);

                                    }

                                    cookie.setValue(length);
                                    Log.v("useless.lenght22222", String.valueOf(cookie.marker_array_length));}

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", "ERROR");
                            Toast.makeText(getApplicationContext(), "CONNECTION PROBLEM", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            marker_requestQueue.add(jsonObjectRequest);

            //Log.v("result.size", String.valueOf(result.size()));

            if (result == null) {
                Toast.makeText(getApplicationContext(), "null result", Toast.LENGTH_SHORT).show();
            }


        }

    }
}
