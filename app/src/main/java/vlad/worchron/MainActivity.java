package vlad.worchron;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import java.util.List;

import vlad.DAO.GeneralDAO;
import vlad.DAO.WorkoutDAO;
import vlad.Previews.ExercisePreview;
import vlad.Previews.WorkoutPreview;
import vlad.backend.Exercises.Exercise;
import vlad.backend.Exercises.SelectableExercise;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ExercisesFragment mExercisesFragment;
    public static GeneralDAO WorkoutDAO;
    public static GeneralDAO<SelectableExercise, SelectableExercise> ExerciseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set up toolbar for this app
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set up folder if it does not exist to store workouts
        String WORKOUT_DIR = getString(R.string.workout_dir);
        String EXERCISE_DIR = getString(R.string.exercise_dir);
        String firstTimeAccessKey = "firstTime";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        WorkoutDAO = new GeneralDAO(this, WORKOUT_DIR, new WorkoutPreview.WorkoutPreviewFactory());
        ExerciseDAO = new GeneralDAO<>(this, EXERCISE_DIR, new SelectableExercise.SelectableExerciseFactory());
        if(!prefs.getBoolean(firstTimeAccessKey,false)){
            boolean workoutInit = WorkoutDAO.initializeDirectory();
            boolean exerciseInit = ExerciseDAO.initializeDirectory();
            if(workoutInit && exerciseInit) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(firstTimeAccessKey, true);
                editor.apply();
            }
        }
        //Set up view pager for tabs
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == getResources().getInteger(R.integer.create_exercise_code)){
            if(resultCode == RESULT_OK){
                String accessCode = getString(R.string.create_exercise_result);
                SelectableExercise newExercise = (SelectableExercise) data.getSerializableExtra(accessCode);
                ExerciseDAO.saveBackend(newExercise,this);
                mExercisesFragment.addElement(newExercise);
            }
        }
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
                    mExercisesFragment = new ExercisesFragment();
                    return mExercisesFragment;
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
