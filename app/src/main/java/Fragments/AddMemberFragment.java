package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dighisoft.christocentric.R;
import com.dighisoft.christocentric.Utils;

import Adapter.GenericSpinnerAdapter;
import Models.BranchB;
import Models.BranchDatabase;
import Models.Member;
import Models.UserDBModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddMemberFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddMemberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMemberFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText firstNameEdittext, middleNameEdittext, surNameEdittext,
            emailEdittext, schoolEdittext, addressEdittext, telephoneEdittext, occupationEdittext,
            dobEdittext, specialnotesEdittext;
    private Spinner branchSpinner, memberStatusSpinner, maritalStatusSpinner;
    private Button addMemberButton;

    public AddMemberFragment() {

        // Required empty public constructor

    }

    public static AddMemberFragment newInstance(String param1, String param2) {
        AddMemberFragment fragment = new AddMemberFragment();
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


        View v = inflater.inflate(R.layout.fragment_add_member, container, false);

        firstNameEdittext = v.findViewById(R.id.member_firstname);
        middleNameEdittext = v.findViewById(R.id.member_middlename);
        surNameEdittext = v.findViewById(R.id.member_lastname);
        emailEdittext = v.findViewById(R.id.member_email);
        telephoneEdittext = v.findViewById(R.id.member_tel);
        addressEdittext = v.findViewById(R.id.member_address);
        schoolEdittext = v.findViewById(R.id.member_school);
        occupationEdittext = v.findViewById(R.id.member_occupation);
        dobEdittext = v.findViewById(R.id.member_dob);
        memberStatusSpinner = v.findViewById(R.id.member_status);
        maritalStatusSpinner = v.findViewById(R.id.marital_status);
        branchSpinner = v.findViewById(R.id.branch);
        specialnotesEdittext = v.findViewById(R.id.special_notes);
        addMemberButton = v.findViewById(R.id.add_member_button);

        // Inflate the layout for this fragment
        setListeners();

        return v;
    }

    private void setListeners() {

        dobEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show the calender


            }
        });
        GenericSpinnerAdapter adt = new GenericSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, BranchDatabase.getAll());
        branchSpinner.setAdapter(adt);

        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verifyFields()) {
                    Member member = new Member();
                    member.setAddress(addressEdittext.getText().toString());

                    BranchDatabase branchDatabase = (BranchDatabase) branchSpinner.getSelectedItem();
                    BranchB branchB = new BranchB();

//                    branchB.setAddress(branchDatabase.getAddress());
                    branchB.setId(Math.toIntExact(branchDatabase.getId()));
//                    branchB.setName(branchB.getName())
                    member.setBranch(branchB);
//                    member.setDob(dateitem);
                    member.setEmail(emailEdittext.getText().toString());
                    member.setFirstname(firstNameEdittext.getText().toString());
                    member.setLastname(surNameEdittext.getText().toString());
                    member.setMaritalStatus(maritalStatusSpinner.getSelectedItem().toString());
                    member.setOccupation(occupationEdittext.getText().toString());
                    member.setOthername(middleNameEdittext.getText().toString());
                    member.setSchool(schoolEdittext.getText().toString());
                    member.setTelephone(telephoneEdittext.getText().toString());
                    member.setSpecialNotes(specialnotesEdittext.getText().toString());
                    member.setStatus(getMemberStatus(memberStatusSpinner.getSelectedItem().toString()));


                    Utils.getMemberRequest().addNewMember(UserDBModel.getUser().get(0).jwt, member).enqueue(new Callback<Member>() {
                        @Override
                        public void onResponse(Call<Member> call, Response<Member> response) {
                            mListener.onAddMemberSuccess(true);
                            Utils.reset(addressEdittext, emailEdittext, dobEdittext,
                                    firstNameEdittext, surNameEdittext, middleNameEdittext,
                                    occupationEdittext, schoolEdittext);
                        }

                        @Override
                        public void onFailure(Call<Member> call, Throwable t) {
                            mListener.onAddMemberSuccess(false);
                        }
                    });
                }


                mListener.onAddMemberButtonClicked();

            }
        });
    }

    private boolean verifyFields() {

        return Utils.isEmpty(addressEdittext, emailEdittext, dobEdittext,
                firstNameEdittext, surNameEdittext, middleNameEdittext, occupationEdittext, schoolEdittext);
    }

    private Boolean getMemberStatus(String toString) {
        if (toString.equalsIgnoreCase("Member")) {
            return true;
        } else {
            return false;
        }

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);

        void onAddMemberButtonClicked();

        void onAddMemberSuccess(boolean state);
    }
}
