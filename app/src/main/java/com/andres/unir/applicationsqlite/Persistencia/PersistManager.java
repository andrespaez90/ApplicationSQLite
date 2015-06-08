package com.andres.unir.applicationsqlite.Persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.andres.unir.applicationsqlite.Modelo.Persona;

import java.util.Vector;

/**
 * Created by INNSO SAS on 07/05/2015.
 */
public class PersistManager extends SQLiteOpenHelper {

    public PersistManager(Context context) {
        super(context, "DataBase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Person (name TEXT, DNI TEXT, PRIMARY KEY(DNI) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String Save(Persona p) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("INSERT INTO Person VALUES ('" + p.getNombre() + "'," + "'" + p.getDNI() + "')");
            return "ok";
        } catch (SQLiteConstraintException ex) {
            return "id";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String Delete(String id){
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM Person WHERE DNI ='"+id+"';");
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String Edit(String id, String name) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("UPDATE  Person SET name = '"+name+"' WHERE DNI ='"+id+"';");
            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Vector<Persona> getsaved() {
        Vector<Persona> result = new Vector<Persona>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursosr = db.rawQuery("SELECT * FROM Person", null);
        while(cursosr.moveToNext())
        {
            Persona u = new Persona(cursosr.getString(0), cursosr.getString(1));
            result.add(u);
        }

        cursosr.close();
        return result;
    }


}
