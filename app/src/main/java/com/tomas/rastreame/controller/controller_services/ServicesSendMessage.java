package com.tomas.rastreame.controller.controller_services;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.tomas.rastreame.controller.controller_network.HTTPHandle;
import com.tomas.rastreame.models.manager_database.SQLite_Manager;
import com.tomas.rastreame.models.objects.Config;
import com.tomas.rastreame.models.objects.MessageOBJ;

import java.util.ArrayList;

/**
 * Created by Tomas on 26/10/2015.
 */
public class ServicesSendMessage extends IntentService
{
    private ArrayList<MessageOBJ> listMessage;
    private SQLite_Manager sqLite_manager;
    private HTTPHandle httpHandle;
    private Config config;
    private final String TAG = "ServicesSendMessage";
    public ServicesSendMessage()
    {
        super("ServicesSendMessage");
    }

    @Override
    public void onHandleIntent(Intent intent)
    {
        try
        {
            if(isConnected())
            {
                this.httpHandle = new HTTPHandle();
                this.sqLite_manager = new SQLite_Manager(this.getApplicationContext());
                this.listMessage = this.sqLite_manager.readMessage();
                this.config = this.sqLite_manager.readConfign();
                if(this.listMessage != null && this.config.getHostSendMessage() != null)
                {
                    for(MessageOBJ message: this.listMessage)
                    {
                        this.httpHandle.setData(config.getHostSendMessage(),
                                message.getNumberMessage(),
                                message.getBody(),
                                message.getDate(),
                                message.getHour(),
                                message.getSeconds(),
                                this.config.getNameDevice());
                        this.httpHandle.seenMessage();
                        this.sqLite_manager.removeItemFromTableMessage(message.getId());
                    }
                }else this.onHandleIntent(intent);
            }else this.onHandleIntent(intent);
        }catch (CursorIndexOutOfBoundsException e)
        {
            Log.e(this.TAG, e.getMessage());
        }
    }
    /*
            Service
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
        context.startService(new Intent(context, ServicesSendMessage.class));
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

}
