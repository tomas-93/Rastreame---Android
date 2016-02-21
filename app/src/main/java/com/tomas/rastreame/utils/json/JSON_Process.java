package com.tomas.rastreame.utils.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tomas on 30/10/2015.
 */
public class JSON_Process extends JSONObject
{
    private String number, message;
    public void defragmentJSON(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            this.number = jsonObject.getString("number");
            this.message = jsonObject.getString("message");
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    public String getNumber()
    {
        return number;
    }
    public String getMessage()
    {
        return message;
    }
}
