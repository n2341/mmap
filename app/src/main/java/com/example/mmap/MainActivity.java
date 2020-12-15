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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnsave;
    EditText etinput;
    String filename="";
    String filepath="";
    String filecontent="";

    private Button start,stop;
    private static final String FILE_NAME="JIDDA.txt";
    EditText internal;
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

        btnsave=findViewById(R.id.btnexternal);
        etinput=findViewById(R.id.etinput);
        filename="myFile.text";
        filepath="MyFileDir";
        if(isExternalStorageAvailableForRW()){
            btnsave.setEnabled(true);
        }
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filecontent=etinput.getText().toString().trim();
                if (!filecontent.equals("")){
                    File myExternalFile=new File(getExternalFilesDir(filepath),filename);
                    FileOutputStream fos=null;
                    try {
                        fos=new FileOutputStream(myExternalFile);
                        fos.write(filecontent.getBytes());
                    }catch (FileNotFoundException e){
                        e.printStackTrace();

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    etinput.setText("");
                    Toast.makeText(getApplicationContext(),"saved on the External Memory",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Text field can't be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });







        registerReceiver(mybroadcast,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
//        MediaPlayer player=MediaPlayer.create(this,Settings.System.DEFAULT_RINGTONE_URI);
//        player.setLooping(true);
//        player.start();
        internal= findViewById(R.id.txtenter);
        start=(Button)findViewById(R.id.startservice);
        stop=(Button)findViewById(R.id.stopservice);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);


}
    public boolean isExternalStorageAvailableForRW(){
        String extStorage=Environment.getExternalStorageState();
        if(extStorage.equals(Environment.MEDIA_MOUNTED)){
            return  true;
        }
        return false;
    }


public void save(View view){
String text=internal.getText().toString();
    FileOutputStream fos=null;
    try {
        fos=openFileOutput(FILE_NAME,MODE_PRIVATE);
        fos.write(text.getBytes());
        internal.getText().clear();
        Toast.makeText(this,"saved to " + getFilesDir()+ "/"+FILE_NAME,Toast.LENGTH_LONG).show();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }finally {
        if (fos != null){
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
public  void load(View view){
    FileInputStream fis=null;
    try {
        fis=openFileInput(FILE_NAME);
        InputStreamReader isr=new InputStreamReader(fis);
        BufferedReader br=new BufferedReader(isr);
        StringBuilder sb=new StringBuilder();
        String text;
        while ((text = br.readLine()) !=null){
            sb.append(text).append("/n");
        }
        internal.setText(sb.toString());
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    finally {
        if (fis !=null){
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
                startActivity(new Intent(this, listview.class));
                return true;
            case R.id.cp:
                startActivity(new Intent(this, secondActivity.class));
                return true;
            case R.id.mail:
                Intent h = new Intent(Intent.ACTION_SEND);
                h.setData(Uri.parse("mailto"));
                String to[] = {"mupatt@gmail.com", "nankundjiddah@gmail.com", "bryntu9@gmail.com"};
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        if (view==start){
startService(new Intent(this,Myservice.class));
        }else if (view==stop){
            stopService(new Intent(this,Myservice.class));
        }

    }
}


