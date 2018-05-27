package vlad.worchron;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager)findViewById(R.id.main_view_pager);
        PagerAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabs = (TabLayout) findViewById(R.id.main_tab_layout);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabTextColors(
                getResources().getColor(R.color.textColor),
                getResources().getColor(R.color.textColor)
        );
    }
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);

        //TODO Add the logo of the app
        //getSupportActionBar().setIcon(R.drawable.ic_launcher_foreground);
        return super.onCreateOptionsMenu(menu);
    }

    private class PageAdapter extends FragmentPagerAdapter{
        private String [] tabTitles = {"Workouts", "Exercises"};
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new WorkoutsFragment();
                case 1:
                    return new ExercisesFragment();
                    default:
                        return null;

            }
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return tabTitles[position];
        }
    }
}
