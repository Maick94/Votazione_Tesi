package com.example.michele.votazione.connessione;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.michele.votazione.GestioneGiuria;
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
 * Created by Michele on 29/03/2020.
 */

public class InserisciGiuriaAsync extends AsyncTask<String, Void, String> {

    private ProgressDialog loadingDialog;
    private Context context;
    private String id;
    private static String concorso;

    public InserisciGiuriaAsync(Context context, String id){
        this.context = context;
        this.id=id;
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
                // Parametri  passati da execute come parametri
                concorso = params[0];


                // Dati inoltrati al server
                String data = "?concorso=" + URLEncoder.encode(concorso, "UTF-8");

                // Link
                String link ="http://databaseandroid.altervista.org/app/Control/InserisciGiuriaControl.php" + data;
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
            Boolean bool = new Gson().fromJson(result, Boolean.class);
            if(bool){
                Toast.makeText(context, "Giuria registrata correttamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, GestioneGiuria.class);
                intent.putExtra("Id", (Serializable) id);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Errore: Giuria non registrato riprovare", Toast.LENGTH_SHORT).show();
            }
        }/*else {       //vedere in caso di internet assente dove mandare l'app
            String msg = "Connessione lenta o non funzionante";
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle("Timeout connessione");
            builder.setMessage(msg);

            builder.setPositiveButton("Riprova", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new SingInOrganizzatoreAsync(context).execute(organizzatore);
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
