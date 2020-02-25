package com.example.mmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class listview extends AppCompatActivity {
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        listView=findViewById(R.id.list);
        final ArrayList<String>arrayList=new ArrayList<>();
        arrayList.add("mbarara");
        arrayList.add("kampala");
        arrayList.add("Mukono");
        arrayList.add("Arua");
        arrayList.add("mbarara");
        arrayList.add("kampala");
        arrayList.add("Mukono");
        arrayList.add("Arua");
        arrayList.add("mbarara");
        arrayList.add("kampala");
        arrayList.add("Mukono");
        arrayList.add("Arua");
        arrayList.add("mbarara");
        arrayList.add("kampala");
        arrayList.add("Mukono");
        arrayList.add("Arua");
        arrayList.add("mbarara");
        arrayList.add("kampala");
        arrayList.add("Mukono");
        arrayList.add("Arua");
        arrayList.add("mbarara");
        arrayList.add("kampala");
        arrayList.add("Mukono");
        arrayList.add("Arua");

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }
}



