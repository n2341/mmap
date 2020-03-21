package com.example.mmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class secondActivity extends AppCompatActivity {
    TextView cprogram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        cprogram=findViewById(R.id.program);
        String  txt ="";

        try {
            InputStream inputStream = getAssets().open("cfile.cpp");
            int size = inputStream.available();
            byte[]buffer=new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            txt=new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cprogram.setText((CharSequence)txt);

    }
}
