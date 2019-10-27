package com.example.appciudadpromocionaeventos.TourismSecretary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appciudadpromocionaeventos.Class.Evento;
import com.example.appciudadpromocionaeventos.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;
import java.util.ArrayList;

public class MainPostulatedEventPanel extends AppCompatActivity implements Serializable {

    TextView tvNameEventPTS, tvPlaceEventPTS, tvDateEventPTS, tvHoursEventPTS, tvDescrEventPTS,
            tvEmailEventPTS, tvMessage3, tvMessage4,tvMessage5;
    Button btnSeeRUTPTS, btnHideRUTPTS, btnYesApproveEventPTS, btnNoApproveEventPTS,
            btnYesFinancingEventPTS, btnNoFinancingEventPTS, btnYesPublicity,btnNopublicity;
    ImageView ivRUTPTS; //PTS = Panel Tourism Secretary

    Evento e;
    String nameEvent = "", email = "", uid = "", nameThisEvent = "";
    Object url;




    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_postulated_event_panel);

        connectAndToShow();

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        btnSeeRUTPTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase.child("UserServiceProvider").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                if(email.equals(ds.child("email").getValue().toString())){
                                    url = ds.child("rut").getValue();
                                    seeRUT(true);

                                    Glide.with(getApplicationContext())
                                            .load(url)
                                            .fitCenter()
                                            .centerCrop()
                                            .into(ivRUTPTS);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        btnHideRUTPTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeRUT(false);
            }
        });
        btnYesApproveEventPTS.setOnClickListener(new View.OnClickListener() {
            //Verificar si solicito financiacion y publicidad
            @Override
            public void onClick(View v) {

                /*firebaseDatabase.child("Evento").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                if(email.equals(ds.child("email").getValue().toString())){
                                    uid = ds.getKey();
                                    firebaseDatabase.child("Evento").child(uid).child("aprobado").setValue(true);
                                    Toast.makeText(getApplicationContext(), "El evento fue aprobado", Toast.LENGTH_SHORT).show();
                                    tvMessage4.setVisibility(View.VISIBLE);
                                    btnYesFinancingEventPTS.setVisibility(View.VISIBLE);
                                    btnNoFinancingEventPTS.setVisibility(View.VISIBLE);
                                    btnYesApproveEventPTS.setEnabled(false);
                                    btnNoApproveEventPTS.setEnabled(false);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "El evento NO fue aprobado", Toast.LENGTH_SHORT).show();
                    }
                });*/

                firebaseDatabase.child("Evento").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                if(email.equals(ds.child("email").getValue().toString()) &&
                                        nameThisEvent.equals(ds.child("nombre").getValue().toString())){
                                    uid = ds.getKey();
                                    firebaseDatabase.child("Evento").child(uid).child("aprobado").setValue(true);
                                    Toast.makeText(getApplicationContext(), "El evento fue aprobado", Toast.LENGTH_SHORT).show();
                                    /////Verificar si pidio financacion o publicidad
                                    tvMessage4.setVisibility(View.VISIBLE);
                                    tvMessage5.setVisibility(View.VISIBLE);
                                    btnYesFinancingEventPTS.setVisibility(View.VISIBLE);
                                    btnNoFinancingEventPTS.setVisibility(View.VISIBLE);
                                    btnYesApproveEventPTS.setEnabled(false);
                                    btnNoApproveEventPTS.setEnabled(false);
                                    btnYesPublicity.setVisibility(View.VISIBLE);
                                    btnNopublicity.setVisibility(View.VISIBLE);
                                    btnYesPublicity.setEnabled(false);
                                    btnNopublicity.setEnabled(false);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "El evento NO fue aprobado", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnNoApproveEventPTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase.child("Evento").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            if(email.equals(dataSnapshot.child("email").getValue().toString()) &&
                                nameThisEvent.equals(dataSnapshot.child("nombre").getValue().toString())){
                                //firebaseDatabase.child("Evento").child(uid).setValue(null);
                                Toast.makeText(getApplicationContext(), "El evento fue aprobado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "El evento NO fue aprobado", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnYesApproveEventPTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnNoFinancingEventPTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mandar informacion al prestador de servicio
            }
        });

        btnYesPublicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnNopublicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void connectAndToShow(){
        tvNameEventPTS = findViewById(R.id.tvNameEventPTS);
        tvPlaceEventPTS = findViewById(R.id.tvPlaceEventPTS);
        tvDateEventPTS = findViewById(R.id.tvDateEventPTS);
        tvHoursEventPTS = findViewById(R.id.tvHoursEventPTS);
        tvDescrEventPTS = findViewById(R.id.tvDescrEventPTS);
        tvEmailEventPTS = findViewById(R.id.tvEmailEventPTS);
        tvMessage5=findViewById(R.id.tvMessage5);
        btnSeeRUTPTS = findViewById(R.id.btnSeeRUTPTS);
        btnHideRUTPTS = findViewById(R.id.btnHideRUTPTS);
        ivRUTPTS = findViewById(R.id.ivRUTPTS);
        btnYesApproveEventPTS = findViewById(R.id.btnYesApproveEventPTS);
        btnNoApproveEventPTS = findViewById(R.id.btnNoApproveEventPTS);
        btnYesFinancingEventPTS = findViewById(R.id.btnYesFinancingEventPTS);
        btnNoFinancingEventPTS = findViewById(R.id.btnNoFinancingEventPTS);
        btnYesPublicity=findViewById(R.id.btnYesPublicity);
        btnNopublicity=findViewById(R.id.btnNoPublicity);
        tvMessage3 = findViewById(R.id.tvMessage3);
        tvMessage4 = findViewById(R.id.tvMessage4);

        btnHideRUTPTS.setVisibility(View.INVISIBLE);
        ivRUTPTS.setVisibility(View.INVISIBLE);
        btnYesApproveEventPTS.setEnabled(false);
        btnNoApproveEventPTS.setEnabled(false);
        btnYesFinancingEventPTS.setVisibility(View.INVISIBLE);
        btnNoFinancingEventPTS.setVisibility(View.INVISIBLE);
        tvMessage4.setVisibility(View.INVISIBLE);
        tvMessage3.setVisibility(View.INVISIBLE);
        btnYesApproveEventPTS.setVisibility(View.INVISIBLE);
        btnNoApproveEventPTS.setVisibility(View.INVISIBLE);

        e = MainPanelTourismSecretary.toPss;
        nameEvent = getIntent().getExtras().getString("nameEvent");
        email = getIntent().getExtras().getString("email");
        nameThisEvent = e.getNombre();

        tvNameEventPTS.setText(nameEvent);
        tvPlaceEventPTS.setText(e.getLugar());
        tvDateEventPTS.setText(e.getFechaIncio() + "\t    " + e.getFechaFinalizacion());
        tvHoursEventPTS.setText(e.getHoraInicio() + "\t   " + e.getHoraFinalizacion());
        tvDescrEventPTS.setText(e.getDescripcion());
        tvEmailEventPTS.setText(e.getEmail());
    }

    private void seeRUT(boolean o){
        if(o){
            tvNameEventPTS.setVisibility(View.INVISIBLE);
            tvPlaceEventPTS.setVisibility(View.INVISIBLE);
            tvDateEventPTS.setVisibility(View.INVISIBLE);
            tvHoursEventPTS.setVisibility(View.INVISIBLE);
            tvDescrEventPTS.setVisibility(View.INVISIBLE);
            tvEmailEventPTS.setVisibility(View.INVISIBLE);
            btnSeeRUTPTS.setVisibility(View.INVISIBLE);
            btnYesApproveEventPTS.setVisibility(View.INVISIBLE);
            btnNoApproveEventPTS.setVisibility(View.INVISIBLE);
            btnYesFinancingEventPTS.setVisibility(View.INVISIBLE);
            btnNoFinancingEventPTS.setVisibility(View.INVISIBLE);
            tvMessage3.setVisibility(View.INVISIBLE);
            tvMessage4.setVisibility(View.INVISIBLE);
            btnHideRUTPTS.setVisibility(View.VISIBLE);
            ivRUTPTS.setVisibility(View.VISIBLE);
        }else{
            tvNameEventPTS.setVisibility(View.VISIBLE);
            tvPlaceEventPTS.setVisibility(View.VISIBLE);
            tvDateEventPTS.setVisibility(View.VISIBLE);
            tvHoursEventPTS.setVisibility(View.VISIBLE);
            tvDescrEventPTS.setVisibility(View.VISIBLE);
            tvEmailEventPTS.setVisibility(View.VISIBLE);
            btnSeeRUTPTS.setVisibility(View.VISIBLE);
            btnYesApproveEventPTS.setVisibility(View.VISIBLE);
            btnNoApproveEventPTS.setVisibility(View.VISIBLE);
            tvMessage3.setVisibility(View.VISIBLE);
            btnHideRUTPTS.setVisibility(View.INVISIBLE);
            ivRUTPTS.setVisibility(View.INVISIBLE);
            btnYesApproveEventPTS.setEnabled(true);
            btnNoApproveEventPTS.setEnabled(true);
        }
    }

    private void approveFunding(){

        firebaseDatabase.child("Evento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                        if(email.equals(ds.child("email").getValue().toString()) &&
                                nameThisEvent.equals(ds.child("nombre").getValue().toString())){
                            uid = ds.getKey();
                            firebaseDatabase.child("Evento").child(uid).child("financiado").setValue(true);
                            Toast.makeText(getApplicationContext(), "El evento fue financiado", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    private void denyFunding(){}

    private void approvePublicity(){}

    private void denyPublicity(){


    }

}


















