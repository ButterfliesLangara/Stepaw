package com.butterflies.stepaw;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LineChart mChart;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<DonutSection> kmDataList = new ArrayList<>();
    private List<DonutSection> minDataList = new ArrayList<>();

    public WeeklyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeeklyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyFragment newInstance(String param1, String param2) {
        WeeklyFragment fragment = new WeeklyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_weekly, container, false);

        //daily report chart generator - donut chart
        DonutProgressView kmDonutChart = view.findViewById(R.id.kmDonutView);
        DonutProgressView minDonutChart = view.findViewById(R.id.minDonutView);

        renderDonutData(kmDonutChart, minDonutChart);

        //weekly report - line chart generation
        mChart = view.findViewById(R.id.chart);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        mChart.animateY(1000);
        mChart.animateX(1000);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(getActivity(), "Value selected", Toast.LENGTH_SHORT).show();
                renderDonutData(kmDonutChart, minDonutChart);
                //System.out.println(e.getX()+ " " + e.getY());
            }

            @Override
            public void onNothingSelected() {

            }
        });

        renderData();
        return view;
    }

    private void renderDonutData(DonutProgressView kmDonutChart, DonutProgressView minDonutChart){

        Random rn = new Random();
        float answer = rn.nextInt(5) + 1;
        DonutSection kmSection = new DonutSection("km",
                Color.parseColor("#004E99"), answer);

        DonutSection minSection = new DonutSection("min",
                Color.parseColor("#FBD617"),answer);

        kmDataList = new ArrayList<>();
        kmDataList.add(kmSection);
        kmDonutChart.setCap(5f);
        kmDonutChart.submitData(kmDataList);
        kmDonutChart.animate();

        minDataList = new ArrayList<>();
        minDataList.add(minSection);
        minDonutChart.setCap(5f);
        minDonutChart.submitData(minDataList);
        minDonutChart.animate();

    }


    public void renderData() {

        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(12);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getXAxisValues()));

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);

        mChart.getAxisRight().setEnabled(true);
        mChart.getAxisRight().setDrawLabels(false);
        mChart.getAxisRight().setDrawAxisLine(false);
        mChart.getAxisRight().enableGridDashedLine(10f, 10f, 10f);
        mChart.getLegend().setEnabled(false);
        mChart.getDescription().setEnabled(false);
        setData();
    }

    private ArrayList<String> getXAxisValues()
    {
        ArrayList<String> labels = new ArrayList<> ();

        labels.add( "SUN");
        labels.add( "MON");
        labels.add( "TUE");
        labels.add( "WED");
        labels.add( "THU");
        labels.add( "FRI");
        labels.add( "SAT");
        return labels;
    }

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 50));
        values.add(new Entry(1, 100));
        values.add(new Entry(2, 80));
        values.add(new Entry(3, 120));
        values.add(new Entry(4, 110));
        values.add(new Entry(5, 150));
        values.add(new Entry(6, 250));


        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {

            set1 = new LineDataSet(values, "Walk Data");
            set1.setDrawIcons(false);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);

            set1.setLineWidth(1.5f);
            set1.setCircleRadius(5f);
            set1.setDrawCircleHole(true);
            set1.setDrawValues(false);
            set1.setDrawFilled(false);
            set1.setFormLineWidth(.5f);
            set1.setFormSize(15f);
            set1.setDrawHorizontalHighlightIndicator(true);
            set1.setHighLightColor(Color.rgb(255,222,46));
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);

            mChart.setData(data);
        }
    }

}