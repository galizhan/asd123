package kz.edu.sdu.galix.mid1;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyService extends Service {
    public MyService() {
    }
    int interval=1000;
    ExecutorService es;
    String pause="";
    MyBinder binder = new MyBinder();
    @Override
    public void onCreate() {
        super.onCreate();
        es = Executors.newFixedThreadPool(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Create create = new Create();
        es.execute(create);
        return super.onStartCommand(intent, flags, startId);
    }


    class Create implements Runnable {
        Boolean start = false;
        String text;

        public Create() {

        }

        public void run() {
            Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
            NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MyService.this)
                                    .setSmallIcon(android.R.drawable.presence_audio_away)
                                    .setContentTitle("My notification")
                                    .setContentText("Hello World!")
                                    .setSmallIcon(android.R.drawable.alert_light_frame);
                    NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            for(int i=0;i<1000;i++){
                Log.d("service",""+i);
                Log.d("service",""+interval);
                intent.putExtra("t",i);
                sendBroadcast(intent);
                if (pause !=null && pause.equals("pause")){
                    mBuilder.setProgress(1000, i, false);
                    manager.notify(0,mBuilder.build());
                }
                if (pause.equals("resume")){
                    manager.cancel(0);
                }

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    int upInterval(int gap) {
        if(interval+gap<=1500)
        interval = interval + gap;
        return interval;
    }

    int downInterval(int gap) {
        if(interval-gap>=500)
        interval = interval - gap;
        if (interval < 0) interval = 0;
        return interval;
    }
    public void change_pause(String s){
        pause = s;
    }


    public IBinder onBind(Intent arg0) {
        return binder;
    }

    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}

