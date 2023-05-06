package Utils;

import AccountBookGUI.InterfaceManager;

public class SessionUtil {
    private String username = "";
    private String id = "";
    private String password = "";
    private double totalExpenditure = 0.0;
    private double totalIncome = 0.0;
    private String currency = "";
    private boolean isLogin;

    public SessionUtil() {
    }

    public void login(String username, String id, String password, double totalIncome, double totalExpenditure, String currency) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.totalIncome = totalIncome;
        this.totalExpenditure = totalExpenditure;
        this.currency = currency;
        isLogin = true;
    }

    public void logout() {
        username = "";
        id = "";
        password = "";
        totalIncome = 0;
        totalExpenditure = 0;
        currency = "";
        isLogin = false;
        InterfaceManager.userHistory.clear();
        InterfaceManager.userList.clear();
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
        this.totalIncome= totalIncome;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
