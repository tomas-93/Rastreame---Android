package com.tomas.rastreame.utils.task;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;

import com.tomas.rastreame.controller.controller_network.HTTPHandle;
import com.tomas.rastreame.models.manager_database.SQLite_Manager;
import com.tomas.rastreame.models.objects.Config;

import java.util.TimerTask;

/**
 * Created by Tomas on 01/11/2015.
 */
public class TaskTimerControlServer extends TimerTask
{
    private SQLite_Manager sqLite_manager;
    private HTTPHandle httpHandle;
    private Config config;
    private boolean flag;
    private final String TAG ="TaskTimerControlServer";

    public TaskTimerControlServer(Context context, boolean flag)
    {
        this.sqLite_manager =  new SQLite_Manager(context);
        this.httpHandle = new HTTPHandle();
        this.flag = flag;
    }
    @Override
    public void run()
    {
        try
        {
            if(flag)
            {
                this.config = this.sqLite_manager.readConfign();
                if(this.config.getHostReception() != null)
                {
                    this.httpHandle.setData(this.config.getHostReception());
                    this.httpHandle.receiveMessage();
                }
            }
        }catch (CursorIndexOutOfBoundsException e)
        {
            //Log.e(this.TAG, e.getMessage());
        }
    }
}
