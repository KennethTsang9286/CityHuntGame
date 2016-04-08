package com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.tab3;

/**
 * Created by TKenneth on 1/3/16.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.example.zadgargdvreadeageatkenneth.cityhuntgame.cookie;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.R;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.TAB;

public class TabFragment3 extends Fragment {


    cookie cookie = new cookie();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_3, container, false);

    }
    //
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recList = (RecyclerView) getView().findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        cookie.setmListener(new cookie.mListener() {
            @Override
            public void onValueChanged(int newValue) {
                System.out.println();
                InfoAdapter ca = new InfoAdapter(getActivity(), TAB.result);
                recList.setAdapter(ca);}


        });

        //Log.v("tab3.sizeeeeeeeeeeee",String.valueOf(TAB.result.size()));
    }




/**

    private List createList(int size) {

        List result = new ArrayList();
        for (int i = 1; i <= size; i++) {
            cardInfo ci = new cardInfo();
            ci.location = cardInfo.NAME_PREFIX + i;
            ci.description = cardInfo.IMAGE_PREFIX + i;

            result.add(ci);

        }
        return result;
    }
 **/

    /**
    private List createList(final Context context) {
        final List result = new ArrayList();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,"http://cityunt.16mb.com/ShowMapLatLng.php",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("u112713487_apps");
                            int length = jsonArray.length();
                            for (int i = 1; i < length; i++) {
                                JSONObject marker = jsonArray.getJSONObject(i);
                                cardInfo ci = new cardInfo();
                                ci.location = marker.getString("markerName");
                                ci.description = marker.getString("marker_description");

                                Log.v("location_"+i,ci.location);
                                Log.v("descritpion", ci.description);
                                //ci.image = cardInfo.IMAGE_PREFIX + i;
                                System.out.println();
                                result.add(ci);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", "ERROR");
                        Toast.makeText(context,"CONNECTION PROBLEM",Toast.LENGTH_SHORT);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
        if (result == null){
            Toast.makeText(context,"null result",Toast.LENGTH_SHORT).show();}
        return result;

    }
    */



    //send request









}
