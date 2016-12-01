package com.example.alumnedam.horari;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ALUMNEDAM on 01/12/2016.
 */

public class SqlActivity extends SQLiteOpenHelper {
    //Sentencias para crear las bases de datos
    String tablaHorarios = "CREATE TABLE Horarios(Id_Horario INTEGER PRIMARY KEY, grup TEXT, codi_asignatura INTEGER, hora_inici TEXT, hora_fi TEXT, dia_setmana TEXT, profesor INTEGER, aula TEXT)";
    String tablaProfesor = "CREATE TABLE Profesor(Id_profesor INTEGER PRIMARY KEY, Nom TEXT, codi_asignatura INTEGER)";
    String tablaAsignatura = "CREATE TABLE Asignatura(Id_Asignatura INTEGER PRIMARY KEY, Nom TEXT, codi_asignatura TEXT)";

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

        db.execSQL("INSERT INTO Asignatura(1, Programació Orientada a Objectes, M03");

        db.execSQL("INSERT INTO tablaHorarios VALUES (1,'Josefa', 'M07', '15:00', '18:00', 'Dilluns', 100, '208') ");


        db.execSQL("INSERT INTO tablaHorarios VALUES (1,'A1', 'M07', '15:00', '18:00', 'Dilluns', 100, '208') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (2,'A1', 'M03', '15:00', '17:00', 'Dimarts', 'Josefa', '208') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (3,'A1', 'M10', '17:00', '18:00', 'Dimarts', 'Marta', '201') ");
        db.execSQL("INSERT INTO tablaHorarios VALUES (4,'Pati', 'Pati', '18:00', '18:20', 'Dimarts', 'Marta', 'Pati') ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
