package Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.dighisoft.christocentric.R;
import com.dighisoft.christocentric.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Adapter.GenericSpinnerAdapter;
import Adapter.MemberAdapter;
import Adapter.PaymentAdapter;
import Models.Branch;
import Models.Member;
import Models.PaymentB;
import Models.PaymentB;
import Models.UserDBModel;
import Models.UserDTO;
import Request.BranchRequest;
import ViewModel.PaymentViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardContributionFragment extends Fragment {

    private PaymentViewModel mViewModel;
    private RecyclerView recyclerView;
    private LineChart lineChart;
    private RecyclerView.LayoutManager layoutManager;
    private Button addMemberBt;
    private OnFragmentInteractionListener mListener;
    private PaymentAdapter paymentAdapter;
//    private int period = Calendar.DAY_OF_WEEK;
    private RecyclerView branchrecyclerView;
    private RecyclerView categoryrecyclerView;

    private String period = "MONTHLY";
    private Spinner branchSpinner;
    private Spinner periodSpinner;
    private List<PaymentB> mPayment = new ArrayList<>();


    public static DashboardContributionFragment newInstance() {
        return new DashboardContributionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_contribution, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        branchSpinner = getActivity().findViewById(R.id.member_branch_spinner);
        periodSpinner = getActivity().findViewById(R.id.member_period_spinner);branchrecyclerView = getActivity().findViewById(R.id.payment_branch_recycler_view);
        categoryrecyclerView = getActivity().findViewById(R.id.category_payment_recycler_view);
        lineChart = getActivity().findViewById(R.id.general_payment_chart);

        addMemberBt = getActivity().findViewById(R.id.add_new_member_button);

        setListeners();


        // TODO: Use the ViewModel
        branchrecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        branchrecyclerView.setLayoutManager(layoutManager);
        initChart("MONTH");


    }

    private void setListeners() {
        addMemberBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onContributionMoreButtonClicked();
            }
        });
    }

    private void setSpinners() {
        final ArrayList<Branch> br = new ArrayList<>();

        Branch dummy = new Branch();
        dummy.setName("All");
        final ArrayList<Branch> ls = new ArrayList<>();
        ls.clear();
        ls.add(dummy);
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BranchRequest service = retrofit.create(BranchRequest.class);

        service.getBranches().enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Call<List<Branch>> call, Response<List<Branch>> response) {
                for (Branch bg : response.body()) {

                    for (UserDTO user : bg.getUserDTO()
                    ) {
                        if (user.getId().equals(UserDBModel.getUser().get(0).userid)) {
                            br.add(bg);

                            Log.d("geting branched", bg.getName());
                        }
                    }
                }
                ls.addAll(br);
                GenericSpinnerAdapter adt =
                        new GenericSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, ls);

                adt.setNotifyOnChange(true);
                branchSpinner.setAdapter(adt);

            }

            @Override
            public void onFailure(Call<List<Branch>> call, Throwable t) {
                Log.d("Branches failed", t.getCause().getMessage());
            }
        });

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Branch b = (Branch) parent.getItemAtPosition(position);


                //Update LIstview and Chart

                if (!b.getName().equals("All")) {
                    mPayment.clear();
                    mPayment.addAll(b.getPayments());
                    initChart(period, b.getPayments());


                } else {
                    initChart(period);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mperiod = (String) parent.getItemAtPosition(position);
//                initChart(period.toUpperCase());

                period = mperiod;

                if (mPayment.size() > 0)
                    initChart(mperiod.toUpperCase(), mPayment);

                Log.d("Spineer Click", period);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initChart(final String period) {

        final List<Entry> paymentEntries = new ArrayList<>();
        mViewModel.getAllPayments(UserDBModel.getUser().get(0).jwt).observe(getActivity(), new Observer<List<PaymentB>>() {
            @Override
            public void onChanged(@Nullable List<PaymentB> payments) {

                paymentAdapter = new PaymentAdapter(payments, period);
                branchrecyclerView.setAdapter(paymentAdapter);


//                // update the chart
                for (Map.Entry<Float, Float> m : cordinatesXY(payments, period).entrySet()) {

                    paymentEntries.add(new Entry(m.getKey(), m.getValue()));

                }

//                paymentEntries.add(new Entry(1f, 19f));
//                paymentEntries.add(new Entry(2f, 2f));
//                paymentEntries.add(new Entry(3f, 2f));
//                paymentEntries.add(new Entry(4f, 14f));
//                paymentEntries.add(new Entry(5f, 14f));
                drawChart(lineChart, paymentEntries);

            }
        });
    }
    private void initChart(final String period, final List<PaymentB> payemnts) {

        final List<Entry> memberEntries = new ArrayList<>();
        paymentAdapter = new PaymentAdapter(payemnts,period);
        recyclerView.setAdapter(paymentAdapter);
        for (Map.Entry<Float, Float> m : cordinatesXY(mPayment, period).entrySet()) {

            memberEntries.add(new Entry(m.getKey(), m.getValue()));

        }
        drawChart(lineChart, memberEntries);

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onContributionMoreButtonClicked();

    }
}
