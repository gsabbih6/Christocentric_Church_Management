package Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import Fragments.DashboardContributionFragment;
import Fragments.DashboardMemberFragment;


public class DashboardActivityAdapter extends FragmentStatePagerAdapter {

    public DashboardActivityAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return DashboardMemberFragment.newInstance();
            case 1:
                return DashboardContributionFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
