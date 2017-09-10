package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XAxisValueFormatter implements IAxisValueFormatter {

    private DateFormat dateFormat;

    public XAxisValueFormatter(){
        dateFormat = new SimpleDateFormat("dd/MM/yy");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Date date = new Date((long)value * 1000);
        return dateFormat.format(date);
    }
}
