package Models;

public class UserModel {
    private String username;
    private String id;
    private String password;
    private double totalExpenditure;
    private double totalIncome;
    private String currency;

    public UserModel() {
    }

    public UserModel(String username, String id, String password, double totalIncome, double totalExpenditure, String currency) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.totalIncome = totalIncome;
        this.totalExpenditure = totalExpenditure;
        this.currency = currency;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(double totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String age) {
        this.currency = age;
    }
}
