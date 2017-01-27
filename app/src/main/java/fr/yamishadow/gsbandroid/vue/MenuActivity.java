package fr.yamishadow.gsbandroid.vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.util.Hashtable;

import fr.yamishadow.gsbandroid.R;
import fr.yamishadow.gsbandroid.controleur.Global;
import fr.yamishadow.gsbandroid.modele.AccesDistant;
import fr.yamishadow.gsbandroid.modele.FraisMois;
import fr.yamishadow.gsbandroid.outils.Serializer;

public class MenuActivity extends AppCompatActivity {

    private AccesDistant accesDistant;
    private Button btnFleche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // récupération des informations sérialisées
        recupSerialize() ;
        // chargement des méthodes événementielles
        btnFleche = (Button) findViewById(R.id.cmdTransfert);
        cmdMenu_clic(((Button)findViewById(R.id.cmdKm)), KmActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdHf)), HfActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdRepas)), RepasActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdNuitee)), NuitActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdEtape)), EtapeActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdHfRecap)), HfRecapActivity.class) ;
        cmdTransfert_clic() ;

        btnFleche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}
