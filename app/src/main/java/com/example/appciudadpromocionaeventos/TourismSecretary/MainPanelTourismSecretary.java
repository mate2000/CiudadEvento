package com.example.appciudadpromocionaeventos.TourismSecretary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appciudadpromocionaeventos.Class.Evento;
import com.example.appciudadpromocionaeventos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class MainPanelTourismSecretary extends AppCompatActivity {

    ListView lvRecords;
    ArrayList<String> listado;
    static ArrayList<Evento> eventos = new ArrayList<>();
    static Evento toPss;
    String emaill = "", nameEvent = "";

    Evento e;

    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel_tourism_secretary);

        lvRecords = findViewById(R.id.lvRecords);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        getEventosFromFireBase();

        lvRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toPss = null;
                Intent intent = new Intent(getApplicationContext(), MainPostulatedEventPanel.class);
                for(Evento e : eventos){
                    if(e.getNombre().equalsIgnoreCase(listado.get(position))){
                        toPss = e;
                    }
                }
                //intent.putExtra("Object", (CharSequence) toPss);
                intent.putExtra("nameEvent", listado.get(position));
                intent.putExtra("email", emaill);
                intent.putExtra("nameEvent2", nameEvent);
                startActivity(intent);
            }
        });

    }

    private void getEventosFromFireBase() {
        final ArrayList<String> datos = new ArrayList<>();

        mDataBase.child("Evento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    datos.clear();
                    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                        String nombreEvento = ds.child("nombre").getValue().toString();
                        datos.add(nombreEvento);
                        String lugar = ds.child("lugar").getValue().toString();
                        String fechaInicio = ds.child("fechaIncio").getValue().toString();
                        String fechaFinal = ds.child("fechaFinalizacion").getValue().toString();
                        String horaInicio = ds.child("horaInicio").getValue().toString();
                        String horaFinal = ds.child("horaFinalizacion").getValue().toString();
                        String descripcion = ds.child("descripcion").getValue().toString();
                        boolean SolicitudFinanciacion =  (boolean)ds.child("SocitarFinanciacion").getValue();
                        boolean SolicitudPublicidad =  (boolean)ds.child("soliciatarPublicidad").getValue();
                        //boolean financiado = ((boolean) ds.child("financiado").getValue());
                        String email = ds.child("email").getValue().toString();
                        emaill = email;
                        nameEvent = nombreEvento;

                        e = new Evento(nombreEvento, lugar, fechaInicio, fechaFinal, horaInicio,
                                horaFinal, descripcion, false, email, false,false,SolicitudFinanciacion,SolicitudPublicidad);

                        eventos.add(e);
                    }

                    listado = datos;
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listado);
                    lvRecords.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}