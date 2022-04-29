package dk.zeb.valutacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.util.ArrayList;
import java.util.List;

import dk.zeb.valutacalculator.currency.ApiCurrency;
import dk.zeb.valutacalculator.currency.CurrencyListener;
import dk.zeb.valutacalculator.currency.Rate;

public class MainActivity extends AppCompatActivity {

    private ApiCurrency apiCurrency;
    private Spinner baseSpinner;

    /***/
    CurrencyListener currencyListener = new CurrencyListener() {
        @Override
        public void onBaseChanges(List<String> bases) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, bases);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            baseSpinner.setAdapter(adapter);
        }

        @Override
        public void onRatesChange(List<Rate> rates) {
            ListView listView = findViewById(R.id.CurrencyList);
            List<String> list = new ArrayList<String>();
            for (Rate r : rates) {
                list.add(r.name + ": " + r.spotRate);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
            System.out.println(rates);
        }
    };

    /***/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.baseSpinner = (Spinner) findViewById(R.id.CurrencySpinner);
        this.baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItemText = (String) adapterView.getItemAtPosition(i);
                apiCurrency.updateRates(selectedItemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.apiCurrency = new ApiCurrency(Volley.newRequestQueue(this));
        this.apiCurrency.addListener(this.currencyListener);
        this.apiCurrency.updateBases();
    }
}