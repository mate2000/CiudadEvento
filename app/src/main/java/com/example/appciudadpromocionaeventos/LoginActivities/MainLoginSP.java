package com.example.appciudadpromocionaeventos.LoginActivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appciudadpromocionaeventos.Class.UserServiceProvider;
import com.example.appciudadpromocionaeventos.R;
import com.example.appciudadpromocionaeventos.ServiceProvider.MainActivityPostulateEvent;
import com.example.appciudadpromocionaeventos.ServiceProvider.MainPanelServiceProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainLoginSP extends AppCompatActivity {

    EditText etLSPUser, etLSPPassword;
    Button btnLSPEnter;
    public static String user = "";

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_sp);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        connect();

        btnLSPEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etLSPUser.getText().toString().equals("") || etLSPPassword.getText().toString().equals("")
                        || etLSPUser.getText().toString().equals(" ") || etLSPPassword.getText().toString().equals(" ")){
                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_LONG).show();
                }else{
                    if(network() != null && network().isConnected()) {
                        // Si hay conexión a Internet en este momento
                        user = etLSPUser.getText().toString().trim();
                        String pass = etLSPPassword.getText().toString().trim();
                        login(user, pass);
                    }else{
                        // No hay conexión a Internet en este momento
                        Toast.makeText(getApplicationContext(), "No puedes acceder si no tienes conexion a internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void connect(){
        etLSPUser = findViewById(R.id.etLSPUser);
        etLSPPassword = findViewById(R.id.etLSPPassword);
        btnLSPEnter = findViewById(R.id.btnLSPEnter);
    }

    public NetworkInfo network(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    private void login(String email, String password){
        progressDialog.setMessage("Realizando ingreso en linea...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "ingresando y cargando", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainPanelServiceProvider.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Correo y/o clave incorrecta", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

                });

    }
}