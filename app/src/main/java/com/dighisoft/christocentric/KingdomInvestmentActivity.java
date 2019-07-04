package com.dighisoft.christocentric;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import Fragments.AddKingdomInvestmentFragment;
import Fragments.ContributionHomeFragment;

//import

public class KingdomInvestmentActivity extends AppCompatActivity implements ContributionHomeFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kingdom_investment);
        openFragment(ContributionHomeFragment.newInstance("", ""));


    }

    private void openFragment(final Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_KI_placeholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();

        if (getSupportFragmentManager().getBackStackEntryCount() <= 0)
            finish();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAddNewContribution() {

        openFragment(AddKingdomInvestmentFragment.newInstance());

    }
}
