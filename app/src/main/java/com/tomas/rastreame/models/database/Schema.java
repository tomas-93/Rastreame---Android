package com.tomas.rastreame.models.database;

import android.provider.BaseColumns;

/**
 * Created by Tomas on 22/10/2015.
 */
public class Schema implements BaseColumns
{
    //Table
    public static final String TABLE_CONFIG = "config";
    public static final String TABLE_MESSAGE = "message";

    //Columns Confing
    public static final String CONFIG_COLUMN_HOST_SHIPPING_MESSAGE = "hostMessage";//enviar mensajes recibidos
    public static final String CONFIG_COLUMN_NAME_DEVICE  = "nameDevice";
    public static final String CONFIG_COLUMN_STATUS_OPERATION = "statusOperation";
    public static final String CONFIG_COLUMN_HOST_RECEPTION = "hotsReception";
    //Column MessageOBJ
    public static final String MESSAGE_COLUMN_NUMBER_MESSAGE = "numberMessage";
    public static final String MESSAGE_COLUMN_CONTAINER_MESSAGE = "containerMessage";
    public static final String MESSAGE_COLUMN_DATE ="date";
    public static final String MESSAGE_COLUMN_HOUR_MINUTE ="hourMinute";
    public static final String MESSAGE_COLUMN_SECONDS= "seconds";

    public static final String COLUMN_NULL = null;

    //Create Table

    protected static final String CREATE_TABLE_CONFIG = "CREATE TABLE " + TABLE_CONFIG +" ("+
            BaseColumns._ID + " INTEGER PRIMARY KEY, "  +
            CONFIG_COLUMN_HOST_SHIPPING_MESSAGE + " TEXT, " +
            CONFIG_COLUMN_NAME_DEVICE + " TEXT, " +
            CONFIG_COLUMN_STATUS_OPERATION + " TEXT, " +
            CONFIG_COLUMN_HOST_RECEPTION + " TEXT );" ;

    protected static final String CREATE_TABLE_MESSAGE = "CREATE TABLE " + TABLE_MESSAGE + " ("+
            BaseColumns._ID + " INTEGER PRIMARY KEY, " +
            MESSAGE_COLUMN_NUMBER_MESSAGE + " TEXT, " +
            MESSAGE_COLUMN_CONTAINER_MESSAGE + " TEXT, " +
            MESSAGE_COLUMN_DATE + " TEXT, "+
            MESSAGE_COLUMN_HOUR_MINUTE + " TEXT, " +
            MESSAGE_COLUMN_SECONDS + " TEXT );";

    //Drop

    public static final String DROP_TABLE_MESSAGE = "DROP TABLE IF EXISTS "+TABLE_MESSAGE;
    public static final String DROP_TABLE_CONFIG = "DROP TABLE IF EXISTS "+TABLE_CONFIG;
    //Count id
    public static final String COUNT_ID_MESSAGE = "SELECT COUNT(*) AS " +BaseColumns._ID+ " FROM " + TABLE_MESSAGE;

    //Max id
    public static final String SELECT_MAX_ID_MESSAGE = "SELECT MAX("+ BaseColumns._ID+") AS "+BaseColumns._ID+" FROM "+ TABLE_MESSAGE;
    //
}
