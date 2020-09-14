package com.example.michele.votazione;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.michele.votazione.connessione.SingInOrganizzatoreAsync;
import com.example.michele.votazione.entity.Organizzatore;
import com.google.gson.Gson;
import java.util.regex.Pattern;

public class RegistrazioneOrganizzatore extends AppCompatActivity {
    private EditText usernameEdit, passwordEdit;
    private Button singin;
    private String username, password;
    private TextView accedi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione_organizzatore);
        findViewById();
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEdit.getText().toString();
                password = passwordEdit.getText().toString();
                if(username.equals(""))
                    Toast.makeText(RegistrazioneOrganizzatore.this, "Username non può essere nullo", Toast.LENGTH_SHORT).show();
                else if(isValid(password)) {
                    Organizzatore organizzatore=new Organizzatore(username,password);
                    String singInOrganizzatore = new Gson().toJson(organizzatore, Organizzatore.class);
                    new SingInOrganizzatoreAsync(RegistrazioneOrganizzatore.this).execute(singInOrganizzatore);
                }else{
                    Toast.makeText(RegistrazioneOrganizzatore.this, "Password invalida riprova", Toast.LENGTH_SHORT).show();
                    passwordEdit.setText("");
                }
            }
        });
        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed ();
            }
        });

    }

    private void findViewById(){
        usernameEdit = (EditText) findViewById(R.id.username);
        passwordEdit = (EditText) findViewById(R.id.password);
        singin = (Button) findViewById(R.id.button2);
        accedi= (TextView) findViewById(R.id.login);
    }

    public static boolean isValid(String password) {
        Pattern maiuscolaPatten = Pattern.compile("[A-Z ]");
        Pattern minuscolaPatten = Pattern.compile("[a-z ]");
        Pattern numeroPatten = Pattern.compile("[0-9 ]");
        boolean flag=true;
        if (password.length() < 8) {
            flag=false;
        }
        if (!maiuscolaPatten.matcher(password).find()) {
            flag=false;
        }
        if (!minuscolaPatten.matcher(password).find()) {
            flag=false;
        }
        if (!numeroPatten.matcher(password).find()) {
            flag=false;
        }
        return flag;
    }

}