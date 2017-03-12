package kz.edu.sdu.galix.mid1;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String BROADCAST_ACTION = "sdu.android.mid1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fl);
        if (fragment == null) {
            fragment = new Fragment1();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fl, fragment);
            transaction.commit();
        }
    }

    public void loadFragment2() {
        Fragment2 fragment2 = new Fragment2();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, fragment2).addToBackStack(null).commit();

    }

}
