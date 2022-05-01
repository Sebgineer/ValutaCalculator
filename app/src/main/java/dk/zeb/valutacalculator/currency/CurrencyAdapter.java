package dk.zeb.valutacalculator.currency;

import android.content.Context;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import dk.zeb.valutacalculator.R;

import java.text.DecimalFormat;
import java.util.List;

public class CurrencyAdapter extends ArrayAdapter<Rate> {
    public CurrencyAdapter(Context context, List<Rate> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Rate rate = getItem(position);
        DecimalFormat df = new DecimalFormat("0.00");
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rate_row_item, parent, false);
        }

        TextView currencyTitle = (TextView) convertView.findViewById(R.id.rateTitle);
        TextView currencyAmount = (TextView) convertView.findViewById(R.id.rateValue);

        currencyTitle.setText(rate.getName());
        currencyAmount.setText(String.format("%s", df.format(rate.getSpotRate())));

        return convertView;
    }
}
