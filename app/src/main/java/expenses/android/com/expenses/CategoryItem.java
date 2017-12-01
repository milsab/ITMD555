package expenses.android.com.expenses;

/**
 * Created by Alex Wang on 12/1/17.
 */

public class CategoryItem {
    String category;
    int icon;

    // Object Constructor
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
}
