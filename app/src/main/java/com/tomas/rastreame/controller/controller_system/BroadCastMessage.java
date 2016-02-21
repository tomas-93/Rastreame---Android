package com.tomas.rastreame.controller.controller_system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.tomas.rastreame.controller.controller_services.ServicesSendMessage;
import com.tomas.rastreame.models.manager_database.SQLite_Manager;
import com.tomas.rastreame.models.objects.Config;
import com.tomas.rastreame.models.objects.MessageOBJ;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Tomas on 26/10/2015.
 */
public class BroadCastMessage extends BroadcastReceiver
{
    private SQLite_Manager sqlite_manager;
    private MessageOBJ message;
    private Config config;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.sqlite_manager = new SQLite_Manager(context);
        this.getDataConfig();

        final SmsMessage SMS = this.getMessageReceiver(intent);
        if(SMS != null && this.config.getStatusOperation().equals("Activado"))
        {
            this.sqlite_manager.insertDataMessage(this.message);
        }
        ServicesSendMessage servicesSendMessage = new ServicesSendMessage();
        servicesSendMessage.onService(context);

    }
    private void getDataConfig()
    {
        this.config = this.sqlite_manager.readConfign();
    }

    private SmsMessage getMessageReceiver(Intent intent)
    {
        this.message = new MessageOBJ();
        Bundle bundle = intent.getExtras();
        SmsMessage messagePhone = null;

        try
        {
            if(bundle != null)
            {
                Object objects [] = (Object[]) bundle.get("pdus");
                StringBuilder stringMessage = new StringBuilder();
                for(int cont = 0; cont < objects.length; cont++)
                {
                    messagePhone = SmsMessage.createFromPdu((byte[]) objects[cont]);
                    stringMessage.append(messagePhone.getMessageBody());
                }
                this.message.setId(this.sqlite_manager.getMaxIdFromTableMesssage());
                this.message.setBody(stringMessage.toString());
                this.message.setNumberMessage(messagePhone.getOriginatingAddress());
                this.generatingMessageDataTable();
            }
        }catch (NullPointerException ex)
        {
            ex.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return messagePhone;
        }
    }
    private void generatingMessageDataTable()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hour = new SimpleDateFormat("hh:mm");
        SimpleDateFormat seconds = new SimpleDateFormat("ss");
        message.setDate(String.valueOf(date.format(calendar.getTime())));
        message.setHour(String.valueOf(hour.format(calendar.getTime())));
        message.setSeconds(String.valueOf(seconds.format(calendar.getTime())));

    }
}
