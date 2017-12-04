package expenses.android.com.expenses;

import java.util.ArrayList;

/**
 * Created by Alex Wang on 12/1/17.
 */
/**
 * @author Expense Group
 *
 *         CategoryItem POJO class. Used to store information on a particular category.
 */
public class CategoryItem {
    String category;
    int icon;

    // Object Constructors
    public CategoryItem() {

    }

    public CategoryItem(String category, int icon) {
        this.category = category;
        this.icon = icon;
    }

    // Setter and Getters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * Add the categories to an ArrayList
     *
     * @return none
     */
    public ArrayList addCategories() {
        ArrayList<CategoryItem> list = new ArrayList<>();
        list.add(new CategoryItem("general", R.drawable.ic_home));
        list.add(new CategoryItem("food", R.drawable.ic_food));
        list.add(new CategoryItem("pets", R.drawable.ic_paw));
        list.add(new CategoryItem("drinks", R.drawable.ic_drinks));
        list.add(new CategoryItem("clothing", R.drawable.ic_clothing));
        list.add(new CategoryItem("shopping", R.drawable.ic_merchandise));
        list.add(new CategoryItem("education", R.drawable.ic_education));
        list.add(new CategoryItem("transportation", R.drawable.ic_subway));
        list.add(new CategoryItem("health", R.drawable.ic_health));
        list.add(new CategoryItem("gas", R.drawable.ic_gas));
        list.add(new CategoryItem("hotel", R.drawable.ic_hotel));
        list.add(new CategoryItem("other", R.drawable.ic_other));

        return list;

    }
}
