package es.iesnervion.mcasado.todolists.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.util.Objects;

import es.iesnervion.mcasado.todolists.WhatToShow;
import es.iesnervion.mcasado.todolists.WhatToShowType;
import es.iesnervion.mcasado.todolists.interfaces.TitleChanger;
import es.iesnervion.mcasado.todolists.viewmodels.AddEditTaskVM;
import es.iesnervion.mcasado.todolists.DB.Priority;
import es.iesnervion.mcasado.todolists.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditTaskFragment extends Fragment{

    AddEditTaskVM viewModel;

    private final String datepickerFragmentTag = "datepicker_fragment";
    private final String timepickerFragmentTag = "timepicker_fragment";


    public AddEditTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this) .get(AddEditTaskVM.class);
        if (getArguments()!=null) {
            viewModel.saveCatId(AddEditTaskFragmentArgs.fromBundle(getArguments()).getCatId());
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                          @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getResources().getString(R.string.add_edit_task_fragment_title);
        ((TitleChanger)requireActivity()).changeToolBarTitle(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextInputEditText etxTitle;
        TextInputLayout txlTitle;
        TextInputEditText etxDesc;
        RadioGroup rgPriority;
        TextInputEditText txtDueDate;
        TextInputEditText txtDueTime;


        View v = inflater.inflate(R.layout.fragment_add_edit_task, container, false);
        etxTitle = v.findViewById(R.id.etxTitle);
        txlTitle = v.findViewById(R.id.txlTitle);
        etxDesc = v.findViewById(R.id.etxDescription);
        rgPriority = v.findViewById(R.id.rgPriority);
        txtDueDate = v.findViewById(R.id.txtDueDateEdit);
        txtDueTime = v.findViewById(R.id.txtDueTimeEdit);

        //Actually the system restores the views itself, but even so, I'm restoring the views from
        //the saved data (SavedStateHandle) for pedagogical reasons
        //Not using LiveData because we would have an infinite loop (saved values retrieved =>
        //=> views updated => values from views saved => saved values retrieved => views updated...)
        //This is because I save the information in the views when it changes (when the user writes
        //something in the date field, when the user checks a radio button, etc...)
        if (savedInstanceState!=null){
            Priority priority;
            etxTitle.setText(viewModel.getTitle());
            etxDesc.setText(viewModel.getDescription());
            priority = viewModel.getPriority();
            if (priority!=null) {
                switch (priority) {
                    case LOW:
                        rgPriority.check(R.id.rbLow);
                        break;
                    case HIGH:
                        rgPriority.check(R.id.rbHigh);
                        break;
                    case NORMAL:
                        rgPriority.check(R.id.rbNormal);
                        break;
                }
            }
            txtDueDate.setText(viewModel.getDueDateAsString());
            txtDueTime.setText(viewModel.getDueTime());
        }

        //Title
        // To save everytime the user writes a character, but maybe it's better to save when
        // the focus is not on the field anymore
        etxTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                txlTitle.setError(null);
                viewModel.saveTitle(editable.toString());
            }
        });

        //Description
        // To save everytime the user writes a character, but maybe it's better to save when
        // the focus is not on the field anymore
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

        txtDueDate.setOnClickListener(view -> materialDatePicker.show(getParentFragmentManager(),
                                                                        datepickerFragmentTag));
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    txtDueDate.setText(materialDatePicker.getHeaderText());
                    //It's better to save the date in a format that doesn't depend on locale nor API
                    viewModel.saveDueDate((Long)selection);
                    //We also save it as String, just in case we need it to recover after the system
                    //kills the app. That way, we will just fill the date field with this String
                    viewModel.saveDueDateAsString (materialDatePicker.getHeaderText());
                });

        //Timepicker
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
        btnCancel.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });

        //Save button
        Button btnSave = v.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(
                view -> {
                    if (areRequiredFieldsEmpty()){
                        displayErrors();
                    } else {
                        viewModel.insertTask();
                        //TODO Check if the task was actually inserted

                        NavDirections action = AddEditTaskFragmentDirections
                                .actionAddEditTaskFragmentToTasksListFragment(true,
                                        new WhatToShow(WhatToShowType.CAT, viewModel.getCatId()));
                        Navigation.findNavController(view).navigate(action);
                    }
               });
        //TODO: Add a spinner to the form to be populated with the category for the task??

        return v;
    }

    private void displayErrors() {
        TextInputLayout txlTitle = getView().findViewById(R.id.txlTitle);
        if (Objects.requireNonNull(txlTitle.getEditText()).getText().toString().trim().isEmpty()){
            txlTitle.setError(getString(R.string.title_required));
        }
    }

    private boolean areRequiredFieldsEmpty() {
        boolean res=false;
        TextInputEditText etxTitle = getView().findViewById(R.id.etxTitle);
        if (Objects.requireNonNull(etxTitle.getText()).toString().trim().isEmpty()){
            res = true;
        }
        return res;
    }
}