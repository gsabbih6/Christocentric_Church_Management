package Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dighisoft.christocentric.R;
import com.dighisoft.christocentric.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import Adapter.GenericSpinnerAdapter;
import Models.Branch;
import Models.Investment;
import Models.Member;
import Models.Payment;
import Models.UserDBModel;
import Models.UserDTO;
import Request.BranchRequest;
import Request.PaymentRequest;
import ViewModel.AddKingdomInvestmentViewModel;
import ViewModel.InvestmentTypeVM;
import ViewModel.MemberViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddKingdomInvestmentFragment extends Fragment {

    private AddKingdomInvestmentViewModel mViewModel;
    private Spinner branches, members, investtype;
    private Button proceed;
    private EditText amount;
    private Integer branchesId;
    private Integer investtypId;
    private Integer memberId;
    private PaymentRequest service;

    public static AddKingdomInvestmentFragment newInstance(int memberID) {
        return new AddKingdomInvestmentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_kingdom_investment_fragment, container, false);


        members = v.findViewById(R.id.payment_member);
        branches = v.findViewById(R.id.payment_branch);
        investtype = v.findViewById(R.id.payment_investtype);
        proceed = v.findViewById(R.id.inv_proceed_button);
        amount = v.findViewById(R.id.inv_amount);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")

                .create();
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(PaymentRequest.class);
        // TODO: Use the ViewModel

        setBranches();
        setInvestment();
        setListeners();


    }

    private void setListeners() {
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Process result
                if (!amount.getText().toString().matches("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setMessage(" Do want to proceed ?")
                            .setTitle("Add new Kingdom Investment")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // proceed

                                    Payment p = new Payment();
                                    p.setAmount(Double.parseDouble(amount.getText().toString()));
                                    p.setBranch(branchesId);
                                    p.setInvestment(investtypId);

                                    if (memberId != -1) {
                                        p.setMember(memberId);
                                    }

                                    // set request

                                    service.addNewPayment(p).enqueue(new Callback<Payment>() {
                                        @Override
                                        public void onResponse(Call<Payment> call, Response<Payment> response) {
                                            if (response.isSuccessful()) {

                                                // Show success dialog

                                                amount.setText("");
                                                branches.setSelection(0);

                                                Toast.makeText(getContext(), "Successfully posted payment", Toast.LENGTH_LONG).show();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<Payment> call, Throwable t) {

                                        }
                                    });


                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

//                          clos

                        }
                    }).show();

//                  AlertDialog dialog = builder.create();
//                  dialog.
                }

            }
        });
    }

    private void setBranches() {

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BranchRequest service = retrofit.create(BranchRequest.class);

        final List<Branch> br = new ArrayList<>();

        service.getBranches().enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Call<List<Branch>> call, Response<List<Branch>> response) {
                Log.d("Branches type", String.valueOf(response.body().get(0).getUserDTO().size()));
                for (Branch bg : response.body()) {

                    for (UserDTO user : bg.getUserDTO()
                    ) {

                        if (user.getId().equals(UserDBModel.getUser().get(0).userid)) {
                            br.add(bg);
                        }
                    }
                }

                GenericSpinnerAdapter branchArrayAdapter = new GenericSpinnerAdapter(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, br);
                branches.setAdapter(branchArrayAdapter);

            }

            @Override
            public void onFailure(Call<List<Branch>> call, Throwable t) {
                Log.d("Branches failed", t.getCause().getMessage());
            }
        });


        branches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Branch b = (Branch) parent.getItemAtPosition(position);

                setMembers(b.getId());
                branchesId = b.getId();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setMembers(int branchID) {

        MemberViewModel memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        final List<Member> list = new ArrayList<>();
        Member dummy = new Member();
        dummy.setFirstname("N/A");
        dummy.setLastname("");
        list.clear();
        list.add(dummy);
        memberViewModel.getMembersByBranch(branchID).observe(getActivity(), new Observer<List<Member>>() {
            @Override
            public void onChanged(@Nullable List<Member> m) {
                list.addAll(m);

                Log.d("Member count failed", String.valueOf(m.size()));
                GenericSpinnerAdapter membersArrayAdapter = new GenericSpinnerAdapter(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        list);
                membersArrayAdapter.setNotifyOnChange(true);
                members.setAdapter(membersArrayAdapter);
//                membersArrayAdapter.notifyDataSetChanged();

            }
        });

        members.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Member m = (Member) parent.getItemAtPosition(position);
                if (m.getFirstname().equals("N/A")) {
                    memberId = -1;
                } else {
                    memberId = m.getId();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        return  ;
    }

    private void setInvestment() {

        InvestmentTypeVM investViewModel = ViewModelProviders.of(this).get(InvestmentTypeVM.class);

        investViewModel.getInvestments(UserDBModel.getUser().get(0).church).observe(getActivity(), new Observer<List<Investment>>() {
            @Override
            public void onChanged(@Nullable List<Investment> investments) {

                //


                GenericSpinnerAdapter inArrayAdapter = new GenericSpinnerAdapter(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item,
                        investments);

                investtype.setAdapter(inArrayAdapter);
            }
        });
        investtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Investment b = (Investment) parent.getItemAtPosition(position);
                investtypId = b.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
