package com.example.zadgargdvreadeageatkenneth.cityhuntgame;

import android.widget.NumberPicker;

/**
 * Created by TKenneth on 13/3/16.
 */
public class cookie {
    public static String teamName;
    public static int teamID = 1;
    public static int eventID = 1;
    public static int OrganizerID = 1;
    public static int timeLength = 1;
    static public  int marker_array_length ;

    //private static final cookie cookieHolder = new cookie();
    //public static cookie getInstance() {return cookieHolder;}
    //public int getMarkerArrayLength(){return marker_array_length;};

    private static mListener mListener;

    //public int  cookie(int initialValue){marker_array_length = initialValue;}

    public  void setmListener(mListener listener){
        mListener = listener;
    }

    public static void setValue(int newValue){
        marker_array_length = newValue;
        if(mListener != null){mListener.onValueChanged(marker_array_length);}

    }



    public int getValue(){
        return marker_array_length;
    }

    public static interface  mListener{void onValueChanged(int newValue);};

    }




