package es.iesnervion.mcasado.todolists;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends Fragment {


    private TextInputEditText txtDueDate;
    private TextInputEditText txtDueTime;
    public AddTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        Spinner spPriority;
//        ArrayAdapter<String> adapter = new ArrayAdapter (requireContext(), android.R.layout.simple_spinner_item,  getResources().getStringArray(R.array.arrPriorities));

        View v = inflater.inflate(R.layout.fragment_add_task, container, false);
//        spPriority = v.findViewById(R.id.spnPriority);
//        spPriority.setAdapter(adapter);

        //Datepicker
        MaterialDatePicker.Builder dateBuilder = MaterialDatePicker.Builder.datePicker();
        dateBuilder.setTitleText(R.string.date_picker_title);
        MaterialDatePicker materialDatePicker = dateBuilder.build();

        txtDueDate = v.findViewById(R.id.txtDueDateEdit);
        //TODO Change fragment_tag for date picker
        txtDueDate.setOnClickListener(view -> materialDatePicker.show(getParentFragmentManager(),"fragment_tagggggggggggg"));

        materialDatePicker.addOnPositiveButtonClickListener(selection -> txtDueDate.setText(materialDatePicker.getHeaderText()));

        //Timepicker
        txtDueTime = v.findViewById(R.id.txtDueTimeEdit);
        MaterialTimePicker timepicker = new MaterialTimePicker.Builder()
                                                                .setTimeFormat(TimeFormat.CLOCK_24H)
                                                                .setTitleText(R.string.time_picker_title)
                                                                .build();
        //TODO Change fragment_tag for time picker
        txtDueTime.setOnClickListener(view -> timepicker.show(getParentFragmentManager(), "fragment_tag"));


        //TODO fix timepicker: it doesn't show the clock the second time you tap on the inputtext

        return v;
    }
}