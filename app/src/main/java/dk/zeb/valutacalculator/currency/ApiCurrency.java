package dk.zeb.valutacalculator.currency;

import android.content.Context;
import android.widget.ListView;

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
    private List<CurrencyListener> listeners;
    private List<String> currencyBases;
    private List<Rate> rates;
    private ApiRequester requester;


    /***/
    public ApiCurrency(RequestQueue queue) {
        this.listeners = new ArrayList<CurrencyListener>();
        this.currencyBases = new ArrayList<String>();
        this.rates = new ArrayList<Rate>();
        this.requester = new ApiRequester(queue);
    }

    /**
     * */
    @Override
    public List<Rate> getRates(String base) {
        requester.requestJSONObject("latest/" + base, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        });
        return null;
    }

    /** Update the base currency and notify all the observer*/
     public void updateBases() {
        requester.requestJSONObject("latest/DKK", new Response.Listener<JSONObject>() {
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
                    notifyObserverOnBaseChange();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /** To notify the observers when currency base has changed*/
    private void notifyObserverOnBaseChange() {
        for (CurrencyListener l : listeners) {
            l.onBaseChanges(this.currencyBases);
        }
    }

    /** Add observer to observable.
     * @param listener adds as observer*/
    public void addListener(CurrencyListener listener) {
        listeners.add(listener);
    }

    /** Remove observer from observable
     * @param listener removes as a observer*/
    public void removeListener(CurrencyListener listener) {
        listeners.remove(listener);
    }
}
