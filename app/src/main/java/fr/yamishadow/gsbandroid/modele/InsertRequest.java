package fr.yamishadow.gsbandroid.modele;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JEAN on 27/01/2017.
 */

public class InsertRequest extends StringRequest {
    private static final String SERVADR = "http://siolms.pro:83/atraore/PPE/Android/insert.php";
    private Map<String,String> users;


    public InsertRequest(String id, String mois, String idfraisforfait, String quantite, Response.Listener<String> listener) {
        super(Method.POST, SERVADR, listener, null);
        users = new HashMap<>();
        users.put("id", id);
        users.put("mois", mois);
        users.put("idfraisforfait", idfraisforfait);
        users.put("quantite", quantite);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return users;
    }
}
