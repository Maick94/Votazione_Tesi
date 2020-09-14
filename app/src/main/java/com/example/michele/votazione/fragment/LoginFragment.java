package com.example.michele.votazione.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.michele.votazione.R;
import com.example.michele.votazione.RegistrazioneOrganizzatore;
import com.example.michele.votazione.connessione.LoginOrganizzatoreAsync;
import com.example.michele.votazione.entity.Organizzatore;
import com.google.gson.Gson;

/**
 * Created by Michele on 03/03/2020.
 */

public class LoginFragment extends android.support.v4.app.Fragment {
    private static View view;
    private Context context;
    private EditText usernameEdit, passwordEdit;
    private Button login;
    private String username, password;
    private TextView registrazione;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        context = getActivity();
        findViewById();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEdit.getText().toString();
                password = passwordEdit.getText().toString();
                Organizzatore organizzatore = new Organizzatore(username, password);
                String loginOrganizzatore = new Gson().toJson(organizzatore, Organizzatore.class);
                new LoginOrganizzatoreAsync(context, username).execute(loginOrganizzatore);
            }
        });
        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegistrazioneOrganizzatore.class);
                context.startActivity(intent);
            }
        });
        return view;
    }

    private void findViewById(){
        usernameEdit = (EditText) view.findViewById(R.id.username);
        passwordEdit = (EditText) view.findViewById(R.id.password);
        login = (Button) view.findViewById(R.id.button1);
        registrazione= (TextView) view.findViewById(R.id.signIn);
    }


}