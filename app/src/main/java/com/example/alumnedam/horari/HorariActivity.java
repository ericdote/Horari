package com.example.alumnedam.horari;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HorariActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horari);
        //Le llega el grupo de la anterior activity
        String intentGrup = getIntent().getStringExtra("grup");
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
            Cursor c = db.rawQuery("SELECT * FROM tablaHorarios WHERE ? BETWEEN hora_inici AND hora_fi AND grup = ? AND dia_setmana = ?", args); //TODO Falla aqui bestialmente
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
     * @return
     */
    public String diaDeLaSemana(){
        String[] diesSetmana = new String[]{"Diumenge", "Dilluns", "Dimarts", "Dimecres", "Dijous", "Divendres", "Dissabte"};
        Calendar cal = Calendar.getInstance();
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        String dia = diesSetmana[dow-1];
        Toast.makeText(this, ""+dia, Toast.LENGTH_SHORT).show();
        return dia;
    }
}
