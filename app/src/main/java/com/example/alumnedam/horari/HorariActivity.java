package com.example.alumnedam.horari;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HorariActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horari);
        String intentGrup = getIntent().getStringExtra("grup");
        String idHorari, grup, codiAsignatura, horaInici, horaFi, diaSetmana, profesor, aula;
        //Creamos la BBDD
        SqlActivity sql = new SqlActivity(this, "Eric", null, 1);
        //Creamos el objeto BBDD
        db = sql.getWritableDatabase();
        //2 Strings, uno para la hora del sistema y otro para el grupo que escoja el usuario
        String horaDelSistema;
        //Cogemos la hora del sistema y le damos el formato que queremos.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");
        horaDelSistema = formato.format(calendar.getTime());
        //Si la BBDD no esta vacia hace la SELECT
        if(db != null){
            String[] args = new String[] {horaDelSistema};
            Cursor c = db.rawQuery("SELECT * FROM tablaHorarios WHERE ? BETWEEN hora_inici AND hora_fi", args);
            if(c.moveToFirst()){
                do{
                    idHorari = c.getString(0);
                    grup = c.getString(1);
                    codiAsignatura = asignatura(c.getString(2));
                    horaInici = c.getString(3);
                    horaFi = c.getString(4);
                    diaSetmana = c.getString(5);
                    profesor = profe(c.getString(6));
                    aula = c.getString(7);
                }while(c.moveToNext());
                //Llamamos a asignaTV para todos los valores anteriormente obtenidos colocarlos en TextViews
                asignaTV(grup, codiAsignatura, horaInici, horaFi, diaSetmana, profesor, aula);
            }
        }
    }

    /**
     * Metodo que le llega por parametro la Id de una asignatura y se busca cual es su nombre.
     * Una vez obtenido el nombre se devuelve el resultado (nombre de la asignatura)
     * @param codiAsignatura
     * @return
     */
    public String asignatura (String codiAsignatura){
        String nom="";
        String[] args = new String[] {codiAsignatura};
        Cursor c = db.rawQuery("SELECT Nom FROM tablaAsignatura WHERE ? LIKE Id_Asignatura", args);
        if (c.moveToFirst()){
            do{
                nom = c.getString(0);
            } while(c.moveToNext());
        }
        return nom;
    }

    /**
     * Metodo que le llega por parametro la Id de un profesor y se busca cual es su nombre.
     * Una vez obtenido el nombre se devuelve el resultado (nombre del profesor)
     * @param profesor
     * @return
     */
    public String profe (String profesor){
        String nom ="";
        String[] args = new String[] {profesor};
        Cursor c = db.rawQuery("SELECT Nom FROM tablaProfesor WHERE ? LIKE Id_profesor", args);
        if (c.moveToFirst()){
            do{
                nom = c.getString(0);
            } while(c.moveToNext());
        }
        return nom;
    }

    /**
     * Metodo donde le llegan todos los String anteriores para poder asignar a TextViews los valores.
     * @param grup
     * @param codiAsignatura
     * @param horaInici
     * @param horaFi
     * @param diaSetmana
     * @param profesor
     * @param aula
     */
    public void asignaTV(String grup,String codiAsignatura,String horaInici,String horaFi,String diaSetmana,String profesor,String aula){
        TextView hInici = (TextView)findViewById(R.id.tvHoraInAsignar);
        hInici.setText(horaInici);
        TextView hFi = (TextView)findViewById(R.id.tvHoraFinAsignar);
        hFi.setText(horaFi);
        TextView dSetmana = (TextView)findViewById(R.id.tvDiaAsignar);
        dSetmana.setText(diaSetmana);
        TextView grupos = (TextView)findViewById(R.id.tvGrupAsignar);
        grupos.setText(grup);
        TextView clase = (TextView)findViewById(R.id.tvClaseAsignar);
        clase.setText(aula);
        TextView asig = (TextView)findViewById(R.id.tvAsignaturaAsignar);
        asig.setText(codiAsignatura);
        TextView profe = (TextView)findViewById(R.id.tvProfesorAsignar);
        profe.setText(profesor);
    }
}
