package com.tomas.rastreame.models.manager_database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import com.tomas.rastreame.models.objects.Config;
import com.tomas.rastreame.models.objects.MessageOBJ;
import com.tomas.rastreame.models.database.SQLite;
import com.tomas.rastreame.models.database.Schema;

import java.util.ArrayList;

/**
 * Created by Tomas on 22/10/2015.
 */
public class SQLite_Manager
{
    private SQLiteDatabase sqlite_manager;
    private SQLite sqlIte;
    private Table_Config table_config;
    private Table_Message table_message;

    public SQLite_Manager(Context context)
    {
        this.sqlIte = new SQLite(context);
        this.sqlite_manager = this.sqlIte.getWritableDatabase();
        this.table_config = new Table_Config(this.sqlite_manager);
        this.table_message = new Table_Message(this.sqlite_manager);
    }

    public void insertDataConfig(Config config)throws SQLiteConstraintException
    {
        final ContentValues CONTENVALUES = new ContentValues();
        final int id = 1;
        CONTENVALUES.put(Schema._ID, id);
        CONTENVALUES.put(Schema.CONFIG_COLUMN_HOST_RECEPTION, config.getHostReception());
        CONTENVALUES.put(Schema.CONFIG_COLUMN_HOST_SHIPPING_MESSAGE, config.getHostSendMessage());
        CONTENVALUES.put(Schema.CONFIG_COLUMN_NAME_DEVICE, config.getNameDevice());
        CONTENVALUES.put(Schema.CONFIG_COLUMN_STATUS_OPERATION, config.getStatusOperation());
        this.table_config.insertIntoToTableConfig(CONTENVALUES);
    }
    public void insertDataMessage(MessageOBJ message)
    {
        final ContentValues CONTENVALUES = new ContentValues();
        CONTENVALUES.put(Schema._ID, message.getId());
        CONTENVALUES.put(Schema.MESSAGE_COLUMN_CONTAINER_MESSAGE, message.getBody());
        CONTENVALUES.put(Schema.MESSAGE_COLUMN_NUMBER_MESSAGE, message.getNumberMessage());
        CONTENVALUES.put(Schema.MESSAGE_COLUMN_DATE,message.getDate());
        CONTENVALUES.put(Schema.MESSAGE_COLUMN_HOUR_MINUTE, message.getHour());
        CONTENVALUES.put(Schema.MESSAGE_COLUMN_SECONDS, message.getSeconds());
        this.table_message.insertIntoToTableMessage(CONTENVALUES);

    }

    public Config readConfign()
    {
        return this.table_config.readCursor(this.table_config.readingDataFromTheTableConfig());
    }

    public ArrayList<MessageOBJ> readMessage()
    {
        return this.table_message.readCursor(this.table_message.readingDataFromTheTableMessage());
    }

    public void removeItemFromTableMessage(int id)
    {
        final String ARGS[] = {String.valueOf(id)};
        this.table_message.removeItem(ARGS);
    }
    public int getMaxIdFromTableMesssage()
    {
        return this.table_message.getMaxId();
    }

    public boolean setStatusConfig(String status)
    {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(Schema.CONFIG_COLUMN_STATUS_OPERATION, status);
        return this.table_config.setStatus(contentValues);
    }
}
