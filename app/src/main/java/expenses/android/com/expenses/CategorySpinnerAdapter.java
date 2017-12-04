package expenses.android.com.expenses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alex Wang on 12/1/17.
 */
/**
 * @author Expense Group
 *
 *         CategorySpinnerAdapter class extends the ArrayAdapter class. Used for the
 *         custom spinner on category.
 */
public class CategorySpinnerAdapter extends ArrayAdapter<CategoryItem> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<CategoryItem> categoryItems;
    private final int mResource;

    public CategorySpinnerAdapter(@NonNull Context context, int resource,
                                  @NonNull List<CategoryItem> objects) {
        super(context, resource, 0, objects);

        mInflater = LayoutInflater.from(context);
        mContext = context;
        mResource = resource;
        categoryItems = objects;
    }

    /**
     * Create a view  in the spinner
     *
     * @return none
     */
    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    /**
     * Get the view for a particular view in the spinner
     *
     * @return none
     */
    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);

        ImageView icons = (ImageView) view.findViewById(R.id.ic_category);
        TextView categories = (TextView) view.findViewById(R.id.text_category);

        CategoryItem categoryItem = categoryItems.get(position);

        icons.setImageResource(categoryItem.getIcon());
        categories.setText(categoryItem.getCategory());

        return view;
    }


}
