package fr.yamishadow.gsbandroid.modele;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JEAN on 27/01/2017.
 */

public class LoginRequest extends StringRequest {
    private static final String SERVADR = "http://siolms.pro:83/atraore/PPE/Android/login.php";
    private Map<String,String> users;

    /**
     * Constructeur de la requete Login
     * @param login
     * @param mdp
     * @param listener
     */
    public LoginRequest(String login, String mdp, Response.Listener<String> listener) {
        super(Method.POST, SERVADR, listener, null);
        users = new HashMap<>();
        users.put("login", login);
        users.put("mdp", mdp);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return users;
    }
}
