package com.example.alumnedam.horari;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HorariActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    String intentGrup, fondo, font;

    /**
     * Metode onCreate on arriban els intents. Un cop arribats en crida a cambiarApariencia i a la consulta que s'ha de realitzar.
     * Aquesta s'anira realitzant amb un thread cada X estona per refrescar l'informacio.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horari);
        //Le llega el grupo de la anterior activity
        intentGrup = getIntent().getStringExtra("grup");
        font = getIntent().getStringExtra("font");
        fondo = getIntent().getStringExtra("fondo");
        cambiarApariencia();
        consulta(intentGrup);
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                consulta(intentGrup);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    /**
     * Metode que li arriba el grup que pertany l'usuari i realitza una SELECT a la BBDD per trobar l'informacio relacionada amb la consulta.
     * Un cop trobat tot s'envia l'informació a asignaTV.
     * @param intentGrup
     */
    public void consulta(String intentGrup) {
        //Variables que usamos
        String grup, codiAsignatura, horaInici, horaFi, diaSetmana, diaSetmanaHorari, profesor, aula;
        //Asignamos el dia la semana actual gracias al metodo diaDeLaSemana
        diaSetmana = diaDeLaSemana();
        //Creamos la BBDD
        SqlActivity sql = new SqlActivity(this, "Eric", null, 1);
        //Creamos el objeto BBDD
        db = sql.getWritableDatabase();
        //String, para la hora del sistema
        String horaDelSistema;
        //Cogemos la hora del sistema y le damos el formato que queremos.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");
        horaDelSistema = formato.format(calendar.getTime());
        //Si la BBDD no esta vacia hace la SELECT
        if (db != null) {
            String[] args = new String[]{horaDelSistema, intentGrup, diaSetmana};
            Cursor c = db.rawQuery("SELECT * FROM tablaHorarios WHERE ? BETWEEN hora_inici AND hora_fi AND grup = ? AND dia_setmana = ?", args);
            if (c.moveToFirst()) {
                do {
                    grup = c.getString(1);
                    codiAsignatura = asignatura(c.getString(2));
                    horaInici = c.getString(3);
                    horaFi = c.getString(4);
                    diaSetmanaHorari = c.getString(5);
                    profesor = profe(c.getString(6));
                    aula = c.getString(7);
                } while (c.moveToNext());
                //Llamamos a asignaTV para todos los valores anteriormente obtenidos colocarlos en TextViews
                asignaTV(grup, codiAsignatura, horaInici, horaFi, diaSetmanaHorari, profesor, aula);
            }
        }
    }

    /**
     * Metodo que le llega por parametro la Id de una asignatura y se busca cual es su nombre.
     * Una vez obtenido el nombre se devuelve el resultado (nombre de la asignatura)
     *
     * @param codiAsignatura
     * @return
     */
    public String asignatura(String codiAsignatura) {
        String nom = "";
        String[] args = new String[]{codiAsignatura};
        Cursor c = db.rawQuery("SELECT Nom FROM tablaAsignatura WHERE ? LIKE Id_Asignatura", args);
        if (c.moveToFirst()) {
            do {
                nom = c.getString(0);
            } while (c.moveToNext());
        }
        return nom;
    }

    /**
     * Metodo que le llega por parametro la Id de un profesor y se busca cual es su nombre.
     * Una vez obtenido el nombre se devuelve el resultado (nombre del profesor)
     *
     * @param profesor
     * @return
     */
    public String profe(String profesor) {
        String nom = "";
        String[] args = new String[]{profesor};
        Cursor c = db.rawQuery("SELECT Nom FROM tablaProfesor WHERE ? LIKE Id_profesor", args);
        if (c.moveToFirst()) {
            do {
                nom = c.getString(0);
            } while (c.moveToNext());
        }
        return nom;
    }

    /**
     * Metodo donde le llegan todos los String anteriores para poder asignar a TextViews los valores.
     *
     * @param grup
     * @param codiAsignatura
     * @param horaInici
     * @param horaFi
     * @param diaSetmanaHorari
     * @param profesor
     * @param aula
     */
    public void asignaTV(String grup, String codiAsignatura, String horaInici, String horaFi, String diaSetmanaHorari, String profesor, String aula) {
        TextView hInici = (TextView) findViewById(R.id.tvHoraInAsignar);
        hInici.setText(horaInici);
        TextView hFi = (TextView) findViewById(R.id.tvHoraFinAsignar);
        hFi.setText(horaFi);
        TextView dSetmana = (TextView) findViewById(R.id.tvDiaAsignar);
        dSetmana.setText(diaSetmanaHorari);
        TextView grupos = (TextView) findViewById(R.id.tvGrupAsignar);
        grupos.setText(grup);
        TextView clase = (TextView) findViewById(R.id.tvClaseAsignar);
        clase.setText(aula);
        TextView asig = (TextView) findViewById(R.id.tvAsignaturaAsignar);
        asig.setText(codiAsignatura);
        TextView profe = (TextView) findViewById(R.id.tvProfesorAsignar);
        profe.setText(profesor);
    }

    /**
     * Metodo que tiene un array de strings, con los diferentes dias de la semana.
     * Coge el dia de hoy y devuelve el dia de hoy en formato de String gracias al array.
     *
     * @return
     */
    public String diaDeLaSemana() {
        String[] diesSetmana = new String[]{"Diumenge", "Dilluns", "Dimarts", "Dimecres", "Dijous", "Divendres", "Dissabte"};
        Calendar cal = Calendar.getInstance();
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        String dia = diesSetmana[dow - 1]; //-5 Ya que es Sabado para probar con el martes.
        return dia;
    }

    /**
     * Metode utilitzat per cambiar l'apariencia de l'activity, tant el fons com la font del text.
     */
    public void cambiarApariencia() {
        TextView profe = (TextView) findViewById(R.id.tvProfesor);
        switch (font) {
            case "Blau":
                findViewById(R.id.activity_horari).setBackgroundColor(Color.BLUE);
                break;
            case "Verd":
                findViewById(R.id.activity_horari).setBackgroundColor(Color.GREEN);
                break;
            case "Groc":
                findViewById(R.id.activity_horari).setBackgroundColor(Color.YELLOW);
                break;
            case "Blanc":
                findViewById(R.id.activity_horari).setBackgroundColor(Color.WHITE);
                break;
        }
        switch (fondo) {
            case "Sant-Serif":
                profe.setTypeface(Typeface.SANS_SERIF);
                break;
            case "Arial":
                profe.setTypeface(Typeface.SERIF);
                break;
            case "Monospace":
                profe.setTypeface(Typeface.MONOSPACE);
                break;
        }
    }
}
