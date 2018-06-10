package vlad.worchron;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;
import java.util.Objects;

public class RenameDialog extends android.app.DialogFragment {

    private final static String OBJECT_KEY = "renameObject";

    public static RenameDialog newInstance(Renamable toRename){
        RenameDialog newDialog = new RenameDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(OBJECT_KEY, toRename);
        newDialog.setArguments(bundle);
        return newDialog;
    }

    RenameDialogCallback mCallback;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mCallback = (RenameDialogCallback) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() +
            "must implement RenameDialogCallback");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.rename_exercise_view, null);
        final EditText textBox = view.findViewById(R.id.rename_exercise_new_name);

        builder.setTitle("Please Enter a new name")
                .setPositiveButton("OK", (dialog, id) ->{
                    //name should be changed
                    String newName = textBox.getText().toString();
                    ((Renamable) Objects.requireNonNull(getArguments().get(OBJECT_KEY))).rename(newName);
                    mCallback.sendNewName();
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    //Action canceled
                    mCallback.onCancel();
                    dismiss();
                })
                .setView(view);


        return builder.create();
    }
//<----------------------------Callback stuff------------------------------------------------------------->
    public interface RenameDialogCallback{
        /**
         * Callback that lets the activity know that the object has been renamed
         */
        void sendNewName();

        /**
         * Does whatever it needs to if cancelled
         */
        void onCancel();
    }

    /**
     * Defines an interface for an object that can be renamed
     */
    public interface Renamable extends Serializable {
        /**
         * Sets a new name for the object
         * @param newName the objects new name
         */
        void rename(String newName);
    }
}
