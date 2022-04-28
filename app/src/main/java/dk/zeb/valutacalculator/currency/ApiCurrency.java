package dk.zeb.valutacalculator.currency;

import android.content.Context;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.gson.JsonArray;

//import java.net.HttpURLConnection;
import org.json.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import dk.zeb.valutacalculator.MainActivity;

public class ApiCurrency implements CurrencyDAO {
    private String key = "317d4391da7c6878cf4ee44a";
    private String path = String.format("https://v6.exchangerate-api.com/v6/%s/", key);
    private List<CurrencyListener> listeners = new ArrayList<CurrencyListener>();
    private List<String> currencyBases = new ArrayList<String>();

    public ApiCurrency() {
    }


    @Override
    public List<Rate> getRates(String base) {
        System.out.println("GetRate");
        try {

            String url = path + "latest/DKK";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("Next: ");
                    System.out.println(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Fail");
                }
            }
            );
            MainActivity.queue.add(stringRequest);
            System.out.println("kage");
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

     public void updateBases() {
        String url = path + "latest/DKK";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    currencyBases.clear();
                    JSONObject array = response.getJSONObject("conversion_rates");
                    Iterator<String> keys = array.keys();
                    while(keys.hasNext()) {
                        String key = keys.next();
                        currencyBases.add(key);
                    }
                    notifyAllObserverBase();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MainActivity.queue.add(jsonObjectRequest);
    }

    public void notifyAllObserverBase() {
        for (CurrencyListener l : listeners) {
            l.onBaseChanges(this.currencyBases);
        }
    }

    public void addListener(CurrencyListener listener) {
        listeners.add(listener);
    }

    public void removeListener(CurrencyListener listener) {
        listeners.remove(listener);
    }
}
