package kz.edu.sdu.galix.lesson4;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public final static String BROADCAST_ACTION = "sdu.android.lesson";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
        Intent i;
        i = new Intent(this,MyService.class);
        startService(i);
    }

}
