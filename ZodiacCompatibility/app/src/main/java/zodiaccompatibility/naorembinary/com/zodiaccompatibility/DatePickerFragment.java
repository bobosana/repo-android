package zodiaccompatibility.naorembinary.com.zodiaccompatibility;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by Dylan on 26-01-2016.
 */
public class DatePickerFragment extends android.support.v4.app.DialogFragment {
    public DatePickerFragment(){
    }
    Communicator communicator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator=(Communicator) context;
    }

    interface Communicator{
    public void onDateDone(String sign);
    }

    DatePickerDialog.OnDateSetListener ondateset;
    private int year,month,day;
    public void setCallback(DatePickerDialog.OnDateSetListener ondate){
        ondateset=ondate;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void setArguments(Bundle args){
        super.setArguments(args);
        year=args.getInt("year");
        month=args.getInt("month");
        day=args.getInt("day");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(),ondateset,year,month,day);
    }
}
