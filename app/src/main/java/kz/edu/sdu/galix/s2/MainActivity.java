package kz.edu.sdu.galix.s2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText ed;
    ListView lv;
    ArrayList<Map<String, String>> data;
    SimpleAdapter sa;
    BroadcastReceiver br;
    public final static String BROADCAST_ACTION = "sdu.android.lesson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = (EditText) findViewById(R.id.ed);
        lv = (ListView) findViewById(R.id.lv);
        data = new ArrayList<>();
        sa = new SimpleAdapter(this, data, R.layout.item,
                new String[]{"me", "bot"}, new int[]{R.id.me, R.id.nameTV});
        lv.setAdapter(sa);
        br = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                String t = intent.getStringExtra("request");
                if (t!=null && t.equals("stop")) {
                    unregisterReceiver(br);
                    t = "bye";
                }
                if (t != null) {
                    Map<String, String> map = new HashMap();
                    map.put("me", ed.getText().toString());
                    map.put("bot", t);
                    data.add(map);
                    sa.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }
    public void send(View v){
        Log.d("mylogs","send");
        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, filter);
        Intent i;
        i = new Intent(this,MyService.class).putExtra("text",ed.getText().toString());
        startService(i);
        ed.setText("");

    }
}