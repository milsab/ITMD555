package expenses.android.com.expenses.domain;

/**
 * @author Expense Group
 *
 *         Expense POJO class. Used to store information on a particular expense.
 */
public class Expense {

    private int id;
    private String title;
    private String description;
    private int amount;
    private int date;
    private String category;

    public Expense(int id, String title,String description,int amount, int date, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", amount='" + amount + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
