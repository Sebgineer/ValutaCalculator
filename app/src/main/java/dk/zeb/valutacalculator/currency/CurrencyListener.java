package dk.zeb.valutacalculator.currency;

import java.util.List;

public interface CurrencyListener {

    public void onBaseChanges(List<String> bases);

}
