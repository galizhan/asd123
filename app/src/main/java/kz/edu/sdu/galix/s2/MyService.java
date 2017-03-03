package kz.edu.sdu.galix.s2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;



public class MyService extends Service {

    final String LOG_TAG = "myLogs";
    ExecutorService es;

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
        es = Executors.newFixedThreadPool(2);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");
        String text = intent.getStringExtra("text");
        Conversation mr = new Conversation(startId, text);
        es.execute(mr);

        return super.onStartCommand(intent, flags, startId);
    }
    class Conversation implements Runnable {
        int startId;
        Boolean start = false;
        String text;

        public Conversation(int startId, String text) {
            //this.time = time;
            this.text=text;
            this.startId = startId;

            Log.d(LOG_TAG, "MyRun#" + startId + " create");
        }

        public void run() {
            Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
            Log.d("mylogs",text);
            switch (text){
                case "hi":
                    start = true;
                    intent.putExtra("request","Hello");
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MyService.this)
                                    .setSmallIcon(android.R.drawable.presence_audio_away)
                                    .setContentTitle("My notification")
                                    .setContentText("Hello World!");
                    NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(0,mBuilder.build());
                            break;
                case "bye": intent.putExtra("request","stop");
                            start = false;
                            break;
                default: if(start) intent.putExtra("request",text);
            }



            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendBroadcast(intent);


            stop();
        }

        void stop() {
            Log.d(LOG_TAG, "MyRun#" + startId + " end, stopSelfResult("
                    + startId + ") = " + stopSelfResult(startId));
        }
    }

}