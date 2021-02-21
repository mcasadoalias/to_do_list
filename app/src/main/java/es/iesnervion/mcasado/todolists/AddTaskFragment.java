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
                    //LocalDate fff = LocalDate.parse( materialDatePicker.getHeaderText());
                    viewModel.saveDueDate(materialDatePicker.getHeaderText());
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
                                int hour;
                                int min;
                                hour = materialTimePicker.getHour();
                                min = materialTimePicker.getMinute();
                                LocalTime time = LocalTime.of(hour,min);
                                txtDueTime.setText(time.toString());
                                //TODO Save time in the viewmodel
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
                    //TODO Implement saving task functionality
                    LocalDate date = LocalDate.now();
                    LocalTime time = LocalTime.now();
                    Task task = new Task("Prueba","descripcion ", Priority.LOW,
                                          date, time);
                    TodoDB.getTodoDB(getContext()).taskDAO().insertTask(task);
               });

        //TODO: Add a spinner to the form to be populated with the group (list) for the task

        return v;
    }
}