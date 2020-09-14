package com.example.michele.votazione;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.michele.votazione.connessione.LoginGiudiceAsync;
import com.example.michele.votazione.connessione.LoginOrganizzatoreAsync;
import com.example.michele.votazione.entity.Giudice;
import com.example.michele.votazione.entity.Organizzatore;
import com.google.gson.Gson;

public class LoginGiuria extends AppCompatActivity {

    private String idConcorso;
    private String imei;
    private EditText emailEdit, passwordEdit;
    private Button login;
    private String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_giuria);
        idConcorso= (String) getIntent().getSerializableExtra("IdConcorso");
        imei= (String) getIntent().getSerializableExtra("imei");
        findViewById();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEdit.getText().toString();
                password = passwordEdit.getText().toString();


                Giudice giudice=new Giudice(email,password);
                String loginGiudice = new Gson().toJson(giudice, Giudice.class);
                new LoginGiudiceAsync(LoginGiuria.this,idConcorso,imei).execute(loginGiudice);
            }
        });


    }

    private void findViewById(){
        emailEdit = (EditText) findViewById(R.id.email);
        passwordEdit = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.button1);
    }
}
