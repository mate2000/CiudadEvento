package com.example.appciudadpromocionaeventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appciudadpromocionaeventos.LoginActivities.MainLoginSP;
import com.example.appciudadpromocionaeventos.LoginActivities.MainLoginTS;
import com.example.appciudadpromocionaeventos.RegisterActivities.MainRegisterSP;

public class MainPrincipalScreen extends AppCompatActivity {

    TextView tvMessageWelcome;
    Button btnRegisterCitizen, btnRegisterServiceProvider, btnRegisterTourismSecretary, btnLoginCitizen, btnLoginServiceProvider,
            btnLoginTourismSecretary;
    String message = "Ahora podras buscar tus eventos preferidos de la manera más rapida, crear " +
            "el tuyo poropio o aceptar los que prefieras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal_screen);

        connect();

        btnRegisterServiceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainRegisterSP.class)); //MainRegisterServiceProvider
            }
        });
        btnLoginServiceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainLoginSP.class)); //MainRegisterServiceProvider
            }
        });
        btnLoginTourismSecretary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainLoginTS.class));
            }
        });
    }

    private void connect() {
        tvMessageWelcome = findViewById(R.id.tvMessageWelcome);
        btnRegisterCitizen = findViewById(R.id.btnRegisterCitizen);
        btnRegisterServiceProvider = findViewById(R.id.btnRegisterServiceProvider);
        btnRegisterTourismSecretary = findViewById(R.id.btnRegisterTourismSecretary);
        btnLoginCitizen = findViewById(R.id.btnLoginCitizen);
        btnLoginServiceProvider = findViewById(R.id.btnLoginServiceProvider);
        btnLoginTourismSecretary = findViewById(R.id.btnLoginTourismSecretary);

        tvMessageWelcome.setText(message);
        btnRegisterTourismSecretary.setEnabled(false);
        btnLoginCitizen.setEnabled(false);
        btnRegisterCitizen.setEnabled(false);
    }

}
