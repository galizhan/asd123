package kz.edu.sdu.galix.lesson4;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
            Intent intent = new Intent(MyService.this, Main2Activity.class);
            PendingIntent pIntent = PendingIntent.getActivity(MyService.this, 0, intent, 0);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MyService.this)
                                    .setSmallIcon(android.R.drawable.presence_audio_away)
                                    .setContentTitle("My notification")
                                    .setContentIntent(pIntent)
                                    .setContentText("Hello World!");
                    NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(0,mBuilder.build());


            // 2-я часть

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();
        }

        void stop() {
            Log.d(LOG_TAG, "MyRun#" + startId + " end, stopSelfResult("
                    + startId + ") = " + stopSelfResult(startId));
        }
    }

}