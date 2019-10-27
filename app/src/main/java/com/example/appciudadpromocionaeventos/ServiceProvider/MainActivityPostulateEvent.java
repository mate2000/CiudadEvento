package com.example.appciudadpromocionaeventos.ServiceProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appciudadpromocionaeventos.Class.DataBaseHelper;
import com.example.appciudadpromocionaeventos.Class.Evento;
import com.example.appciudadpromocionaeventos.R;

import java.util.LinkedList;
import static com.example.appciudadpromocionaeventos.LoginActivities.MainLoginSP.user;

public class MainActivityPostulateEvent extends AppCompatActivity {

    EditText txtNombre, txtHoraIncio, txtFechaFinal, txtHoraFinal, txtLugar, txtDescripcion, txtFechaIncio;
    CheckBox cbFinanciacion,cbPublicidad;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_postulate_event);

        conectar();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enviar();
            }
        });

    }

    public void Enviar(){
        Evento evento = new Evento();
        evento.setNombre(txtNombre.getText().toString());
        evento.setFechaIncio(txtFechaIncio.getText().toString());
        evento.setFechaFinalizacion(txtFechaFinal.getText().toString());
        evento.setHoraInicio(txtHoraIncio.getText().toString());
        evento.setHoraFinalizacion(txtHoraFinal.getText().toString());
        evento.setLugar(txtLugar.getText().toString());
        evento.setDescripcion(txtDescripcion.getText().toString());
        evento.setFinanciado(false);
        evento.setEmail(user);
        evento.setAprobado(false);
        evento.setPublicitado(false);

        if(cbFinanciacion.isChecked()) {
            evento.setSocitarFinanciacion(true);
        }else{
            evento.setSocitarFinanciacion(false);
        }
        if(cbPublicidad.isChecked()){
            evento.setSoliciatarPublicidad(true);
        }else{
            evento.setSoliciatarPublicidad(false);
        }




        new DataBaseHelper().addEvento(evento, new DataBaseHelper.DataStatus() {
            @Override
            public void dataIsLoaded(LinkedList<Evento> eventos, LinkedList<String> keys) {
                Toast.makeText(getApplicationContext(),"El evento fue ingresado con exito", Toast.LENGTH_LONG).show();
            }

            @Override
            public void dataIsDeleated() {

            }

            @Override
            public void dataIsInserted() {

            }

            @Override
            public void dataIsUpdated() {

            }
        });
        Toast.makeText(getApplicationContext(),"El evento fue ingresado con exito", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), MainPanelServiceProvider.class));
    }

    public void validar(){}

    private void conectar() {
        btnEnviar=findViewById(R.id.btnEnviar);
        txtNombre=findViewById(R.id.txtNombre);
        txtFechaIncio=findViewById(R.id.txtFechaInicio);
        txtFechaFinal=findViewById(R.id.txtFechaFinalizacion);
        txtHoraIncio=findViewById(R.id.txtHorainicio);
        txtHoraFinal=findViewById(R.id.txtHoraFinalizacion);
        txtLugar=findViewById(R.id.txtLugar);
        txtDescripcion=findViewById(R.id.txtDescripcion);
        cbFinanciacion=findViewById(R.id.cbfinanciacionEvento);
        cbPublicidad=findViewById(R.id.cbPublicidadEvento);
    }


}
