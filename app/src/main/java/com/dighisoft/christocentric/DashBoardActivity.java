package com.dighisoft.christocentric;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import Adapter.DashboardActivityAdapter;
import Fragments.DashboardContributionFragment;
import Fragments.DashboardMemberFragment;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DashboardMemberFragment.OnFragmentInteractionListener,
        DashboardContributionFragment.OnFragmentInteractionListener {

    private ViewPager pager;
    private DashboardActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        final Toolbar toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
//        toolbar.setTitle("Welcome"+ UserDBModel.getUser().get(0).username);

        pager = (ViewPager) findViewById(R.id.main_viewpager);
        adapter = new DashboardActivityAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }

            @Override
            public void onPageSelected(int i) {

                switch (i) {
                    case 0:
                        toolbar.setTitle("Dashboard - Members");
                        break;
                    case 1:
                        toolbar.setTitle("Dashboard - Contribution");
                        break;
//                        default:
//                            toolbar.setTitle("Dashboard - Members");
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMemberMoreButtonClicked() { // to be renamed
// open member activty with intent

        Intent i = new Intent(this, MemberActivity.class);
//        i.putExtra("FROM", "new_member");
        startActivity(i);

//        Toast.makeText(this, "Not active yet", Toast.LENGTH_LONG);

    }

    @Override
    public void onContributionMoreButtonClicked() {
        Intent i = new Intent(this, KingdomInvestmentActivity.class);
        startActivity(i);
    }


}
