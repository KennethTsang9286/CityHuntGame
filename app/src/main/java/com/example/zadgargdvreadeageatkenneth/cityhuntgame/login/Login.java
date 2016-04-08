package com.example.zadgargdvreadeageatkenneth.cityhuntgame.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.R;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.cookie;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.TAB;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TKenneth on 13/3/16.
 */
public class Login extends AppCompatActivity {

    String url = "http://cityunt.16mb.com/android_login.php";
    RequestQueue requestQueue_send;
    EditText teamName;
    EditText teamPassword;
    ProgressDialog PD;
    CheckBox mCbShowPwd;
    EditText mEtPwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button button = (Button) findViewById(R.id.start_button);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        teamName = (EditText) findViewById(R.id.teamName);
        teamPassword = (EditText) findViewById(R.id.password);
        requestQueue_send = Volley.newRequestQueue(getApplicationContext());

        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.v("teamName", teamName.getText().toString());
                    Log.v("teamPassword", teamPassword.getText().toString());
                    PD.show();

                   /** Map<String, String> params = new HashMap<String, String>();
                    params.put("teamName", teamName.getText().toString());
                    params.put("teamPassword", teamPassword.getText().toString());
                    Log.v("teamName", teamName.getText().toString());
                    Log.v("teamPassword", teamPassword.getText().toString());**/


                    /**CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject teaminfo) {

                            PD.dismiss();
                            try {
                                int jsonObject = teaminfo.getInt("success");
                                //String q = teaminfo.replaceAll("\\s", "");
                                ;
                                Log.v("success", String.valueOf(jsonObject));
                                //int t = Integer.parseInt(q);
                                //Log.v("int_success",Integer.toString(t));
                                if (jsonObject > 0) {
                                    Log.v("hhih","hih");

                                    //JSONObject array = teaminfo.getJSONObject("info");

                                    //JSONArray jsonArray = teaminfo.getJSONArray("info");
                                    //JSONObject array = jsonArray.getJSONObject(0);
                                    //cookie.teamID = array.getInt("teamID");
                                    /**cookie.OrganizerID = array.getInt("OrganizerID");
                                    cookie.eventID = array.getInt("eventID");**//**
                                    cookie.teamID = teaminfo.getInt("teamID");
                                    Log.v("teamID", String.valueOf(cookie.teamID));
                                    /**cookie.eventID = teaminfo.getInt("eventID");
                                    cookie.OrganizerID = teaminfo.getInt("OrganizerID");
                                    cookie.teamName = teamName.getText().toString();
                                    Log.v("teamID", String.valueOf(cookie.teamID));
                                    Log.v("OrganizerID", String.valueOf(cookie.OrganizerID));
                                    Log.v("eventID", String.valueOf(cookie.eventID));**//**
                                    //Log.v("teamID",String.valueOf(cookie.teamID));
                                    Toast toast = Toast.makeText(getApplicationContext(),
                                            "Welcome, " + teamName.getText().toString(),
                                            Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    Intent intent = new Intent(getBaseContext(), TAB.class);
                                    startActivityForResult(intent, 0);
                                } else {

                                    Toast.makeText(getApplicationContext(),
                                            "WRONG TEAM NAME AND PASSWORD COMBINATION", Toast.LENGTH_SHORT).show();
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
                                        "CONNECTION ERROR", Toast.LENGTH_SHORT).show();

                            }
                        });
                    requestQueue_send.add(jsObjRequest);**/



                             StringRequest  jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override public void onResponse(String teaminfo) {
                            PD.dismiss();
                            try {
                            JSONObject jsonObject = new JSONObject(teaminfo);
                                String success = jsonObject.getString("success");
                                String qq = success.replaceAll("\\s","");
                                int q = Integer.parseInt(qq);
                            //String q = teaminfo.replaceAll("\\s", "");
                            ;
                            Log.v("success", success);
                                Log.v("success_number", String.valueOf(q));
                            //int t = Integer.parseInt(q);
                            //Log.v("int_success",Integer.toString(t));

                            if (q > 0) {
                            //JSONArray jsonArray = teaminfo.getJSONArray("info");
                            //JSONObject array = jsonArray.getJSONObject(0);
                                Log.v("hihihih","hihihihih");
                                String ttt = jsonObject.getString("try");
                                Log.v("try",ttt);
                                //String aaa = jsonObject.getString("teamID").replaceAll("\\s", "");

                                //String aa = aaa.replaceAll("\\s", "");
                                cookie.teamID = Integer.parseInt(jsonObject.getString("teamID").replaceAll("\\s", ""));
                                cookie.OrganizerID = Integer.parseInt(jsonObject.getString("OrganizerID").replaceAll("\\s", ""));
                                cookie.eventID = Integer.parseInt(jsonObject.getString("eventID").replaceAll("\\s", ""));
                               // String bbb = jsonObject.getString("OrganizerID");



                            //cookie.OrganizerID = Integer.parseInt(jsonObject.getString("OrganizerID"));
                            //cookie.eventID = Integer.parseInt(jsonObject.getString("eventID"));
                            Log.v("teamID",String.valueOf(cookie.teamID));
                            Log.v("OrganizerID",String.valueOf(cookie.OrganizerID));
                            Log.v("eventID",String.valueOf(cookie.eventID));
                            //cookie.teamID = teaminfo.getInt("teamID");
                            //cookie.eventID = teaminfo.getInt("eventID");
                            //cookie.OrganizerID = teaminfo.getInt("OrganizerID");
                            cookie.teamName = teamName.getText().toString();
                            //Log.v("teamID",String.valueOf(cookie.teamID));
                            Toast toast = Toast.makeText(getApplicationContext(),
                            "Welcome, " + teamName.getText().toString(),
                            Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                            Intent intent = new Intent(getBaseContext(), TAB.class);
                            startActivityForResult(intent, 0);
                                teamName.setText("");
                                teamPassword.setText("");
                            } else {
                            Toast.makeText(getApplicationContext(),
                            "WRONG TEAM NAME AND PASSWORD COMBINATION", Toast.LENGTH_SHORT).show();
                            }
                            } catch (Exception e) {
                            e.printStackTrace();
                            }
                            }}, new Response.ErrorListener() {
                            @Override public void onErrorResponse(VolleyError error) {
                            PD.dismiss();
                            Toast.makeText(getApplicationContext(),
                            "CONNECTION ERROR", Toast.LENGTH_SHORT).show();

                            }
                            }) {
                            @Override protected Map<String, String> getParams() throws AuthFailureError{
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("teamName", teamName.getText().toString());
                            params.put("teamPassword", teamPassword.getText().toString());
                            Log.v("teamName", teamName.getText().toString());
                            Log.v("teamPassword", teamPassword.getText().toString());
                            return params;
                            }
                            };

                             requestQueue_send.add(jsonObjectRequest);

                }
            });

        }


        // get the password EditText

        // get the show/hide password Checkbox
         mCbShowPwd = (CheckBox) findViewById(R.id.showPW);
        teamPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        // add onCheckedListener on checkbox
        // when user clicks on this checkbox, this is the handler.
        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    teamPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    teamPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


    }





}