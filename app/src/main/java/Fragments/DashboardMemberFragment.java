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
import android.support.v4.widget.SwipeRefreshLayout;
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
import Adapter.MemberAdapter;
import Models.Branch;
import Models.BranchDatabase;
import Models.Member;
import Models.MemberDatabase;
import Models.PaymentDatabase;
import Models.UserDBModel;
import ViewModel.MemberViewModel;

public class DashboardMemberFragment extends Fragment {

    private MemberViewModel mViewModel;
    private MemberAdapter memberAdapter;
    private RecyclerView recyclerView;
    private LineChart lineChart;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private Button addMemberBt;
    private OnFragmentInteractionListener mListener;
    private String period = "MONTHLY";
    private Spinner branchSpinner;
    //    private Spinner periodSpinner;
    private List<MemberDatabase> mMember = new ArrayList<>();


    public static DashboardMemberFragment newInstance() {
        return new DashboardMemberFragment();
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getActivity().setTitle("Dashboard - Members");
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.member_dashboard_fragment, container, false);
        branchSpinner = v.findViewById(R.id.member_branch_spinner);
//        periodSpinner = getActivity().findViewById(R.id.member_period_spinner);
        mViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        recyclerView = v.findViewById(R.id.my_recycler_view);
        lineChart = v.findViewById(R.id.member_chart);
        addMemberBt = v.findViewById(R.id.member_more_button);
//        mySwipeRefreshLayout=getActivity().findViewById(R.id.m_swipe_refresh);


        // Set Listeners()

        setListeners();
        setSpinners();


        // TODO: Use the ViewModel
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        initChart(period);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void initChart(final String period) {
        final List<Entry> memberEntries = new ArrayList<>();
//        mViewModel.getMembers().observe(getActivity(), new Observer<List<Member>>() {
//            @Override
//            public void onChanged(@Nullable List<Member> members) {
//                // set observable members to the adapter

        List<MemberDatabase> members=new ArrayList<>();
        members.clear();members.addAll(Select.from(MemberDatabase.class).fetch());
                mMember.clear();
                mMember.addAll(members);
                memberAdapter = new MemberAdapter(members, recyclerView);
                recyclerView.setAdapter(memberAdapter);
                for (Map.Entry<Float, Float> m : cordinatesXY(members, period).entrySet()) {

                    memberEntries.add(new Entry(m.getKey(), m.getValue()));

                }

//            }
//        });
        drawChart(lineChart, memberEntries);
    }

    private void initChart(final String period, final List<MemberDatabase> members) {

        final List<Entry> memberEntries = new ArrayList<>();
        memberAdapter.setItems(members);
//        recyclerView.setAdapter(memberAdapter);
        for (Map.Entry<Float, Float> m : cordinatesXY(members, period).entrySet()) {

            memberEntries.add(new Entry(m.getKey(), m.getValue()));

        }
        drawChart(lineChart, memberEntries);

    }

    private void setSpinners() {
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
                Branch b = (Branch) parent.getItemAtPosition(position);


                //Update LIstview and Chart

//                if (!b.getName().equals("All")) {
//                    mMember.clear();
//                    mMember.addAll(b.getMembers());
//                    initChart(period, b.getMembers());
//
//
//                } else {
//                    initChart(period);
//                }

                BranchDatabase bb = (BranchDatabase) parent.getItemAtPosition(position);


                //Update LIstview and lineChart

                if (!bb.getName().equals("All")) {
                    mMember.clear();
//                    mPayment.addAll(hm.get(bb.getId()));

                    mMember.addAll(MemberDatabase.getByBranchId(bb.getId()));
                    initChart(period, mMember);
                } else {
                    initChart(period);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setListeners() {
        addMemberBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMemberMoreButtonClicked();
            }
        });
    }

    private void drawChart(LineChart chart, List<Entry> memberEntries) {
        if (chart.getData() != null) {
            chart.clearValues();
        }
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
        chart.notifyDataSetChanged();
        chart.setData(lineData);

        chart.invalidate();
    }

    private Map<Float, Float> cordinatesXY(List<MemberDatabase> members, String period) {

        Map<Float, Float> hm = new HashMap<>();

        for (MemberDatabase m : members) {
            Float y = hm.get((float) longDateToMonth(m.getCreatedAt(), period));
            int x = longDateToMonth(m.getCreatedAt(), period);


            if (y == null) {
                hm.put((float) x, 1f);
            } else {
                hm.put((float) x, y + 1);
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

    public interface OnFragmentInteractionListener {

        void onMemberMoreButtonClicked();

    }
}
