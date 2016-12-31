package com.example.alumnedam.horari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btnHorari);
        btn.setOnClickListener(this);

    }

    public void guardarPreferences(TextView tvNom, Spinner spin){
        SharedPreferences prefs = getSharedPreferences("PreferenciasHorari", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nom", tvNom.getText().toString());
        editor.putString("grup", spin.getSelectedItem().toString());
        editor.commit();
    }



    @Override
    public void onClick(View view) {
        TextView tvNom = (TextView) findViewById(R.id.etNom);
        Spinner spin = (Spinner) findViewById(R.id.spinnerCurs);
        Intent intent = new Intent(this, HorariActivity.class);
        if (!(tvNom.getText().toString().equals(""))) {
            guardarPreferences(tvNom, spin);
            intent.putExtra("grup", spin.getSelectedItem().toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Introdueix els valors", Toast.LENGTH_SHORT).show();
        }
    }
}
