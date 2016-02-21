package com.tomas.rastreame.models.objects;

/**
 * Created by Tomas on 24/10/2015.
 */
public class Config
{
    public static final int CONFIG = 314;
    private String hostSendMessage, nameDevice, statusOperation, hostReception;

    public Config()
    {
    }

    public Config(String hostSendMessage, String nameDevice, String statusOperation, String hostReception)
    {
        this.hostSendMessage = hostSendMessage;
        this.nameDevice = nameDevice;
        this.statusOperation = statusOperation;
        this.hostReception = hostReception;
    }
    public String getHostSendMessage()
    {
        return hostSendMessage;
    }

    public void setHostSendMessage(String hostSendMessage)
    {
        this.hostSendMessage = hostSendMessage;
    }

    public String getNameDevice()
    {
        return nameDevice;
    }

    public void setNameDevice(String nameDivice)
    {
        this.nameDevice = nameDivice;
    }

    public String getStatusOperation()
    {
        return statusOperation;
    }

    public void setStatusOperation(String statusOperation)
    {
        this.statusOperation = statusOperation;
    }

    public String getHostReception()
    {
        return hostReception;
    }

    public void setHostReception(String hostReception)
    {
        this.hostReception = hostReception;
    }
}
