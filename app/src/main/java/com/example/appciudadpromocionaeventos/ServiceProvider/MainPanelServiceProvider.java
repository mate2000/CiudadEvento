package com.example.appciudadpromocionaeventos.ServiceProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.appciudadpromocionaeventos.R;

public class MainPanelServiceProvider extends AppCompatActivity {

    Button btnPostEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel_service_provider);

        btnPostEvents = findViewById(R.id.btnPostEvents);

        btnPostEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivityPostulateEvent.class));
            }
        });

    }
}
