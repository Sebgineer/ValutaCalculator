package dk.zeb.valutacalculator.currency;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ApiCurrency implements CurrencyDAO {
    private String key = "317d4391da7c6878cf4ee44a";
    private String path = String.format("https://v6.exchangerate-api.com/v6/%s/", key);

    public ApiCurrency() {

    }

    @Override
    public List<Rate> getRates(String base) {
        try {


            URL url = new URL("https://v6.exchangerate-api.com/v6/317d4391da7c6878cf4ee44a/latest/DKK");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.connect();

            System.out.println("kage");
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}
