package dk.zeb.valutacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.util.ArrayList;
import java.util.List;

import dk.zeb.valutacalculator.currency.ApiCurrency;
import dk.zeb.valutacalculator.currency.CurrencyListener;

public class MainActivity extends AppCompatActivity {

    private ApiCurrency apiCurrency;

    /***/
    CurrencyListener currencyListener = new CurrencyListener() {
        @Override
        public void onBaseChanges(List<String> bases) {
            Spinner spinner = findViewById(R.id.CurrencySpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, bases);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    };

    /***/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiCurrency = new ApiCurrency(Volley.newRequestQueue(this));
        apiCurrency.addListener(this.currencyListener);
        apiCurrency.updateBases();
    }
}