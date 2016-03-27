package com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.tab1;

/**
 * Created by TKenneth on 1/3/16.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;


import com.example.zadgargdvreadeageatkenneth.cityhuntgame.R;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.cookie;

public class TabFragment1 extends Fragment {

    TextView timerTextView;
    long startTime = 0;
    private int length =cookie.timeLength;




    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {

            long millis = System.currentTimeMillis() - startTime;
            //int minute

            int time_lenght = length*60*1000;
            int Rmillis = time_lenght - (int)millis;
            Rmillis = Rmillis %1000;
            //int minute
            int seconds = (int) ((time_lenght - millis) / 1000);
            int minutes = seconds / 60;

            seconds = seconds % 60;
            if((int)(time_lenght - millis) >=0){
                timerTextView.setText(String.format("%d:%02d:%03d", minutes, seconds, Rmillis));

                timerHandler.postDelayed(this, 1);}else{timerTextView.setText(String.format("%d:%02d:%03d", 00, 00, 0000));}
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tab_fragment_1, container, false);

        String text = String.valueOf(length);


        timerTextView = (TextView) view.findViewById(R.id.timerTextView);

        Button b = (Button) view.findViewById(R.id.login_button);
        b.setText("start");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("cont");
                } else if(b.getText().equals("start")){
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("stop");
                }else{timerHandler.post(timerRunnable);
                    b.setText("stop");}
            }
        });

        Button bb = (Button) view.findViewById(R.id.restart);
        bb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View q){
                View view = getView();
                timerHandler.removeCallbacks(timerRunnable);
                Button b = (Button) view.findViewById(R.id.login_button);
                b.setText("start");
                timerTextView.setText(String.format("%d:%02d:%03d", length, 00,0000 ));
            }

        });





        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button) getView().findViewById(R.id.login_button);
        b.setText("start");
    }
}