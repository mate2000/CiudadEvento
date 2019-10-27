package com.example.appciudadpromocionaeventos.Class;

//import android.support.annotation.NonNull;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class DataBaseHelper {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private LinkedList<Evento>  eventos = new LinkedList<>();

    public interface DataStatus{
        void dataIsLoaded(LinkedList<Evento> eventos,LinkedList<String> keys);
        void dataIsDeleated();
        void dataIsInserted();
        void dataIsUpdated();
    }

    public DataBaseHelper(){
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Evento");
    }

    public void readEvento(final DataStatus dataStatus){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventos.clear();
                LinkedList<String> keys = new LinkedList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Evento evento =(Evento)keyNode.getValue();
                    eventos.add(evento);
                }
                dataStatus.dataIsLoaded(eventos,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addEvento(Evento evento,final DataStatus dataStatus){
        String key = reference.push().getKey();
        reference.child(key).setValue(evento).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.dataIsInserted();
            }
        });
    }
}