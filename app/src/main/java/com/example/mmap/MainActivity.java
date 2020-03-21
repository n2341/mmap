package com.example.mmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver mybroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level",0);
            ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
            progressBar.setProgress(level);

            TextView textView = (TextView)findViewById(R.id.batteryView);
            textView.setText("Battery level is:" + Integer.toString(level) + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(mybroadcast,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
}


    public void sendMessage(View view){
        EditText message= (EditText) findViewById(R.id.message);
        Toast.makeText(this,"sending message"+message.getText().toString(),Toast.LENGTH_SHORT);
        Intent intent=new Intent(this,DisplayMessageActivity.class);
        intent.putExtra("MESSAGE",message.getText().toString());
        startActivity(intent);
        message.setText("");
    }
    public void startAlert(View view){
        EditText text=(EditText)findViewById(R.id.arm);
        int obj = Integer.parseInt(text.getText().toString());

        //Create an intent and call your receiver
        Intent intent = new Intent(this,BroadcastReceiver.class);

        //Create a pending Intent to be fired
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),0,intent,0);

        //Recall the alarm manager class
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Real time clock to be used

        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + (obj * 1000),pendingIntent);
        Toast.makeText(this,"Alarm set in" + obj + "seconds",Toast.LENGTH_LONG).show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.jiddah:
                startActivity(new Intent(this, firsstAcyivity.class));
return true;

            case R.id.list:
                startActivity(new Intent(this,listview.class));
                return true;
            case R.id.cp:
                startActivity(new Intent(this,secondActivity.class));
                return true;
            case R.id.mail:
                Intent h = new Intent(Intent.ACTION_SEND);
                h.setData(Uri.parse("mailto"));
                String to [] = {"mupatt@gmail.com","nankundjiddah@gmail.com","bryntu9@gmail.com"};
                h.putExtra(Intent.EXTRA_EMAIL, to);
                h.putExtra(Intent.EXTRA_SUBJECT, "Testing");
                h.putExtra(Intent.EXTRA_TEXT, "Have i made it? Great day!!");
                h.setType("message/rfc822");
                startActivity(h);
                return true;

            case R.id.call:
                try {
                    // check for call permissions
                    int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

                            // Show an explanation to the user *asynchronously*
                            Toast.makeText(this, "This permission is required to call a phone number", Toast.LENGTH_LONG).show();

                        } else {

                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                            }
                    }
                    Intent obj = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0759630945"));
                    startActivity(obj);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }
}


