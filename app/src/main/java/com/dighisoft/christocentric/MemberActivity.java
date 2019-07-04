package com.dighisoft.christocentric;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import Fragments.AddMemberFragment;
import Fragments.MemberActivityHomeFragment;
import Fragments.MemberProfileFragment;

public class MemberActivity extends FragmentActivity implements
        MemberProfileFragment.OnFragmentInteractionListener,
        AddMemberFragment.OnFragmentInteractionListener,
        MemberActivityHomeFragment.OnFragmentInteractionListener {

    public static final String MEMBER_ID = "com.member.id";
    private int memberID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//      memberID = i.getIntExtra(MEMBER_ID, '1'); // ID will be passed to the profile framment

        setContentView(R.layout.member_activity);
        Intent i = getIntent();

//        if (i != null) {

        if (i.getIntExtra(MEMBER_ID, 1) != 1) {
            openFragment(MemberProfileFragment.newInstance(i.getIntExtra(MEMBER_ID, '1'), "name"));
//            }


        } else {
            openFragment(MemberActivityHomeFragment.newInstance("", ""));
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAddMemberButtonClicked() {

    }

    @Override
    public void onAddMemberSuccess(boolean state) {

        // Show exit or stay on page Dialog


    }

    @Override
    public void onAddNewMemberLister() {
        openFragment(AddMemberFragment.newInstance("", ""));
    }

    @Override
    public void onEditButtonClicked(int memberID) {

        // Load Edit Member Fragment


    }

    @Override
    public void onViewDonationButtonCLicked(int memberID) {

        // Load View Donations Fragment

    }

//    @Override
//    public void onAddKingdomInvestmentButtonClicked(int memberID) {
//
//        //Load Kingdom Investment Fragment
//
//        openFragment(AddKingdomInvestmentFragment.newInstance(memberID));
//
//    }

    private void openFragment(final Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_placeholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}