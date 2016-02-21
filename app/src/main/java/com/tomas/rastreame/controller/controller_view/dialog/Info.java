package com.tomas.rastreame.controller.controller_view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.tomas.rastreame.R;

/**
 * Created by Tomas on 01/11/2015.
 */
public class Info extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle saveInstace)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Desarrollado por.")
                .setIcon(R.drawable.ic_face_black_48dp)
                .setMessage("Tomas Yussef Galicia Guzman.");
        return builder.create();
    }
}
