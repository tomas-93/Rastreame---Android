package com.tomas.rastreame.controller.controller_services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;


import com.tomas.rastreame.R;
import com.tomas.rastreame.controller.controller_view.Main;
import com.tomas.rastreame.utils.task.TaskTimerControlServer;

import java.util.Timer;

/**
 * Created by Tomas on 26/10/2015.
 */
public class ServiceReceptionMessage extends Service
{
    private Notification.Builder builder;
    private NotificationManager notificationManager;
    private Timer timer;
    private TaskTimerControlServer taskTimerControlServer;
    private final int TIMER_TASK = 1000;
    private final String NAME_TASK = "ServiceReceptionMessage";
    @Override
    public void onCreate()
    {
        this.notification();
        this.instaceTask();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int id)
    {
        if(this.timer == null && this.taskTimerControlServer == null)
            this.instaceTask();

        return START_REDELIVER_INTENT;
    }
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    @Override
    public void onDestroy()
    {
        this.stopForeground(true);
        this.timer.cancel();
        this.taskTimerControlServer.cancel();
    }
    /*
        Services
     */
    public void onService(Context context)
    {
        if(!this.onStartService(ServicesSendMessage.class, context))
        {
            //levantar servicion
            this.startServiceSendMessage(context);
        }
    }
    private void startServiceSendMessage(Context context)
    {
        context.startService(new Intent(context, ServiceReceptionMessage.class));
    }
    private boolean onStartService(Class<?> classService, Context context)
    {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo serviceInfo: manager.getRunningServices(Integer.MAX_VALUE))
        {
            if(classService.getName().equals(serviceInfo.service.getClassName()))
            {
                return  true;
            }
        }
        return false;
    }
    private  boolean isConnected()
    {
        boolean connect = true;
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info == null || !info.isAvailable() || !info.isConnected())
            connect = false;
        return connect;
    }
    /*
            Process
     */

    private void notification()
    {
        this.builder = new Notification.Builder(this.getApplicationContext());
        this.builder.setSmallIcon(R.drawable.ic_bug_report_black_48dp);
        this.builder.setWhen(System.currentTimeMillis());
        this.builder.setContentTitle("Proceso, en espera del mensaje.");
        this.builder.setContentText("Click para entrar a la actividad");

        Intent intent = new Intent(this.getApplicationContext(), Main.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this.getApplicationContext());
        taskStackBuilder.addParentStack(Main.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);

        this.notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        this.startForeground(1, builder.build());
    }
    private void instaceTask()
    {
        this.timer = new Timer(NAME_TASK);
        this.taskTimerControlServer = new TaskTimerControlServer(this.getApplicationContext(), this.isConnected());
        this.timer.schedule(this.taskTimerControlServer,0 ,TIMER_TASK);
    }
}
