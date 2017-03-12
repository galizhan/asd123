package kz.edu.sdu.galix.mid1;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {


    public Fragment2() {
        // Required empty public constructor
    }
    Button btn1,btn2;
    View view;
    public final static String BROADCAST_ACTION = "sdu.android.lesson";
    final String LOG_TAG = "myLogs";
    boolean bound = false;
    ServiceConnection sConn;
    Intent intent;
    MyService myService;
    BroadcastReceiver br;
    int interval,t;
    ProgressBar pb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_2, container, false);
        IntentFilter filter = new IntentFilter(MainActivity.BROADCAST_ACTION);
        getActivity().registerReceiver(br, filter);
        btn1 = (Button)view.findViewById(R.id.up);
        btn2 = (Button)view.findViewById(R.id.down);
        intent = new Intent(getActivity(), MyService.class);
        pb = (ProgressBar)view.findViewById(R.id.progressBar);
        pb.setMax(1000);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bound) return;
                interval = myService.upInterval(250);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bound) return;
                interval = myService.downInterval(250);
            }
        });
        sConn = new ServiceConnection() {

            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected");
                myService = ((MyService.MyBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
                bound = false;
            }
        };
        getActivity().startService(intent);
        getActivity().bindService(intent, sConn, 0);


        br = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                t = intent.getIntExtra("t",0);
                pb.setProgress(t);
                Log.d("so",""+t);
            }

        };

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!bound) return;
        getActivity().unbindService(sConn);
        bound = false;
    }


}
