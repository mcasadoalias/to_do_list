package es.iesnervion.mcasado.todolists.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import es.iesnervion.mcasado.todolists.DB.Priority;
import es.iesnervion.mcasado.todolists.R;
import es.iesnervion.mcasado.todolists.WhatToShow;
import es.iesnervion.mcasado.todolists.WhatToShowType;
import es.iesnervion.mcasado.todolists.interfaces.MenuChanger;
import es.iesnervion.mcasado.todolists.interfaces.TitleChanger;
import es.iesnervion.mcasado.todolists.viewmodels.AddEditCategoryVM;
import es.iesnervion.mcasado.todolists.viewmodels.AddEditTaskVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditCategoryFragment extends Fragment {


    private AddEditCategoryVM viewModel;

    public AddEditCategoryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddEditCategoryVM.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextInputEditText etxTitle;
        TextInputLayout txlTitle;

        View v = inflater.inflate(R.layout.fragment_add_edit_category, container, false);
        etxTitle = v.findViewById(R.id.cat_etxTitle);
        txlTitle = v.findViewById(R.id.cat_txlTitle);

        //Actually the system restores the views itself, but even so, I'm restoring the views from
        //the saved data (SavedStateHandle) for pedagogical reasons
        //Not using LiveData because we would have an infinite loop (saved values retrieved =>
        //=> views updated => values from views saved => saved values retrieved => views updated...)
        //This is because I save the information in the view when it changes (when the user writes
        //something in the title field)
        if (savedInstanceState!=null) {

            etxTitle.setText(viewModel.getTitle());
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

        //Cancel button
        Button btnCancel;
        btnCancel = v.findViewById(R.id.cat_btnCancel);
        btnCancel.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });

        //Save button
        Button btnSave = v.findViewById(R.id.cat_btnSave);
        btnSave.setOnClickListener(
                view -> {
                    if (areRequiredFieldsEmpty()){
                        displayErrors();
                    } else {
                        viewModel.insertCategory();
                        viewModel.getIdCat().observe(getViewLifecycleOwner(), idCat -> {

                            //TODO: Navigate to the fragment that shows the list of categories

                        });

                    }
                });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                          @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getResources().getString(R.string.add_edit_category_fragment_title);
        ((TitleChanger)requireActivity()).changeToolBarTitle(title);
    }

    private void displayErrors() {
        TextInputLayout txlTitle = getView().findViewById(R.id.cat_txlTitle);
        if (Objects.requireNonNull(txlTitle.getEditText()).getText().toString().trim().isEmpty()){
            txlTitle.setError(getString(R.string.title_required));
        }
    }

    private boolean areRequiredFieldsEmpty() {
        boolean res=false;
        TextInputEditText etxTitle = getView().findViewById(R.id.cat_etxTitle);
        if (Objects.requireNonNull(etxTitle.getText()).toString().trim().isEmpty()){
            res = true;
        }
        return res;
    }
}