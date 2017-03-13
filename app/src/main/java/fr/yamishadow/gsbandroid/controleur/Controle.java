package fr.yamishadow.gsbandroid.controleur;

import android.content.Context;

import fr.yamishadow.gsbandroid.modele.DatabaseHelper;
import fr.yamishadow.gsbandroid.modele.Utilisateur;

/**
 * Created by JEAN on 05/03/2017.
 */

public class Controle {
    private static Context context;
    private Utilisateur user;
    private static Controle instance;
    private DatabaseHelper myDB;

    public Controle(Context context){
        super();
        user = new Utilisateur();
        myDB = new DatabaseHelper(context);
    }

    public final static Controle getInstance(Context context){
        if(Controle.instance == null){
            Controle.instance = new Controle(context);
            Controle.context = context;
        }
        return Controle.instance;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public DatabaseHelper getMyDB() {
        return myDB;
    }

    public void setMyDB(DatabaseHelper myDB) {
        this.myDB = myDB;
    }
}
