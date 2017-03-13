package fr.yamishadow.gsbandroid.vue;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Hashtable;

import fr.yamishadow.gsbandroid.R;
import fr.yamishadow.gsbandroid.controleur.Controle;
import fr.yamishadow.gsbandroid.controleur.Global;
import fr.yamishadow.gsbandroid.modele.DatabaseHelper;
import fr.yamishadow.gsbandroid.modele.FraisMois;
import fr.yamishadow.gsbandroid.modele.InsertRequest;
import fr.yamishadow.gsbandroid.modele.Utilisateur;
import fr.yamishadow.gsbandroid.outils.Serializer;

public class MenuActivity extends AppCompatActivity {
    private String TAG = "MenuActivity";
    private Controle controle;
    private Button btnPush;
    private Boolean success;
    private DatabaseHelper myDB;
    private Utilisateur user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // récupération des informations sérialisées
        recupSerialize() ;
        //chargement des méthodes événementielles
        controle = Controle.getInstance(this);
        user = controle.getUser();
        myDB = controle.getMyDB();
        setTitle("GSBAndroid - "+ user.getNom()+" "+user.getPrenom());
        btnPush = (Button) findViewById(R.id.cmdTransfert);
        cmdMenu_clic(((Button)findViewById(R.id.cmdKm)), KmActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdHf)), HfActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdRepas)), RepasActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdNuitee)), NuitActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdEtape)), EtapeActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdHfRecap)), HfRecapActivity.class) ;
        cmdTransfert_clic() ;
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor resultat = myDB.getAllData(controle.getUser().getId());
                while (resultat.moveToNext()) {
                    dataSender(resultat.getString(1),resultat.getString(2),resultat.getString(3),resultat.getString(4));
                }
                myDB.deleteData(controle.getUser().getId());
                resultat.close();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * Récupére la sérialisation si elle existe
     */
    private void recupSerialize() {
    	Global.listFraisMois = (Hashtable<Integer, FraisMois>) Serializer.deSerialize(Global.filename, MenuActivity.this) ;
    	// si rien n'a été récupéré, il faut créer la liste
    	if (Global.listFraisMois==null) {
    		Global.listFraisMois = new Hashtable<Integer, FraisMois>() ;
    	}
    }

    /**
     * Sur la sélection d'un bouton dans l'activité principale ouverture de l'activité correspondante
     */
    private void cmdMenu_clic(Button button, final Class classe) {
    	button.setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			// ouvre l'activité
    			Intent intent = new Intent(MenuActivity.this, classe) ;
    			startActivity(intent) ;  			
    		}
    	}) ;
    }
    
    /**
     * Cas particulier du bouton pour le transfert d'informations vers le serveur
     */
    private void cmdTransfert_clic() {
    	((Button)findViewById(R.id.cmdTransfert)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			// envoi les informations sérialisées vers le serveur
    			// en construction
    		}
    	}) ;    	
    }

    /**
     * Methode qui gère l'envoie des données à la classe InsertRequest (cette derniere enverra les données au fichier pdf)
     * @param id
     * @param mois
     * @param idfraisforfait
     * @param qte
     */
    public void dataSender(String id, String mois, String idfraisforfait, String qte){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    String[] responseInsert = response.split("%");
                    JSONObject jsonObject = new JSONObject(responseInsert[1]);
                    Log.d(TAG, "Insertion en cours..." );
                    success = jsonObject.getBoolean("success");
                    if(success){
                        Toast.makeText(MenuActivity.this, "Vos données ont bien été ajoutée dans la base de donnée", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MenuActivity.this, "Les données n'ont pas pu être envoyées. Peut être aviez vous déjà envoyé ces données sur la base ?", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        InsertRequest insertRequest = new InsertRequest(id,mois,idfraisforfait,qte, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(MenuActivity.this);
        requestQueue.add(insertRequest);
    }
}
