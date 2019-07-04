package Fragments;

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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.reactiveandroid.query.Select;

import java.time.OffsetDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Adapter.GenericSpinnerAdapter;
import Adapter.PaymentAdapter;
import Models.BranchDatabase;
import Models.PaymentDatabase;
import Models.UserDBModel;
import ViewModel.PaymentViewModel;

public class DashboardContributionFragment extends Fragment {

    private PaymentViewModel mViewModel;
    private RecyclerView recyclerView;
    private LineChart lineChart;
    private RecyclerView.LayoutManager layoutManager;
    private Button addMemberBt;
    private OnFragmentInteractionListener mListener;
    private PaymentAdapter paymentAdapter;
    //    private int period = Calendar.DAY_OF_WEEK;
    private RecyclerView categoryrecyclerView;

    private String period = "MONTHLY";
    private Spinner branchSpinner;
    private Spinner periodSpinner;
    private List<PaymentDatabase> mPayment = new ArrayList<>();
    private LineDataSet lineDataSet;
    private LineData lineData;


    public static DashboardContributionFragment newInstance() {
        return new DashboardContributionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard_contribution, container, false);

        mViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        branchSpinner = v.findViewById(R.id.contribute_branch_spinner);
//        periodSpinner = getActivity().findViewById(R.id.contr);
//        branchrecyclerView = getActivity().findViewById(R.id.payment_branch_recycler_view);
        categoryrecyclerView = v.findViewById(R.id.category_payment_recycler_view);
        lineChart = v.findViewById(R.id.general_payment_chart);

//        setUpChartFeatures();

        addMemberBt = v.findViewById(R.id.more_cont_button);

//        setListeners();
//        setSpinners();

        // TODO: Use the ViewModel
        categoryrecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        categoryrecyclerView.setLayoutManager(layoutManager);

        setUpChartFeatures();
        initChart(period);
        setSpinners();
        setListeners();


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void setUpChartFeatures() {

        lineChart.getXAxis().setDrawAxisLine(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getXAxis().setDrawGridLinesBehindData(false);


        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisLeft().setDrawGridLinesBehindData(false);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);

        lineChart.setTouchEnabled(false);
    }

    private void initChart(final String period) {

//        mViewModel.getAllPayments(UserDBModel.getUser().get(0).jwt).observe(getActivity(),
//                new Observer<List<PaymentDatabase>>() {
//                    @Override
//                    public void onChanged(@Nullable List<PaymentDatabase> payments) {

        List<PaymentDatabase> payments = PaymentDatabase.getPaymentsByUser();

//        Log.e("TAGGY", String.valueOf(Select.from(PaymentDatabase.class).fetch().size()));
        mPayment.clear();
        mPayment.addAll(payments);
        paymentAdapter = new PaymentAdapter(payments, period);
        categoryrecyclerView.setAdapter(paymentAdapter);
        drawChart(getEntry(payments));
//
//                    }
//                });
//        new doInBc().execute();


    }

    private void initChart(final String period, final List<PaymentDatabase> payemnts) {

        if (paymentAdapter == null) {
            paymentAdapter = new PaymentAdapter(payemnts, period);
            categoryrecyclerView.setAdapter(paymentAdapter);
        } else {
            paymentAdapter.setData(payemnts, period);
        }

        drawChart(getEntry(payemnts));

    }

    private List<Entry> getEntry(final List<PaymentDatabase> payemnts) {
        List<Entry> entries = new ArrayList<>();
        for (Map.Entry<Float, Float> m : cordinatesXY(payemnts, period).entrySet()) {

            entries.add(new Entry(m.getKey(), m.getValue()));

        }
        return entries;
    }

    private void setSpinners() {

//        new doInBg().execute();

        final ArrayList<BranchDatabase> br = new ArrayList<>();
//
        BranchDatabase dummy = new BranchDatabase();
        dummy.setName("All");
        final ArrayList<BranchDatabase> ls = new ArrayList<>();
        ls.clear();
        ls.add(dummy);
        ls.addAll(BranchDatabase.getUserBranches(UserDBModel.getUser().get(0).userid));

        GenericSpinnerAdapter adt =
                new GenericSpinnerAdapter(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, ls
                );

        branchSpinner.setAdapter(adt);

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BranchDatabase bb = (BranchDatabase) parent.getItemAtPosition(position);


                //Update LIstview and lineChart

                if (!bb.getName().equals("All")) {
                    mPayment.clear();
//                    mPayment.addAll(hm.get(bb.getId()));

                    mPayment.addAll(PaymentDatabase.getPaymentsByBranchId(Math.toIntExact(bb.getId())));
                    initChart(period, mPayment);
                } else {
                    initChart(period);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                initChart(period);
            }
        });
    }


    private void setListeners() {
        addMemberBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onContributionMoreButtonClicked();
            }
        });
    }

    private void refreshChart(List<Entry> memberEntries) {
        lineChart.clearValues();
        LineDataSet set = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
        lineDataSet = new LineDataSet(memberEntries, "members");
        lineDataSet.setDrawFilled(true);

        lineDataSet.setColor(Color.GREEN, 100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lineDataSet.setFillDrawable(getContext().getDrawable(R.drawable.member_gradient));
        } else {
            lineDataSet.setFillColor(Color.GREEN);
        }
        set = lineDataSet;

        lineData.addDataSet(set);
        lineChart.notifyDataSetChanged();


    }

    private void drawChart(List<Entry> memberEntries) {
        try {
            if (lineChart.getData().getDataSets() != null) {
                refreshChart(memberEntries);

                Log.d("Works", "daas");


//            lineChart.setData(getLineData(memberEntries));
//            lineChart.notifyDataSetChanged();

                return;

            }
        } catch (NullPointerException e) {
//            refreshChart(memberEntries);
        }
//        if (lineChart.getData().getDataSets()!=null) {
//            refreshChart(memberEntries);
//
//            Log.d("Works","daas");
//
//
////            lineChart.setData(getLineData(memberEntries));
////            lineChart.notifyDataSetChanged();
//
//            return;
//
//        }


        lineChart.setData(getLineData(memberEntries));
//        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    private LineData getLineData(List<Entry> memberEntries) {


//        if (lineDataSet == null) {
        lineDataSet = new LineDataSet(memberEntries, "members");
        lineDataSet.setDrawFilled(true);

        lineDataSet.setColor(Color.GREEN, 100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lineDataSet.setFillDrawable(getContext().getDrawable(R.drawable.member_gradient));
        } else {
            lineDataSet.setFillColor(Color.GREEN);
        }

//        }
        if (lineData == null) {
            lineData = new LineData(lineDataSet);
            lineData.setDrawValues(true);
        }


        //
        return lineData;
    }

    private Map<Float, Float> cordinatesXY(List<PaymentDatabase> payments, String period) {

        Map<Float, Float> hm = new HashMap<>();

        for (PaymentDatabase m : payments) {
            float x = longDateToMonth(m.getCreatedAt(), period);
            Float y = hm.get(x);
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public interface OnFragmentInteractionListener {

        void onContributionMoreButtonClicked();

    }

//    class doInBg extends AsyncTask<Void, Void, List<BranchDatabase>> {
//
//        Map<Integer, List<PaymentDatabase>> hm = new HashMap<>();
//
//        @Override
//        protected List<BranchDatabase> doInBackground(Void... voids) {
//
//
//            Log.d("BranchDatabasees failed", "test");
//            final ArrayList<BranchDatabase> br = new ArrayList<>();
//
//            BranchDatabase dummy = new BranchDatabase();
//            dummy.setName("All");
//            final ArrayList<BranchDatabase> ls = new ArrayList<>();
//            ls.clear();
//            ls.add(dummy);
////            Retrofit retrofit = new Retrofit.Builder()
////
////                    .baseUrl(Utils.BASE_URL)
////                    .addConverterFactory(GsonConverterFactory.create())
////                    .build();
////
////            BranchDatabaseRequest service = retrofit.create(BranchDatabaseRequest.class);
//            Log.d("BranchDatabasees failed", "test");
////            try {
//            List<BranchDatabase> lst = Utils.getUserBranch();
//
////                Map<Integer,PaymentDatabase>hm=new HashMap<>();
//
//
//            for (BranchDatabase b : lst) {
//
//                List<PaymentDatabase> pbList = new ArrayList<>();
//
//                for (Payment p : b.getPayments()) {
//                    PaymentDatabase pb = new PaymentDatabase();
//                    pb.setAmount(p.getAmount());
//                    pb.setCreatedAt(p.getCreatedAt());
//                    pb.setId(p.getId());
//                    pb.setUpdatedAt(p.getUpdatedAt());
//                    pb.setInvestment(Utils.getInvestment(p.getInvestment()));
//                    pbList.add(pb);
//
//
//                }
//                hm.put(Math.toIntExact(b.getId()), pbList);
//            }
//            ls.addAll(lst);
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////            service.getBranchDatabasees().enqueue(new Callback<List<BranchDatabase>>() {
////                @Override
////                public void onResponse(Call<List<BranchDatabase>> call, Response<List<BranchDatabase>> response) {
////                    for (BranchDatabase bg : response.body()) {
////
////                        for (UserDTO user : bg.getUserDTO()
////                        ) {
////                            if (user.getId().equals(UserDBModel.getUser().get(0).userid)) {
////                                br.add(bg);
////
//////                            Log.d("geting branched", bg.getPayments().get(0).getUpdatedAt());
////                            }
////                        }
////                    }
////                    ls.addAll(br);
////
////                }
////
////                @Override
////                public void onFailure(Call<List<BranchDatabase>> call, Throwable t) {
////                    Log.d("BranchDatabasees failed", t.getCause().getMessage());
////                }
////            });
//
//
//            return ls;
//        }
//
//        @Override
//        protected void onPostExecute(List<BranchDatabase> branchList) {
//            super.onPostExecute(branchList);
//
//            GenericSpinnerAdapter adt =
//                    new GenericSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, branchList);
//
//            adt.setNotifyOnChange(true);
//            branchSpinner.setAdapter(adt);
//
//            branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    BranchDatabase bb = (BranchDatabase) parent.getItemAtPosition(position);
//
//
//                    //Update LIstview and lineChart
//
//                    if (!bb.getName().equals("All")) {
//                        mPayment.clear();
//                        mPayment.addAll(hm.get(bb.getId()));
//                        initChart(period, mPayment);
//                    } else {
//                        initChart(period);
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                    initChart(period);
//                }
//            });
//        }
//    }
//
//
//    class doInBc extends AsyncTask<Void, Void, List<PaymentDatabase>> {
//
//        @Override
//        protected List<PaymentDatabase> doInBackground(Void... voids) {
//            Gson gson = new GsonBuilder()
//                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//
//                    .create();
//
//            Retrofit retrofit = new Retrofit.Builder()
//
//                    .baseUrl(Utils.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//            final List<PaymentDatabase> ls = new ArrayList<>();
//            final PaymentRequest service = retrofit.create(PaymentRequest.class);
//            for (BranchDatabase b : Utils.getUserBranchDatabase()) {
//                for (Payment p : b.getPayments()) {
//                    try {
//                        for (PaymentDatabase pb : service.getPayments().execute().body()) {
//                            if (pb.getId().equals(p.getId())) {
//                                ls.add(pb);
//                                break;
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            return ls;
//        }
//
//        @Override
//        protected void onPostExecute(List<PaymentDatabase> paymentBS) {
//            super.onPostExecute(paymentBS);
//
//            mPayment.clear();
//            mPayment.addAll(paymentBS);
//            paymentAdapter = new PaymentAdapter(paymentBS, period);
//            categoryrecyclerView.setAdapter(paymentAdapter);
//            drawChart(getEntry(paymentBS));
//        }
//    }
}
