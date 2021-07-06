package es.iesnervion.mcasado.todolists.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import es.iesnervion.mcasado.todolists.DB.Category;
import es.iesnervion.mcasado.todolists.DB.Task;
import es.iesnervion.mcasado.todolists.R;
import es.iesnervion.mcasado.todolists.viewmodels.MainViewModel;
import es.iesnervion.mcasado.todolists.viewmodels.TasksListVM;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<Category> categories;
    private Context context;
    private MainViewModel viewModel;

    public CategoriesAdapter(Context context, List<Category> categories) {
        this.categories = categories;
        this.context = context;
        this.viewModel = new ViewModelProvider((ViewModelStoreOwner) context).
                                get(MainViewModel.class);
    }

    public void setCategories (List<Category> categories){
        this.categories = categories;
    }

    public CategoriesAdapter() {
        this.categories = null;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        if (categories!=null){
            Category category = categories.get(position);

            //Category Title
            holder.getTxvCatTitle().setText(category.getTitle());

        }
    }

    @Override
    public int getItemCount() {
        int size=0;
        if (categories!=null) {
            size = categories.size();
        }
        return size;
    }

    /**
     * ViewHolder Class
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final MaterialCardView card;
        private final TextView txvCatTitle;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.card = itemView.findViewById(R.id.cat_card);
            this.txvCatTitle = itemView.findViewById(R.id.txvCatTitle);
        }

        public MaterialCardView getCard() {
            return card;
        }

        public TextView getTxvCatTitle() {
            return txvCatTitle;
        }
    }
}