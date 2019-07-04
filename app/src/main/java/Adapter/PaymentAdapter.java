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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.OffsetDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Models.InvestmentDatabase;
import Models.PaymentDatabase;

import static com.reactiveandroid.ReActiveAndroid.getContext;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    List<PaymentDatabase> paymentList;
    final List<Long> br;
    RecyclerView recyclerView;
    String period;

    public PaymentAdapter(List<PaymentDatabase> paymentList, String period) {
        this.paymentList = paymentList;
        this.period = period;

//        Retrofit retrofit = new Retrofit.Builder()
//
//                .baseUrl(Utils.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        BranchRequest service = retrofit.create(BranchRequest.class);

        br = getInvestmentDatabases(paymentList);
    }

    private List<Long> getInvestmentDatabases(
            List<PaymentDatabase> paymentList) {


        Map<Long, Long> mp = new HashMap<>();
        for (PaymentDatabase p : paymentList) {
            mp.put(p.getInvestmentid(), p.getInvestmentid());
        }
        TreeMap<Long, Long> tm = new TreeMap<>(mp);
        List<Long> ls = new ArrayList<>();

        for (Long iv : tm.values()) {

            ls.add(iv);

        }


        return ls;
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

        InvestmentDatabase inv = InvestmentDatabase.getInvestmentById(br.get(i));

        myViewHolder.categoryName.setText(inv.getName()); // set the branch name
        buildChart(myViewHolder.chart, inv.getId()); //


    }

    private void buildChart(LineChart chart, Long investmentId) {


        ArrayList<PaymentDatabase> ls = new ArrayList<>();

        for (PaymentDatabase p : paymentList) {

            if (p.getInvestmentid().equals(investmentId)) {

                ls.add(p);

                Log.d("Log log", p.getAmount().toString());

            }

        }


        drawChart(chart, getEntry(ls));


    }

    private List<Entry> getEntry(ArrayList<PaymentDatabase> ls) {
        final List<Entry> paymentEntries = new ArrayList<>();
        for (Map.Entry<Float, Float> m : cordinatesXY(ls, period).entrySet()) {

            paymentEntries.add(new Entry(m.getKey(), m.getValue()));

        }
        return paymentEntries;
    }

    private Map<Float, Float> cordinatesXY(List<PaymentDatabase> payments, String period) {

        Map<Float, Float> hm = new HashMap<>();


        for (PaymentDatabase m : payments) {
            float x = longDateToMonth(m.getCreatedAt(), period);
            Float y = hm.get(x);
//            Float y = m.getAmount().floatValue();
            if (y == null) {
                hm.put(x, m.getAmount().floatValue());
            } else {
                hm.put(x, hm.get(x) + m.getAmount().floatValue());
            }

            Log.d("Count", String.valueOf(x) + "::" + String.valueOf(hm.get((float) x)));

        }
        Log.d("Count", String.valueOf(hm.size()));


        return new TreeMap<>(hm);

    }

    private int longDateToMonth(String date, String period) {

        switch (period) {
            case "YEAR":
                return OffsetDateTime.parse(date).getYear();
            case "WEEKLY":
                return OffsetDateTime.parse(date).getDayOfWeek().getValue();

            case "MONTHLY":
                if (OffsetDateTime.parse(date).getYear() == Year.now().getValue()) {
                    return OffsetDateTime.parse(date).getMonthValue();
                }
            case "DAILY":
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

    public void setData(List<PaymentDatabase> payemnts, String period) {
        paymentList.clear();
        paymentList.addAll(payemnts);
        this.period = period;
        br.clear();
        br.addAll(getInvestmentDatabases(paymentList));
        notifyDataSetChanged();
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
