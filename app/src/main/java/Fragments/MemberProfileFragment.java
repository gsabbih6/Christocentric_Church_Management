package Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dighisoft.christocentric.R;
import com.dighisoft.christocentric.Utils;
import com.squareup.picasso.Picasso;

import Models.Member;
import ViewModel.MemberViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MemberProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemberProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView telNo, name, address, note, status, dob;
    private ImageView imageView;
    private Button addKI, editProfile, viewDonations;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MemberViewModel mViewModel;

    public MemberProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param memberID Parameter 1.
     * @param param2   Parameter 2.
     * @return A new instance of fragment MemberProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberProfileFragment newInstance(int memberID, String param2) {
        MemberProfileFragment fragment = new MemberProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, memberID);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v=inflater.inflate(R.layout.fragment_member_profile, container, false);
        telNo = v.findViewById(R.id.member_tel);
        address = v.findViewById(R.id.member_address);
        name = v.findViewById(R.id.member_name);
        note = v.findViewById(R.id.member_special_note);
        status = v.findViewById(R.id.member_status);
        dob = v.findViewById(R.id.member_dob);
        imageView = v.findViewById(R.id.member_photo_profile);

        addKI = v.findViewById(R.id.member_addKI_button);
        editProfile = v.findViewById(R.id.member_edit_button);
        viewDonations = v.findViewById(R.id.member_view_donations_button);


        setListeners();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        mViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);

        mViewModel.getSelectedMember(getArguments().getInt(ARG_PARAM1)).observe(getActivity(), new Observer<Member>() {
            @Override
            public void onChanged(@Nullable Member member) {
                telNo.setText(member.getTelephone());
                name.setText(member.getFirstname() + " " + member.getLastname());
                address.setText(member.getAddress());
                note.setText(member.getSpecialNotes());
                status.setText(member.getStatus() == null ? "Status: Visitor" : "Status: Member ");
                dob.setText(member.getDob());
                try {
                    Picasso.get().load(Utils.BASE_URL + member.getPhoto().getUrl()).into(imageView);
                    Log.d("VMODEL", Utils.BASE_URL + member.getPhoto().getUrl());
                } catch (NullPointerException e) {
                }
            }
        });


    }

    private void setListeners() {
        addKI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddKingdomInvestmentButtonClicked(getArguments().getInt(ARG_PARAM1));


            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddKingdomInvestmentButtonClicked(getArguments().getInt(ARG_PARAM1));

            }
        });
        viewDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // verify that the user has that role
                mListener.onAddKingdomInvestmentButtonClicked(getArguments().getInt(ARG_PARAM1));

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onEditButtonClicked(int memberID);

        void onViewDonationButtonCLicked(int memberID);

        void onAddKingdomInvestmentButtonClicked(int memberID);
    }
}
