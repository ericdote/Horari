package com.example.alumnedam.horari;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String idHorari, grup, codiAsignatura, horaInici, horaFi, diaSetmana, profesor, aula;
        //Creamos la BBDD
        SqlActivity sql = new SqlActivity(this, "Eric", null, 1);
        //Creamos el objeto BBDD
        SQLiteDatabase db = sql.getWritableDatabase();
        //2 Strings, uno para la hora del sistema y otro para el grupo que escoja el usuario
        String horaDelSistema;
        String grupo = "A1";
        //Cogemos la hora del sistema y le damos el formato que queremos.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        horaDelSistema = format.format(calendar.getTime());
        //Si la BBDD no esta vacia hace la SELECT
        if(db != null){
            String[] args = new String[] {horaDelSistema};
            Cursor c = db.rawQuery("SELECT * FROM tablaHorarios WHERE ? BETWEEN hora_inici AND hora_fi", args);
            if(c.moveToFirst()){
                do{
                    idHorari = c.getString(0);
                    grup = c.getString(1);
                    codiAsignatura = c.getString(2);
                    horaInici = c.getString(3);
                    horaFi = c.getString(4);
                    diaSetmana = c.getString(5);
                    profesor = c.getString(6);
                    aula = c.getString(7);
                }while(c.moveToNext());
                Toast.makeText(this, ""+idHorari+" "+grup+" "+codiAsignatura+" "+horaInici+" "+horaFi+" "+diaSetmana+" "+profesor
                        +" "+aula, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
