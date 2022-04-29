package dk.zeb.valutacalculator.currency;


import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.*;

import java.util.Iterator;

import dk.zeb.valutacalculator.MainActivity;

/**  */
public class ApiRequester {
    /** key is the authorization to use the api*/
    private String key = "317d4391da7c6878cf4ee44a";
    /** path the base url for api call */
    private String path = String.format("https://v6.exchangerate-api.com/v6/%s/", key);
    /** queue to request a http call*/
    private RequestQueue queue;

    /**  */
    public ApiRequester(RequestQueue queue) {
        this.queue = queue;
    }


    /*
    public void requestString(String urlParam, Response.Listener<String> listener) {
        StringRequest stringRequest = new StringRequest(path + urlParam, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //need code
            }
        });
    }*/

    /** Request a json object
     * @param urlParam
     * @param listener */
    public void requestJSONObject(String urlParam, Response.Listener<JSONObject> listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(path + urlParam, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //need code
            }
        });
        queue.add(jsonObjectRequest);
    }
}
