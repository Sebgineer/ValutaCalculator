package dk.zeb.valutacalculator.currency;

import com.android.volley.*;

import org.json.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    public List<Rate> getRates() {
        return this.rates;
    }

    public void updateRates(String base) {
        requester.requestJSONObject("latest/" + base, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                rates.clear();
                try {
                    JSONObject object = response.getJSONObject("conversion_rates");
                    Iterator<String> keys = object.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        Rate rate = new Rate(key, Double.parseDouble(object.get(key).toString()));
                        rates.add(rate);
                    }
                    notifyObserverOnRatesChange();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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

    private void notifyObserverOnRatesChange() {
         for (CurrencyListener l : this.listeners) {
            l.onRatesChange(this.rates);
         }
    }

    /** To notify the observers when currency base has changed*/
    private void notifyObserverOnBaseChange() {
        for (CurrencyListener l : this.listeners) {
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
