package es.iesnervion.mcasado.todolists;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

import es.iesnervion.mcasado.todolists.DB.Priority;
import es.iesnervion.mcasado.todolists.DB.Task;
import es.iesnervion.mcasado.todolists.DB.TodoDB;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends Fragment{

    TodoViewModel viewModel;

    private final String datepickerFragmentTag = "datepicker_fragment";
    private final String timepickerFragmentTag = "timepicker_fragment";
    private TextInputEditText txtDueDate;
    private TextInputEditText txtDueTime;
    public AddTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()) .get(TodoViewModel.class);
        viewModel.clearTaskData ();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_task, container, false);

        //Title
        TextInputEditText etxTitle = v.findViewById(R.id.etxTitle);
        etxTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.saveTitle(editable.toString());
            }
        });

        //Description
        TextInputEditText etxDesc = v.findViewById(R.id.etxDescription);
        etxDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.saveDescription(editable.toString());
            }
        });

        //Priority
        RadioGroup rgPriority = v.findViewById(R.id.rgPriority);
        rgPriority.setOnCheckedChangeListener(
                (radioGroup, id) ->
                {
                   Priority priority = Priority.UNDEFINED;
                   /*Resources ids will not be final from Gradle 5.0
                   * So, it's not recommended to use them on switch cases
                   * Android Studio throws a warning message*/
                   /*switch (id) {
                       case R.id.rbHigh:
                           priority = Priority.HIGH;
                           break;
                       case R.id.rbNormal:
                           priority = Priority.NORMAL;
                           break;
                       case R.id.rbLow:
                           priority = Priority.LOW;
                           break;
                       default:
                           break;
                   }*/
                   if (id == R.id.rbHigh){
                       priority = Priority.HIGH;
                   } else if (id == R.id.rbNormal){
                       priority = Priority.NORMAL;
                   } else if (id == R.id.rbLow){
                       priority = Priority.LOW;
                   }
                   viewModel.savePriority(priority);
                });

        //Datepicker
        MaterialDatePicker<?> materialDatePicker;
        MaterialDatePicker.Builder<?> dateBuilder = MaterialDatePicker.Builder.datePicker();
        dateBuilder.setTitleText(R.string.date_picker_title);
        materialDatePicker = dateBuilder.build();
        txtDueDate = v.findViewById(R.id.txtDueDateEdit);
        txtDueDate.setOnClickListener(view -> materialDatePicker.show(getParentFragmentManager(),
                                                                        datepickerFragmentTag));
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    txtDueDate.setText(materialDatePicker.getHeaderText());
                    //It's better to save the date in a format that doesnt depends on locale nor API
                    viewModel.saveDueDate((Long)selection);
                });

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
                                int hour= materialTimePicker.getHour();
                                int min= materialTimePicker.getMinute();
                                LocalTime time = LocalTime.of(hour,min);
                                txtDueTime.setText(time.toString());
                                viewModel.saveDueTime(time.toString());
                            });
                });

        //Cancel button
        Button btnCancel;
        btnCancel = v.findViewById(R.id.btnCancel);
        //TODO cancel button
        //btnCancel.setOnClickListener()

        //Save button
        Button btnSave = v.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(
                view -> {

                    // TODO Warn about required fields
                    viewModel.insertTask();


               });

        //TODO: Add a spinner to the form to be populated with the group (list) for the task

        return v;
    }
}