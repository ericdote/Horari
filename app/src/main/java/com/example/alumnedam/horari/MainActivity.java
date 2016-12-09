package com.example.alumnedam.horari;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            String[] args = new String[] {horaDelSistema, grupo};
            Cursor c = db.rawQuery(" SELECT * FROM tablaHorarios WHERE ? BETWEEN horaInici AND horaFi AND grup = ?", args);
            if(c.moveToFirst()){
                do{
                    //TODO String http://www.sgoliver.net/blog/bases-de-datos-en-android-iii-consultarrecuperar-registros/
                }while(c.moveToNext());
            }
        }

    }
}
