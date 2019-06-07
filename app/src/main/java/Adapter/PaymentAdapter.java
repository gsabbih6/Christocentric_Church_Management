package Adapter;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dighisoft.christocentric.R;
import com.dighisoft.christocentric.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Models.Investment;
import Models.Payment;
import Models.PaymentB;
import Request.BranchRequest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.reactiveandroid.ReActiveAndroid.getContext;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    List<PaymentB> paymentList;
    final List<Investment> br;
    RecyclerView recyclerView;
    String period;

    public PaymentAdapter(List<PaymentB> paymentList, String period) {
        this.paymentList = paymentList;
        this.period = period;

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BranchRequest service = retrofit.create(BranchRequest.class);

        br = getInvestments(paymentList);
    }

    private List<Investment> getInvestments(
            List<PaymentB> paymentList) {
        Map<Investment,PaymentB> mp=new HashMap<>();
        for(PaymentB p:paymentList){
            mp.put(p.getInvestment(),p);
        }

        return (List<Investment>) mp.keySet();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_category_contribution, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.categoryName.setText(br.get(i).getName()); // set the branch name
        buildChart(myViewHolder.chart, br.get(i).getId()); //


    }

    private void buildChart(LineChart chart, int branchID) {


        ArrayList<PaymentB> ls = new ArrayList<>();

        for (PaymentB p : paymentList) {

            if (p.getBranch().getId().equals(branchID)) {

                ls.add(p);

                Log.d("Log log", p.getAmount().toString());

            }

        }


        drawChart(chart, getEntry(ls));


    }

    private List<Entry> getEntry(ArrayList<PaymentB> ls) {
        final List<Entry> paymentEntries = new ArrayList<>();
        for (Map.Entry<Float, Float> m : cordinatesXY(ls, period).entrySet()) {

            paymentEntries.add(new Entry(m.getKey(), m.getValue()));

        }
        return paymentEntries;
    }

    private Map<Float, Float> cordinatesXY(List<PaymentB> payments, String period) {

        Map<Float, Float> hm = new HashMap<>();

        for (PaymentB m : payments) {
            float x = longDateToMonth(m.getCreatedAt(), period);
            Float y = hm.get(x);
//            Float y = m.getAmount().floatValue();
            if (y == null) {
                hm.put(x, m.getAmount().floatValue());
            } else {
                hm.put(x, hm.get(x) + y);
            }

            Log.d("Count", String.valueOf(x) + "::" + String.valueOf(hm.get((float) x)));

        }
        Log.d("Count", String.valueOf(hm.size()));


        return new TreeMap<>(hm);

    }

    private int longDateToMonth(String date, String period) {
//        Log.d("Date", date);
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
////        Date d = new Date(date);
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(df.parse(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("Year", String.valueOf(c.get(period)));
//        return c.get(period);
//        DateTimeFormatter.ISO_DATE_TIME;

        switch (period) {
            case "YEAR":
                return OffsetDateTime.parse(date).getYear();

            case "MONTH":
                return OffsetDateTime.parse(date).getMonthValue();
            case "DAY":
                return OffsetDateTime.parse(date).getDayOfYear();
            default:
                return OffsetDateTime.parse(date).getMonthValue();
        }

    }

    private void drawChart(LineChart chart, List<Entry> memberEntries) {

        LineDataSet lineDataSet = new LineDataSet(memberEntries, "members");
        lineDataSet.setDrawFilled(true);

        lineDataSet.setColor(Color.GREEN, 100);

        // set attribute

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            lineDataSet.setFillColor(Color.argb(112f,100,222,100));
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lineDataSet.setFillDrawable(getContext().getDrawable(R.drawable.member_gradient));
        } else {
            lineDataSet.setFillColor(Color.GREEN);
        }

        //
        LineData lineData = new LineData(lineDataSet);
        lineData.setDrawValues(true);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setDrawLabels(false);
        chart.getXAxis().setDrawGridLinesBehindData(false);


        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisLeft().setDrawGridLinesBehindData(false);

        chart.getAxisRight().setEnabled(false);

        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);

        chart.setTouchEnabled(false);

        chart.setData(lineData);
        chart.invalidate();
    }

    @Override
    public int getItemCount() {
        return br.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView categoryName;
        public LineChart chart;

        public MyViewHolder(View v) {
            super(v);
            chart = v.findViewById(R.id.category_payment_chart);
            this.categoryName = v.findViewById(R.id.category_payment_name);
        }
    }


}
