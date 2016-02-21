package com.tomas.rastreame.models.manager_database;

import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import com.tomas.rastreame.models.objects.Config;
import com.tomas.rastreame.models.database.Schema;

/**
 * Created by Tomas on 22/10/2015.
 */
class Table_Config
{
    private SQLiteDatabase sqlite_manager;

    public Table_Config(SQLiteDatabase sql)
    {
        this.sqlite_manager = sql;
    }

    protected void insertIntoToTableConfig(ContentValues contentValues)throws SQLiteConstraintException
    {
        if(this.thereAreElementsInTable())
            this.removeData();
        this.sqlite_manager.insertOrThrow(Schema.TABLE_CONFIG, Schema.COLUMN_NULL, contentValues);
    }

    protected Cursor readingDataFromTheTableConfig()
    {
        String COLUMN [] =
                            {
                                Schema.CONFIG_COLUMN_HOST_RECEPTION,
                                Schema.CONFIG_COLUMN_HOST_SHIPPING_MESSAGE,
                                Schema.CONFIG_COLUMN_NAME_DEVICE,
                                Schema.CONFIG_COLUMN_STATUS_OPERATION
                            };
        return this.sqlite_manager.query(Schema.TABLE_CONFIG, COLUMN, null, null, null, null, null);
    }

    protected Config readCursor(Cursor cursor)
    {
        Config config = new Config();
        cursor.moveToFirst();
        config.setHostReception(cursor.getString(cursor.getColumnIndexOrThrow(Schema.CONFIG_COLUMN_HOST_RECEPTION)));
        config.setHostSendMessage(cursor.getString(cursor.getColumnIndexOrThrow(Schema.CONFIG_COLUMN_HOST_SHIPPING_MESSAGE)));
        config.setNameDevice(cursor.getString(cursor.getColumnIndexOrThrow(Schema.CONFIG_COLUMN_NAME_DEVICE)));
        config.setStatusOperation(cursor.getString(cursor.getColumnIndexOrThrow(Schema.CONFIG_COLUMN_STATUS_OPERATION)));
        cursor.close();
        return config;
    }
    protected boolean setStatus(ContentValues contentValues)
    {
        final String ID = Schema._ID +" LIKE ?";
        final String SELECT_ARGS [] = {String.valueOf(1)};

        if(this.sqlite_manager.update(Schema.TABLE_CONFIG,contentValues, ID, SELECT_ARGS) != -1)
            return true;
        else return false;
    }
    private boolean thereAreElementsInTable()
    {
        boolean flag = false;
        Cursor cursor = this.sqlite_manager.rawQuery(Schema.SELECT_MAX_ID_MESSAGE, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0)
            flag = true;
        cursor.close();
        return flag;
    }
    private void removeData()
    {
        final String ARGS [] = {"1"};
        this.sqlite_manager.delete(Schema.TABLE_CONFIG, Schema._ID + "=?",ARGS);
    }
}
