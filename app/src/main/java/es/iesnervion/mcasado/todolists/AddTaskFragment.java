package es.iesnervion.mcasado.todolists;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends Fragment{


    final String datepickerFragmentTag = "datepicker_fragment";
    final String timepickerFragmentTag = "timepicker_fragment";
    private TextInputEditText txtDueDate;
    private TextInputEditText txtDueTime;
    public AddTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO Retrieve ViewModel

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_task, container, false);


        //Datepicker
        MaterialDatePicker materialDatePicker;
        MaterialDatePicker.Builder dateBuilder = MaterialDatePicker.Builder.datePicker();
        dateBuilder.setTitleText(R.string.date_picker_title);
        materialDatePicker = dateBuilder.build();
        txtDueDate = v.findViewById(R.id.txtDueDateEdit);
        txtDueDate.setOnClickListener(view -> materialDatePicker.show(getParentFragmentManager(),
                                                                        datepickerFragmentTag));
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> txtDueDate.setText(materialDatePicker.getHeaderText()));

        //TODO assign date to a variable in viewmodel

        //Timepicker
        txtDueTime = v.findViewById(R.id.txtDueTimeEdit);
        txtDueTime.setOnClickListener(
                view -> {
                    MaterialTimePicker.Builder timePickerBuilder= new MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_24H)
                            .setTitleText(R.string.time_picker_title)
                            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK);
                    MaterialTimePicker materialTimePicker = timePickerBuilder.build();
                    materialTimePicker.show(getParentFragmentManager(), timepickerFragmentTag);
                    materialTimePicker.addOnPositiveButtonClickListener(
                            view1 -> {
                                int hour;
                                int min;
                                String strHour, strMin;
                                hour = materialTimePicker.getHour();
                                min = materialTimePicker.getMinute();
                                strHour = Integer.toString(hour);
                                if (strHour.length()==1){
                                    strHour = "0"+strHour;
                                }
                                strMin = Integer.toString(min);
                                if (strMin.length()==1){
                                    strMin = "0"+strMin;
                                }

                                txtDueTime.setText( strHour + ":" + strMin);

                                //TODO assign time to a variable in viewmodel
                            } );
                });


        //Cancel button
        Button btnCancel;
        btnCancel = v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view -> getActivity().finish());

        //Save button
        Button btnSave = v.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(
                view -> {
                    //TODO Implement saving task functionality
               });

        return v;
    }

}