package com.example.appciudadpromocionaeventos.LoginActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appciudadpromocionaeventos.R;
import com.example.appciudadpromocionaeventos.ServiceProvider.MainActivityPostulateEvent;
import com.example.appciudadpromocionaeventos.TourismSecretary.MainPanelTourismSecretary;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainLoginTS extends AppCompatActivity {

    EditText etLTSUser, etLTSPassword;
    Button btnLTSEnter;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_ts);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        connect();

        btnLTSEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etLTSUser.getText().toString().equals("") || etLTSPassword.getText().toString().equals("")
                        || etLTSUser.getText().toString().equals(" ") || etLTSPassword.getText().toString().equals(" ")){
                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_LONG).show();
                }else{
                    if(network() != null && network().isConnected()) {
                        // Si hay conexión a Internet en este momento
                        String email = etLTSUser.getText().toString().trim() + "@hotmail.com";
                        String pass = etLTSPassword.getText().toString();
                        login(email, pass);
                    }else{
                        // No hay conexión a Internet en este momento
                        Toast.makeText(getApplicationContext(), "No puedes acceder si no tienes conexion a internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void connect(){
        etLTSUser = findViewById(R.id.etLTSUser);
        etLTSPassword = findViewById(R.id.etLTSPassword);
        btnLTSEnter = findViewById(R.id.btnLTSEnter);
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
                            startActivity(new Intent(getApplicationContext(), MainPanelTourismSecretary.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Correo y/o clave incorrecta", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

                });

    }

}
