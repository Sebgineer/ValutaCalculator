package dk.zeb.valutacalculator.currency;

import java.util.List;

public interface CurrencyListener {

    /**When list currency bases is changed this observable is activated
     * @param bases is the updated list of bases  */
    public void onBaseChanges(List<String> bases);

}
