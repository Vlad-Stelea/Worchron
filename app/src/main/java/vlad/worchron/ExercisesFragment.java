package vlad.worchron;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vlad.DAO.GeneralDAO;
import vlad.Previews.ExercisePreview;
import vlad.RecyclerView.Exercise.ExerciseAdapter;
import vlad.backend.Exercises.Exercise;


/**
 *
 */
public class ExercisesFragment extends Fragment {
    private RecyclerView mExercisesView;
    private RecyclerView.Adapter mAdaptor;
    private RecyclerView.LayoutManager mManager;
    private GeneralDAO<Exercise,ExercisePreview> ExerciseDAO;

    public ExercisesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExercisesFragment.
     */
    public static ExercisesFragment newInstance() {
        ExercisesFragment fragment = new ExercisesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        mExercisesView = view.findViewById(R.id.exercises_view);

        //Get list of previews using DAO
        List<ExercisePreview> previews = ExerciseDAO.loadAllPreviews();
        //content should not change layout size of recycler view
        //mExercisesView.setHasFixedSize(true);

        //Linearlayout manager setup
        mManager = new LinearLayoutManager(getContext());
        mExercisesView.setLayoutManager(mManager);

        //Set up adapter
        mAdaptor = new ExerciseAdapter(previews);
        mExercisesView.setAdapter(mAdaptor);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ExerciseDAO = MainActivity.ExerciseDAO;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
