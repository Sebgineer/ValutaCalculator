package dk.zeb.valutacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import dk.zeb.valutacalculator.currency.ApiCurrency;
import dk.zeb.valutacalculator.currency.CurrencyAdapter;
import dk.zeb.valutacalculator.currency.CurrencyListener;
import dk.zeb.valutacalculator.currency.Rate;

public class MainActivity extends AppCompatActivity {

    private ApiCurrency apiCurrency;
    private Spinner baseSpinner;
    private TextInputEditText valueTextInput;
    private double valueMultiplier = 1;

    /***/
    CurrencyListener currencyListener = new CurrencyListener() {
        @Override
        public void onBaseChanges(List<String> bases) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, bases);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            baseSpinner.setAdapter(adapter);
        }

        @Override
        public void onRatesChange(List<Rate> rates) {
            updateValueList(rates);
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

        this.valueTextInput = (TextInputEditText) findViewById(R.id.ValueTextInput);
        this.valueTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    valueMultiplier = Double.parseDouble(charSequence.toString());
                }
                catch (Exception e) {
                    valueMultiplier = 1;
                }
                updateValueList(apiCurrency.getRates());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        this.apiCurrency = new ApiCurrency(Volley.newRequestQueue(this));
        this.apiCurrency.addListener(this.currencyListener);
        this.apiCurrency.updateBases();
    }

    private void updateValueList(List<Rate> rates) {
        ListView listView = findViewById(R.id.CurrencyList);
        List<Rate> list = new ArrayList<Rate>();
        for (Rate r : rates) {
            list.add(new Rate(r.getName(), r.getSpotRate() * this.valueMultiplier));
        }
        CurrencyAdapter adapter = new CurrencyAdapter(MainActivity.this, list);
        listView.setAdapter(adapter);
    }
}