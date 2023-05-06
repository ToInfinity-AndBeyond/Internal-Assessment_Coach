package Models;

public class MonetaryModel {
    private String type;
    private double amount;
    private String date;
    private String category;
    private String payment_method;
    private String add_type;
    private String note;
 
    public MonetaryModel() {
    }

    public MonetaryModel(String type, double amount, String date, String category, String payment_method, String add_type, String note) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.type = type;
        this.payment_method = payment_method;
        this.add_type = add_type;
        this.note = note;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getAdd_type() {
        return add_type;
    }

    public void setAdd_type(String add_type) {
        this.add_type = add_type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
