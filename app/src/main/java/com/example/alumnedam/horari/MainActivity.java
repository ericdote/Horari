package com.example.alumnedam.horari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;

    /**
     * Metode onCreate on es crea les SharedPreferences i es mira si aquestes habien sigut creades abans o no per saltar el MainActivity o no.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences("PreferenciasHorari", 0);
        if(settings.getBoolean("guardado", false) != false){
            String grup = settings.getString("grup", "A1");
            String fondo = settings.getString("fondo", "Blanc");
            String font = settings.getString("font", "Serif");
            saltarMain(grup, fondo, font);
        }
        Button btn = (Button) findViewById(R.id.btnHorari);
        btn.setOnClickListener(this);
    }

    /**
     * En cas de que s'accedeixi per primer cop a les sharedPreferences o s'estigui modificant aquestes, es guardara tot al fitxer de les Shared.
     * @param tvNom
     * @param spin
     * @param fondo
     * @param font
     */
    public void guardarPreferences(TextView tvNom, Spinner spin, Spinner fondo, Spinner font){
        SharedPreferences prefs = getSharedPreferences("PreferenciasHorari", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nom", tvNom.getText().toString());
        editor.putString("grup", spin.getSelectedItem().toString());
        editor.putString("fondo", fondo.getSelectedItem().toString());
        editor.putString("font", font.getSelectedItem().toString());
        editor.putBoolean("guardado", true);
        editor.commit();
    }

    /**
     * Metode que li arriba per parametre els valors necessaris per poder inicialitzar l'horari.
     * @param grup
     * @param fondo
     * @param font
     */
    public void saltarMain(String grup, String fondo, String font){
        intent = new Intent(this, HorariActivity.class);
        intent.putExtra("grup", grup);
        intent.putExtra("fondo", fondo);
        intent.putExtra("font", font);
        startActivity(intent);
    }


    /**
     * Metode onClick, on en cas de presionar el botó d'anar al horari es mira si tots els valors estan complets.
     * En cas correcte es guarda tot i s'envia un intent a la proxima activity.
     * @param view
     */
    @Override
    public void onClick(View view) {
        intent = new Intent(this, HorariActivity.class);
        TextView tvNom = (TextView) findViewById(R.id.etNom);
        Spinner spin = (Spinner) findViewById(R.id.spinnerCurs);
        Spinner fondo = (Spinner)findViewById(R.id.spinnerFons);
        Spinner font = (Spinner)findViewById(R.id.spinnerfont);
        if (!(tvNom.getText().toString().equals(""))) {
            guardarPreferences(tvNom, spin, fondo, font);
            intent.putExtra("grup", spin.getSelectedItem().toString());
            intent.putExtra("fondo", fondo.getSelectedItem().toString());
            intent.putExtra("font", font.getSelectedItem().toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Introdueix els valors", Toast.LENGTH_SHORT).show();
        }
    }
}
