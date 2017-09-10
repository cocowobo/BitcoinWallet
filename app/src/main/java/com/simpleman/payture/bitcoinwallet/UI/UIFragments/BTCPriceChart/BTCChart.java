package com.simpleman.payture.bitcoinwallet.UI.UIFragments.BTCPriceChart;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.simpleman.payture.bitcoinwallet.R;

import java.util.ArrayList;
import java.util.List;

public class BTCChart {

    private LineChart chart;
    private List<Entry> entries;
    private LineDataSet dataSet;
    private LineData lineData;

    public BTCChart(BTCPriceHistoryItem[] priceHistory, Fragment fragment){

        chart = (LineChart)(((BTCPriceChartFragment)fragment).getView().findViewById(R.id.btc_price_chart));
        chart.setNoDataTextColor(Color.BLACK);
        chart.setNoDataText("Отсутствует подключение к сети");

        if (priceHistory != null) {

            entries = new ArrayList<Entry>();

            for (BTCPriceHistoryItem item : priceHistory) {
                entries.add(new Entry(item.getDate(), item.getPrice()));
            }

            dataSet = new LineDataSet(entries, null);
            stylizeChart(fragment);
            lineData = new LineData(dataSet);
            chart.setData(lineData);
        } else {
            chart.setData(null);
        }
        chart.invalidate();
    }

    private void formatXAxis(){
        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelCount(4, true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new XAxisValueFormatter());
    }

    private void formatYAxis(){
        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();
        leftAxis.setDrawAxisLine(false);
        rightAxis.setDrawAxisLine(false);
        leftAxis.setTextSize(10f);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);
        rightAxis.setDrawGridLines(true);
    }

    private void stylizeChart(Fragment fragment){
        formatXAxis();
        formatYAxis();

        dataSet.setColor(Color.BLUE);
        dataSet.setHighlightEnabled(false);
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(fragment.getContext().getDrawable(R.drawable.btc_price_chart_gradient));
        chart.animateX(1500, Easing.EasingOption.EaseInOutCubic);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setScaleEnabled(false);
    }

}
