package com.tomas.rastreame.controller.controller_view;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tomas.rastreame.R;
import com.tomas.rastreame.controller.controller_services.ServiceReceptionMessage;
import com.tomas.rastreame.controller.controller_services.ServicesSendMessage;
import com.tomas.rastreame.controller.controller_view.dialog.DialogMessageNullArgumentsException;
import com.tomas.rastreame.controller.controller_view.dialog.Info;
import com.tomas.rastreame.models.objects.Config;
import com.tomas.rastreame.models.manager_database.SQLite_Manager;
import com.tomas.rastreame.utils.exception.NullArgumentsException;

public class Main extends AppCompatActivity implements OnClickListener
{
    private ImageButton editElements, saveData;
    private CheckBox checkBox;
    private Animation animation;
    private Config config;
    private SQLite_Manager sqLite_manager;
    private EditText urlSend, urlReception, nameDevice;
    private TextView dataConfig, dataMessage;
    private ServiceReceptionMessage serviceReceptionMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        //instace
        this.instace();
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == this.editElements.getId())
        {
            this.editElements.startAnimation(this.animation);
            this.processEdit();

        }else if(view.getId() == this.saveData.getId())
        {
            this.saveData.startAnimation(this.animation);
            this.save();
        }else if(view.getId() == this.checkBox.getId())
        {
            this.processCheckBox();
            Snackbar.make(this.getWindow().getDecorView().getRootView(), "Ha cambiado el Estado del proceso", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            Info info = new Info();
            info.show(this.getFragmentManager(), "Dialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
            Metodos Privados....
     */
    private void instace()
    {
        //DataBase
        this.sqLite_manager = new SQLite_Manager(this.getApplicationContext());
        //TextView
        this.dataConfig = (TextView) this.findViewById(R.id.dataConfigXML);
        this.dataMessage = (TextView) this.findViewById(R.id.dataMessageXML);
        //EditText
        this.urlSend = (EditText) this.findViewById(R.id.urlSendMessageXML);
        this.urlReception = (EditText) this.findViewById(R.id.urlReceptionMessageXML);
        this.nameDevice = (EditText) this.findViewById(R.id.nameDiviceXML);
        //ImageButton
        this.editElements = (ImageButton) this.findViewById(R.id.editElementXML);
        this.saveData = (ImageButton) this.findViewById(R.id.saveXML);
        //CheckBox
        this.checkBox = (CheckBox) this.findViewById(R.id.checkBoxXML);
        //Evento
        this.editElements.setOnClickListener(this);
        this.saveData.setOnClickListener(this);
        this.checkBox.setOnClickListener(this);
        //Service
        this.serviceReceptionMessage = new ServiceReceptionMessage();
        this.serviceReceptionMessage.onService(this.getApplicationContext());
        //Animation
        this.animation = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.animation_buttom);
        this.loadInfo();
    }

    private void loadInfo()
    {
        String messageConfiguration ="";
        String messageConfigMessage ="";
        try
        {
            this.config = this.sqLite_manager.readConfign();
            messageConfiguration += "URL: " + this.config.getHostSendMessage() +
                                    "\nNombre: " +  this.config.getNameDevice() +
                                    "\nProceso: " + this.config.getStatusOperation();
            messageConfigMessage += "URL: " + this.config.getHostReception();
            if(config.getStatusOperation().equalsIgnoreCase("Activado"))
                this.checkBox.setChecked(true);
            else this.checkBox.setChecked(false);

        }catch (CursorIndexOutOfBoundsException ex)
        {
            messageConfigMessage += "No se encuentran registros guardados.";
            messageConfiguration += "No se encuentran registros guardados.";
            this.config = new Config();
        }finally
        {
            this.dataConfig.setText(messageConfiguration, TextView.BufferType.NORMAL);
            this.dataMessage.setText(messageConfigMessage, TextView.BufferType.NORMAL);
        }

    }

    private void processEdit()
    {
        try
        {
            this.validateObject();
            this.nameDevice.setText(this.config.getNameDevice(), TextView.BufferType.NORMAL);
            this.urlSend.setText(this.config.getHostSendMessage(), TextView.BufferType.NORMAL);
            this.urlReception.setText(this.config.getHostReception(), TextView.BufferType.NORMAL);
            if(this.config.getStatusOperation().equalsIgnoreCase("Activado"))
                this.checkBox.setChecked(true);
            else this.checkBox.setChecked(false);
        }catch (NullArgumentsException e)
        {
            final String title = "Error, No hay elementos registrados.";
            DialogMessageNullArgumentsException dialogMessageNullPointereException = new DialogMessageNullArgumentsException();
            dialogMessageNullPointereException.setMenssage(e.getMessage() + "\n\nTiene que guardar elementos para poder editar.");
            dialogMessageNullPointereException.setTitle(title);
            dialogMessageNullPointereException.show(this.getFragmentManager(), "Dialog");
            this.checkBox.setChecked(false);
        }
    }

    private void processCheckBox()
    {
        try
        {
            this.validateObject();
            if(this.checkBox.isChecked())
                this.config.setStatusOperation("Activado");
            else
                this.config.setStatusOperation("Desactivado");

            String message;
            if(this.sqLite_manager.setStatusConfig(this.config.getStatusOperation()))
            {
                message = "Proceso Activado";
                this.config.setStatusOperation("Activado");
            }
            else message = "Proceso Desactivado";

            Snackbar.make(this.getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG).show();
        }catch (NullArgumentsException e)
        {
            final String title = "Error, No se puede activar el proceso.";
            DialogMessageNullArgumentsException dialogMessageNullPointereException = new DialogMessageNullArgumentsException();
            dialogMessageNullPointereException.setMenssage(e.getMessage());
            dialogMessageNullPointereException.setTitle(title);
            dialogMessageNullPointereException.show(this.getFragmentManager(), "Dialog");
            this.checkBox.setChecked(false);
        }
    }

    private void save()
    {
        try
        {
            this.validateData();
            this.config.setHostReception(this.urlReception.getText().toString());
            this.config.setHostSendMessage(this.urlSend.getText().toString());
            if(this.checkBox.isChecked())
                this.config.setStatusOperation("Activado");
            else this.config.setStatusOperation("Desactivado");
            this.config.setNameDevice(this.nameDevice.getText().toString());
            //Guardar elementos a la base de datos
            this.sqLite_manager.insertDataConfig(this.config);
            this.loadInfo();
            this.urlReception.setText("", TextView.BufferType.NORMAL);
            this.urlSend.setText("", TextView.BufferType.NORMAL);
            this.nameDevice.setText("", TextView.BufferType.NORMAL);
        }catch (NullArgumentsException e)
        {
            final String title = "Error, No se puede guardar los datos.";
            DialogMessageNullArgumentsException dialogMessageNullPointereException = new DialogMessageNullArgumentsException();
            dialogMessageNullPointereException.setMenssage(e.getMessage());
            dialogMessageNullPointereException.setTitle(title);
            dialogMessageNullPointereException.show(this.getFragmentManager(), "Dialog");

        }

    }

    private void validateData()throws NullArgumentsException
    {
        boolean flag = false;
        String message = "";
        if(this.urlReception.getText().toString() == null ||
                this.urlReception.getText().toString().equals(""))
        {
            message += "URL del host donde se van a enviar los mensajes, no puede estar vacion.\n\n";
            flag = true;
        }
        if(this.urlSend.getText().toString() == null ||
                this.urlSend.getText().toString().equals(""))
        {
            message += "URL del host donde se recivira el mensaje, no puede estar vacion.\n\n";
            flag = true;
        }
        if(this.nameDevice.getText().toString() == null ||
                this.nameDevice.getText().toString().equals(""))
        {
            message += "El campo del nombre del dispositivo, no puede estar vacion.";
            flag = true;
        }
        if(flag)
        {
            throw new NullArgumentsException(message);
        }
    }

    private void validateObject()throws NullArgumentsException
    {
        if(this.config.getNameDevice() == null ||
                this.config.getHostReception() == null ||
                this.config.getHostSendMessage() == null)
            throw new NullArgumentsException("No puede tener los campos vacion, ó no hay elementos guardados previamente.");

    }

}
