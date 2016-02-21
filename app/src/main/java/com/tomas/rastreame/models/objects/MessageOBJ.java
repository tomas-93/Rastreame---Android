package com.tomas.rastreame.models.objects;

/**
 * Created by Tomas on 22/10/2015.
 */
public class MessageOBJ
{
    public static final int MESSAGE = 551;


    private int id;
    private String numberMessage, body, date, hour, seconds;

    public MessageOBJ()
    {

    }

    public MessageOBJ(int id, String numberMessage, String body, String date, String hour, String seconds)
    {
        this.id = id;
        this.numberMessage = numberMessage;
        this.body = body;
        this.date = date;
        this.hour = hour;
        this.seconds = seconds;
    }
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNumberMessage()
    {
        return numberMessage;
    }

    public void setNumberMessage(String numberMessage)
    {
        this.numberMessage = numberMessage;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getHour()
    {
        return hour;
    }

    public void setHour(String hour)
    {
        this.hour = hour;
    }

    public String getSeconds()
    {
        return seconds;
    }

    public void setSeconds(String seconds)
    {
        this.seconds = seconds;
    }


}
