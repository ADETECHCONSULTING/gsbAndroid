package fr.yamishadow.gsbandroid.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JEAN on 09/03/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "gsb.db";
    public static final String TABLE_NAME = "lignefraisforfait";
    public static final String COL_ROWID = "id";
    public static final String COL_ID = "idvisiteur";
    public static final String COL_MOIS = "mois";
    public static final String COL_IDFRAISFORFAIT = "idfraisforfait";
    public static final String COL_QTE = "quantite";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Crée une base de donnée dans le telephone
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + TABLE_NAME + " (rowid INTEGER PRIMARY KEY AUTOINCREMENT,idvisiteur TEXT, mois TEXT, idfraisforfait TEXT, quantite INTEGER)");
    }

    /**
     * Met à jour la base de donnée
     * @param db
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    /**
     * Methode d'insertion des valeurs
     * @param idvisiteur
     * @param mois
     * @param idfraisforfait
     * @param qte
     * @return
     */
    public boolean insertData(String idvisiteur, String mois, String idfraisforfait, Integer qte){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, idvisiteur);
        contentValues.put(COL_MOIS, mois);
        contentValues.put(COL_IDFRAISFORFAIT, idfraisforfait);
        contentValues.put(COL_QTE, qte);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    /**
     * Methode qui recupere l'ensemble des lignes de la table
     * @return
     */
    public Cursor getAllData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME+" where idvisiteur = ?", new String[]{id});
        return result;
    }


    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "idvisiteur = ?",new String[]{id});
    }


}
