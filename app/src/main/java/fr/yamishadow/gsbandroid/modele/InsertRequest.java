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
    private static final String SERVADR = "http://192.168.0.12/GsbAndroid/insert.php";
    private Map<String,String> users;

    /**
     * Constructeur de la requete Login
     * @param email
     * @param mdp
     * @param listener
     */
    public InsertRequest(String email, String mdp, Response.Listener<String> listener) {
        super(Method.POST, SERVADR, listener, null);
        users = new HashMap<>();
        users.put("email", email);
        users.put("mdp", mdp);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return users;
    }
}
