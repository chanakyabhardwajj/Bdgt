package com.chanakyabhardwaj.bdgt;

import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.Tools;
import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.AxisController;
import com.db.chart.view.BarChartView;
import com.db.chart.view.LineChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.BounceEase;
import com.db.chart.view.animation.easing.ElasticEase;

public class ChartsFragment extends Fragment {
    private final String[] mLabels = {"Jan", "Fev", "Mar", "Apr", "Jun", "May", "Jul", "Aug", "Sep"};
    private final float[][] mValues = {{3.5f, 4.7f, 4.3f, 8f, 6.5f, 9.9f, 7f, 8.3f, 7.0f},
            {4.5f, 2.5f, 2.5f, 9f, 4.5f, 9.5f, 5f, 8.3f, 1.8f}};

    LineChartView lineChartView;
    BarChartView barChartView;

    void prepareLineChart() {
        LineSet dataset = new LineSet(mLabels, mValues[0]);
        dataset.setColor(Color.parseColor("#758cbb"))
                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#758cbb"))
                .setThickness(4)
                .setDashed(new float[]{10f,10f})
                .beginAt(5);
        lineChartView.addData(dataset);

        Animation anim = new Animation()
                .setEasing(new BounceEase());
        lineChartView.show(anim);
    }


    void prepareBarChart() {
        float[] mValues = {3.5f, 4.7f, 4.3f, 8f, 6.5f};
        BarSet dataset = new BarSet();
        dataset.addBar("coffee", 10f);
        dataset.addBar("lunch", 20f);
        dataset.addBar("dinner", 20f);
        dataset.addBar("tickets", 3f);
        dataset.addBar("shoes", 70f);



        Tooltip tip = new Tooltip(getContext());
        tip.setBackgroundColor(Color.parseColor("#CC7B1F"));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1);
            tip.setEnterAnimation(alpha).setDuration(150);

            alpha = PropertyValuesHolder.ofFloat(View.ALPHA,0);
            tip.setExitAnimation(alpha).setDuration(150);
        }

        barChartView.setTooltips(tip);


        dataset.setColor(Color.parseColor("#eb993b"));
        barChartView.addData(dataset);

        barChartView.setBarSpacing(Tools.fromDpToPx(3));

        barChartView.setXLabels(AxisController.LabelPosition.OUTSIDE)
                .setYLabels(AxisController.LabelPosition.OUTSIDE)
                .setXAxis(true)
                .setYAxis(true);

        Animation anim = new Animation()
                .setEasing(new ElasticEase());

        barChartView.show(anim);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_charts, container, false);
        lineChartView = (LineChartView) rootView.findViewById(R.id.linechart);
        barChartView = (BarChartView) rootView.findViewById(R.id.barchart);
//        prepareLineChart();
        prepareBarChart();
        return rootView;
    }
}