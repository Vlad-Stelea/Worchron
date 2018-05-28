package vlad.worchron;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ListView;


import java.util.List;

import vlad.DAO.GeneralDAO;
import vlad.DAO.WorkoutDAO;
import vlad.backend.Workout;
import vlad.backend.WorkoutPreview;


/**
 */
public class WorkoutsFragment extends Fragment {
    private GeneralDAO<Workout, WorkoutPreview> WORKOUT_DAO;
    public WorkoutsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment WorkoutsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutsFragment newInstance() {
        WorkoutsFragment fragment = new WorkoutsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);
        ListView workoutsList = (ListView) view.findViewById(R.id.workouts_list);
        List<WorkoutPreview> workoutPreviews = WORKOUT_DAO.loadAllPreviews();
        workoutsList.setAdapter(new WorkoutPreviewAdaptor(getActivity(), 0, workoutPreviews));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        WORKOUT_DAO = new GeneralDAO<Workout, WorkoutPreview>(context,
                getString(R.string.workout_dir));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class WorkoutPreviewAdaptor extends ArrayAdapter{
        private List<WorkoutPreview> mWorkoutPreviews;
        public WorkoutPreviewAdaptor(@NonNull Context context, int resource, List<WorkoutPreview> previews) {
            super(context, resource);
            mWorkoutPreviews = previews;
        }

        /**
         *
         * @param position The position of the item within the adapter's data set of the item whose view we want.
         * @param convertView The old view to reuse, if possible
         * @param parent The parent that this view will eventually be attached to
         *                  This value must never be null.
         * @return
         */
        @Override
        public View getView(int position,
                            View convertView,
                            ViewGroup parent){
            return new WorkoutPreviewView(getContext(), mWorkoutPreviews.get(position));

        }
        @Override
        public int getCount(){
            return mWorkoutPreviews.size();
        }
    }


}
