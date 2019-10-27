package com.example.appciudadpromocionaeventos.RegisterActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appciudadpromocionaeventos.Class.UserServiceProvider;
import com.example.appciudadpromocionaeventos.LoginActivities.MainLoginSP;
import com.example.appciudadpromocionaeventos.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class MainRegisterSP extends AppCompatActivity {

    EditText etRSPUser, etRSPPassword, etRSPIdentificationCard, etRSPEmail, etRSPFullName, etRSPCellphone;
    Button btnRSPAddRUT, btnRSPSeeRUT, btnRSPRegister, btnRSPBackSeeRUT;
    ImageView ivRSPRUT;
    String user = "", pass = "", identifiedCard = "", email = "", fullName = "", cellphone = "";
    String path, namePic = "";
    ArrayList<String> idetifiedCardExists = new ArrayList<>();
    String downUriStr = "";

    private static final int GALLERY_INTENT = 1;

    private ProgressDialog progressDialog; //Barra de progreso
    private FirebaseAuth myAuth; //Para autenticarnos
    FirebaseDatabase firebaseDatabase; //referencia a firebas
    DatabaseReference databaseReference; //Montar datos
    DatabaseReference databaseReference2; //Coger datos

    private StorageReference mStorageRef; //montar imagenes en firebas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_sp);

        connect();

        btnRSPAddRUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
        btnRSPSeeRUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeRUT(false);
                ivRSPRUT.setImageURI(Uri.parse(path));
            }
        });
        btnRSPRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = etRSPUser.getText().toString();
                pass = etRSPPassword.getText().toString();
                identifiedCard = etRSPIdentificationCard.getText().toString().trim();
                email = etRSPEmail.getText().toString().trim();
                fullName = etRSPFullName.getText().toString();
                cellphone = etRSPCellphone.getText().toString().trim();

                if(user.equals("") || user.equals(" ") || pass.equals("") || pass.equals(" ") || identifiedCard.equals("")
                        || identifiedCard.equals(" ") || email.equals("") || email.equals(" ") || fullName.equals("")
                        || fullName.equals(" ") || cellphone.equals("") || cellphone.equals(" ") || path.equals("")
                        || path.equals(" ")){

                    Toast.makeText(getApplicationContext(), "Todos los campos deben de estar llenos", Toast.LENGTH_LONG).show();
                }else{
                    if(identifiedCardExist()){
                        Toast.makeText(getApplicationContext(), "Esa cedula ya existe", Toast.LENGTH_SHORT).show();
                    }else{
                        recordUser(email, pass);
                    }
                }
            }
        });
        btnRSPBackSeeRUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeRUT(true);
            }
        });
    }

    private void connect(){
        initializeFireBase();

        etRSPUser = findViewById(R.id.etRSPUser);
        etRSPPassword = findViewById(R.id.etRSPPassword);
        etRSPIdentificationCard = findViewById(R.id.etRSPIdentificationCard);
        etRSPEmail = findViewById(R.id.etRSPEmail);
        etRSPFullName = findViewById(R.id.etRSPFullName);
        etRSPCellphone = findViewById(R.id.etRSPCellphone);
        btnRSPAddRUT = findViewById(R.id.btnRSPAddRUT);
        btnRSPSeeRUT = findViewById(R.id.btnSeeRUT);
        btnRSPRegister = findViewById(R.id.btnRSPRegister);
        btnRSPBackSeeRUT = findViewById(R.id.btnRSPBackSeeRUT);
        ivRSPRUT = findViewById(R.id.ivRSPRUT);

        ivRSPRUT.setVisibility(View.INVISIBLE);
        btnRSPBackSeeRUT.setVisibility(View.INVISIBLE);
        btnRSPSeeRUT.setEnabled(false);
        btnRSPRegister.setEnabled(false );
    }

    private void loadImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent ,GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            assert data != null;
            Uri uri = data.getData();
            path = data.getDataString();
            final StorageReference filePath = mStorageRef.child("picturesServiceProvider").child(uri.getLastPathSegment());
            filePath.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    namePic = filePath.getName();
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downUri = task.getResult();
                        downUriStr = downUri.toString();
                        Toast.makeText(getApplicationContext(), "Se subio exitosamente", Toast.LENGTH_LONG).show();
                        btnRSPSeeRUT.setEnabled(true);
                        btnRSPRegister.setEnabled(true);
                        ivRSPRUT.setImageURI(downUri);
                    }
                }
            });
        }
    }

    private void seeRUT(boolean o){
        if(o){
            etRSPUser.setVisibility(View.VISIBLE);
            etRSPPassword.setVisibility(View.VISIBLE);
            etRSPIdentificationCard.setVisibility(View.VISIBLE);
            etRSPEmail.setVisibility(View.VISIBLE);
            etRSPFullName.setVisibility(View.VISIBLE);
            etRSPCellphone.setVisibility(View.VISIBLE);
            btnRSPRegister.setVisibility(View.VISIBLE);
            btnRSPAddRUT.setVisibility(View.VISIBLE);
            btnRSPSeeRUT.setVisibility(View.VISIBLE);
            ivRSPRUT.setVisibility(View.INVISIBLE);
            btnRSPBackSeeRUT.setVisibility(View.INVISIBLE);
        }else{
            etRSPUser.setVisibility(View.INVISIBLE);
            etRSPPassword.setVisibility(View.INVISIBLE);
            etRSPIdentificationCard.setVisibility(View.INVISIBLE);
            etRSPEmail.setVisibility(View.INVISIBLE);
            etRSPFullName.setVisibility(View.INVISIBLE);
            etRSPCellphone.setVisibility(View.INVISIBLE);
            btnRSPRegister.setVisibility(View.INVISIBLE);
            btnRSPAddRUT.setVisibility(View.INVISIBLE);
            btnRSPSeeRUT.setVisibility(View.INVISIBLE);
            btnRSPBackSeeRUT.setVisibility(View.VISIBLE);
            ivRSPRUT.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Method where the user validate and register in the firebase
     * @param email to register
     * @param password to register
     */
    private void recordUser(String email, String password) {
            progressDialog.setMessage("Realizando registro en linea...");
            progressDialog.show();

            myAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Se registro correctamente", Toast.LENGTH_LONG).show();
                                saveDataOnFireBase();
                                startActivity(new Intent(getApplicationContext(), MainLoginSP.class));
                            }else{
                                Toast.makeText(getApplicationContext(), "No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
    }

    private void initializeFireBase() {
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        databaseReference2 = FirebaseDatabase.getInstance().getReference();
        myAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public NetworkInfo networkInfo(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    private void saveDataOnFireBase(){
        if (networkInfo() != null && networkInfo().isConnected()) {
            UserServiceProvider dffb = new UserServiceProvider(user, identifiedCard, email,
                    fullName, cellphone, false, downUriStr, namePic);  //dffb = dates for firebase

            databaseReference.child("UserServiceProvider").child(dffb.getIdentificationCard()).setValue(dffb);
            Toast.makeText(getApplicationContext(), "Sus datos fueron almacendos correctamente", Toast.LENGTH_LONG).show();
        }else{
            String message = "Ahora no estas conectado a internet, los datos se subiran cuando estes conectado de nuevo.";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    private boolean identifiedCardExist() {
        boolean exist = false;

        databaseReference2.child("UserServiceProvider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String identifiedCard = ds.child("identificationCard").getValue().toString();
                        idetifiedCardExists.add(identifiedCard);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for(int i = 0; i < idetifiedCardExists.size(); i++){
            if(idetifiedCardExists.get(i).equals(identifiedCard)){
                exist = true;
            }
        }
        return exist;
    }

}