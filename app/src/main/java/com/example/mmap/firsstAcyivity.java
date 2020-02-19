package com.example.mmap;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class firsstAcyivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    Button playbtn;

    Button stopbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firsst_acyivity);
        playbtn=findViewById(R.id.button1);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.nick);
                mediaPlayer.start();
            }
        });
        stopbtn=findViewById(R.id.button2);
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer !=null && mediaPlayer.isPlaying());
                mediaPlayer.stop();
            }
        });
    }
}

