package com.tomas.rastreame.models.manager_database;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;
import com.tomas.rastreame.models.objects.MessageOBJ;
import com.tomas.rastreame.models.database.Schema;

import java.util.ArrayList;

/**
 * Created by Tomas on 22/10/2015.
 */
class Table_Message
{
    private SQLiteDatabase sqlite_manager;

    public Table_Message(SQLiteDatabase sql)
    {
        this.sqlite_manager = sql;
    }

    protected void insertIntoToTableMessage(ContentValues contentValues)
    {
        this.sqlite_manager.insertOrThrow(Schema.TABLE_MESSAGE, Schema.COLUMN_NULL, contentValues);
    }
    protected Cursor readingDataFromTheTableMessage()
    {
        String [] COLUMNS =
                            {
                                Schema._ID,
                                Schema.MESSAGE_COLUMN_CONTAINER_MESSAGE,
                                Schema.MESSAGE_COLUMN_NUMBER_MESSAGE,
                                Schema.MESSAGE_COLUMN_DATE,
                                Schema.MESSAGE_COLUMN_HOUR_MINUTE,
                                Schema.MESSAGE_COLUMN_SECONDS
                            };
        return this.sqlite_manager.query(Schema.TABLE_MESSAGE, COLUMNS, null,null,null,null,null);
    }
    protected ArrayList<MessageOBJ> readCursor(Cursor cursor)
    {
        ArrayList<MessageOBJ> list = new ArrayList<>();
        cursor.moveToFirst();
        for(int count = 0; count < cursor.getCount(); count++)
        {
            MessageOBJ message = new MessageOBJ();
            message.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Schema._ID)));
            message.setBody(cursor.getString(cursor.getColumnIndexOrThrow(Schema.MESSAGE_COLUMN_CONTAINER_MESSAGE)));
            message.setNumberMessage(cursor.getString(cursor.getColumnIndexOrThrow(Schema.MESSAGE_COLUMN_NUMBER_MESSAGE)));
            message.setDate(cursor.getString(cursor.getColumnIndexOrThrow(Schema.MESSAGE_COLUMN_DATE)));
            message.setHour(cursor.getString(cursor.getColumnIndexOrThrow(Schema.MESSAGE_COLUMN_HOUR_MINUTE)));
            message.setSeconds(cursor.getString(cursor.getColumnIndexOrThrow(Schema.MESSAGE_COLUMN_SECONDS)));
            list.add(message);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    protected void removeItem(String[] args)
    {
        this.sqlite_manager.delete(Schema.TABLE_MESSAGE, Schema._ID + "=?", args);
    }
    protected int getMaxId()
    {
        Cursor cursor = this.sqlite_manager.rawQuery(Schema.SELECT_MAX_ID_MESSAGE, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(Schema._ID));
        cursor.close();
        return id;
    }
}
