package dk.zeb.valutacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import dk.zeb.valutacalculator.currency.ApiCurrency;

public class MainActivity extends AppCompatActivity {

    List<String> list;
    ApiCurrency apiCurrency = new ApiCurrency();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiCurrency.getRates("DKK");

    }
}