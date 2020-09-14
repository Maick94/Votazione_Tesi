package com.example.michele.votazione.connessione;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.michele.votazione.HomeOrganizzatore;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Michele on 22/03/2020.
 */

public class LoginOrganizzatoreAsync extends AsyncTask<String, Void, String> {

    private ProgressDialog loadingDialog;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static String organizzatore;
    private String username;

    public LoginOrganizzatoreAsync(Context context,String username){
        this.context = context;
        this.username =username;
        //Shared
        sharedPreferences = context.getSharedPreferences("sessioneOrganizzatore", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Attendi",null, true, true);
        loadingDialog.setMessage("Connessione in corso");
        loadingDialog.setCancelable(false);
    }


    @Override
    protected String doInBackground(String... params) {
        String result = null;

        if(isNetworkAvailable(context)){
            HttpURLConnection con = null;
            try{
                // Parametri nickname e password passati da execute come parametri
                organizzatore = params[0];


                // Dati nickname e password inoltrati al server
                String data = "?organizzatore=" + URLEncoder.encode(organizzatore, "UTF-8");

                // Link
                String link = "http://databaseandroid.altervista.org/app/Control/LoginOrganizzatoreControl.php" + data;
                Log.d("DEBUG-------:", link);




                // URL
                URL url = new URL(link);

                // HttpURLConnection
                con = (HttpURLConnection) url.openConnection();

                // Timeout per la connessione
                int timeOutInMillis = 3000;
                con.setConnectTimeout(timeOutInMillis);

                // Connection
                con.connect();

                // Timeout nel ricerever informazioni
                int socketTimeOutinMillis = 3000;
                con.setReadTimeout(socketTimeOutinMillis);

                // InputStream risultato server
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                if(con.getHeaderField("Location") == null)
                    result = bufferedReader.readLine();
                bufferedReader.close();

                return result;
            }  catch (SocketTimeoutException e) {
                return null;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        loadingDialog.dismiss();

        if (result != null) {
            // Bool di ritorno da server (true) --> Utente esistente; (false) --> Utente non trovato;
            Boolean bool = new Gson().fromJson(result, Boolean.class);

            if(bool){
                Toast.makeText(context, "Accesso eseugito correttamente. ", Toast.LENGTH_SHORT).show();




                // Sessione inserisce username
                editor.putString("username", username);
                editor.commit();



                Intent intent = new Intent(context, HomeOrganizzatore.class);
                intent.putExtra("Numero", (Serializable) 0);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Errore: Problema riscontrato durante la procedura di autenticazione. Controllare credenziali o collegamento a internet.", Toast.LENGTH_SHORT).show();
            }
        } /*else {   //vedere in caso di internet assente dove mandare l'app
            String msg = "Connessione lenta o non funzionante";
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle("Timeout connessione");
            builder.setMessage(msg);

            builder.setPositiveButton("Riprova", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                    new LoginOrganizzatoreAsync(context,username).execute(organizzatore);
                }
            });

            builder.setNegativeButton("Esci", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }*/
    }

    @Override
    public void onCancelled(){
        super.onCancelled();
        cancel(true);
    }

    private static boolean isNetworkAvailable(Context context){
        return  ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }


}