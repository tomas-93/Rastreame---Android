package com.tomas.rastreame.controller.controller_view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.tomas.rastreame.R;
/**
 * Created by Tomas on 25/10/2015.
 */
public class DialogMessageNullArgumentsException extends DialogFragment
{
    private String message;
    private String title;
    @Override
     public Dialog onCreateDialog(Bundle saveInstace)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(this.title)
                .setIcon(R.drawable.ic_explicit_black_48dp)
                .setMessage(this.message);
        return builder.create();
    }
    public void setMenssage(String message)
    {
        this.message = message;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
}
