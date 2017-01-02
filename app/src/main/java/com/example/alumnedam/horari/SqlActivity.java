package com.example.alumnedam.horari;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ALUMNEDAM on 01/12/2016.
 */

public class SqlActivity extends SQLiteOpenHelper {
    //Sentencias para crear las bases de datos
    String tablaHorarios = "CREATE TABLE tablaHorarios(Id_Horario INTEGER PRIMARY KEY, grup TEXT, codi_asignatura INTEGER, hora_inici TEXT, hora_fi TEXT, dia_setmana TEXT, profesor INTEGER, aula TEXT)";
    String tablaProfesor = "CREATE TABLE tablaProfesor(Id_profesor INTEGER PRIMARY KEY, Nom TEXT)";
    String tablaAsignatura = "CREATE TABLE tablaAsignatura(Id_Asignatura INTEGER PRIMARY KEY, Nom TEXT, codi_asignatura TEXT, Profesor INTEGER)";

    public SqlActivity(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Metodo que crea las tablas en la BBDD y inserta los valores
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Ejecutamos la creacion de las tablas
        db.execSQL(tablaHorarios);
        db.execSQL(tablaProfesor);
        db.execSQL(tablaAsignatura);
        //Insertamos las filas

        db.execSQL("INSERT INTO tablaProfesor VALUES(1, 'Josefa')");
        db.execSQL("INSERT INTO tablaProfesor VALUES(2, 'Jose Antonio Leo')");
        db.execSQL("INSERT INTO tablaProfesor VALUES(3, 'Jorge')");
        db.execSQL("INSERT INTO tablaProfesor VALUES(4, 'LLuis')");
        db.execSQL("INSERT INTO tablaProfesor VALUES(5, 'Marta')");

        db.execSQL("INSERT INTO tablaAsignatura VALUES(1, 'Programació Orientada a Objectes', 'M03', 1)");
        db.execSQL("INSERT INTO tablaAsignatura VALUES(2, 'Desenvolupament de interficies', 'M07', 2)");
        db.execSQL("INSERT INTO tablaAsignatura VALUES(3, 'Programacio multimedia i dispositius mobils', 'M08', 4)");
        db.execSQL("INSERT INTO tablaAsignatura VALUES(4, 'Programacio de serveis i processos', 'M09', 3)");
        db.execSQL("INSERT INTO tablaAsignatura VALUES(5, 'Sistemes de gestió empresarial', 'M10', 5)");
        db.execSQL("INSERT INTO tablaAsignatura VALUES(6, 'Pupurri de cosas con Jorge', 'M05/02/06', 3)");

        db.execSQL("INSERT INTO tablaHorarios VALUES (18, 'A1', '2', '00:00:00', '14:59:59', 'Divendres', 2, '208') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (19,'A1', '4', '00:00:00', '14:59:59', 'Dilluns', 3, '208') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (1,'A1', '2', '15:00:00', '18:59:59', 'Dilluns', 2, '208') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (2,'A1', '1', '15:00:00', '17:59:59', 'Dimarts', '1', '208') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (3,'A1', '5', '17:00:00', '18:59:59', 'Dimarts', '5', '201') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (4,'Pati', 'Pati', '18:00:00', '18:19:59', 'Dimarts', null, 'Pati') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (5,'A1', '5', '18:20:00', '19:19:59', 'Dimarts', '5', '201') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (6,'A1', '6', '19:20:00', '21:19:59', 'Dimarts', '6', '201') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (7,'A1', '6', '16:00:00', '17:59:59', 'Dimecres', '6', '201') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (8,'A1', '4', '17:00:00', '18:59:59', 'Dimecres', '3', '201') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (9,'Pati', 'Pati', '18:00:00', '18:19:59', 'Dimecres', null, 'Pati') ");









    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
