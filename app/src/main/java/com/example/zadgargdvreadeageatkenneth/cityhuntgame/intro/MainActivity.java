package com.example.zadgargdvreadeageatkenneth.cityhuntgame.intro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zadgargdvreadeageatkenneth.cityhuntgame.R;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.login.Login;
import com.example.zadgargdvreadeageatkenneth.cityhuntgame.main.TAB;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.cover_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Intro.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
