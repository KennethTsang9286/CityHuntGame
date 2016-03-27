package com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.tab3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zadgargdvreadeageatkenneth.cityhuntgame.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by TKenneth on 1/3/16.
 */
public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ContactViewHolder> {

    public List<cardInfo> contactList = new ArrayList<cardInfo>();
    private Context context;

    public InfoAdapter(Context context, List<cardInfo> contactList) {
        this.context=context;
        //while(cookie.getInstance().getMarkerArrayLength() == 0) {
        //    try{
            this.contactList = contactList;
        //        wait(500);
        //    }catch(Exception e){Log.v("infoAdapter","we have a loop");};

        // }
        this.context=context;

    }

    @Override
    public int getItemCount() {
        Log.v("Contat.size",String.valueOf(contactList.size()));
        //Log.v("trytrytry", String.valueOf(cookie.marker_array_length));
        return contactList.size();
    }

    private Drawable getDrawableResourceByName(String aString) {
        int resId = context.getResources().getIdentifier(aString, "drawable","com.example.zadgargdvreadeageatkenneth.cityhuntgame" );
        if (resId !=  0){
        return context.getDrawable(resId);}
        return null;
    }


    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        cardInfo ci = contactList.get(i);
        Log.v("cardinfo_ci",ci.location);
        contactViewHolder.vlocation.setText(ci.location);
        contactViewHolder.vdescription.setText(ci.description);
        if(i == 0) {
            contactViewHolder.vImagebutton.setImageDrawable(getDrawableResourceByName("marker"));
        }
        else{contactViewHolder.vImagebutton.setImageDrawable(getDrawableResourceByName("marker_grey"));}



    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected ImageButton vImagebutton;
        protected TextView    vlocation;
        protected TextView    vdescription;
        public ContactViewHolder(View v) {
            super(v);

            vImagebutton =  (ImageButton) v.findViewById(R.id.imageButton);
            vlocation = (TextView) v.findViewById(R.id.location);
            vdescription = (TextView) v.findViewById(R.id.description);
        }
    }

}