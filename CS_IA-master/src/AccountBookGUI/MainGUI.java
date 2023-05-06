package AccountBookGUI;

import Utils.OSValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;

public class MainGUI {
    private JPanel mainMenuPanel;
    private JButton addNewButton;
    private JButton dashboardButton;
    private JButton historyButton;
    private JButton calenderButton;
    private JButton logoutButton;
    private JLabel welcomeText;
    private JLabel mainMenuBalance;
    private JTable toBeAddedTable;
    private JButton myAccountButton;
    private JButton managerButton;

    public JPanel getPanel() {
        return mainMenuPanel;
    }

    public JLabel getWelcomeText() {
        return welcomeText;
    }

    public JLabel getMainMenuBalance() {
        return mainMenuBalance;
    }

    public JButton getAddNewButton() {
        return addNewButton;
    }

    public JButton getDashboardButton() {
        return dashboardButton;
    }

    public JButton getHistoryButton() {
        return historyButton;
    }

    public JButton getCalenderButton() {
        return calenderButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getManagerButton() {
        return managerButton;
    }

    public JTable getToBeAddedTable() {
        return toBeAddedTable;
    }

    public JButton getMyAccountButton() {
        return myAccountButton;
    }

    public void showMainText() {
        double totalTemp = InterfaceManager.sessionUtil.getTotalIncome() - InterfaceManager.sessionUtil.getTotalExpenditure();
        String total = null;
        String currency = InterfaceManager.sessionUtil.getCurrency();

        if (Objects.equals(currency, "EUR (€)")) {
            currency = "€";
        } else if (Objects.equals(currency, "USD ($)")) {
            currency = "$";
        } else if (Objects.equals(currency, "JPY (¥)")) {
            currency = "¥";
        } else if (Objects.equals(currency, "KRW (₩)")) {
            currency = "₩";
        } else if (Objects.equals(currency, "CNY (¥)")) {
            currency = "¥";
        } else if (Objects.equals(currency, "INR (₹)")) {
            currency = "₹";
        }

        if (totalTemp > 0) {
            total = "+" + currency + totalTemp;
        } else {
            total = "-" + currency + Math.abs(totalTemp);
        }

        getMainMenuBalance().setText(String.format(
                "<html><center>Total Income: </center><center>" + currency
                        + InterfaceManager.sessionUtil.getTotalIncome() + "<center>Total Expenditure: </center><center>" + currency +
                        +InterfaceManager.sessionUtil.getTotalExpenditure() + "<center>Total:</center><center>"
                        + total + "</center></html>"
        ));
    }

    public void showToBeAdded() {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_monthly.data";
        }

        String filePath = path;
        File file = new File(filePath);

        String currency = InterfaceManager.sessionUtil.getCurrency();

        if (Objects.equals(currency, "EUR (€)")) {
            currency = "€";
        } else if (Objects.equals(currency, "USD ($)")) {
            currency = "$";
        } else if (Objects.equals(currency, "JPY (¥)")) {
            currency = "¥";
        } else if (Objects.equals(currency, "KRW (₩)")) {
            currency = "₩";
        } else if (Objects.equals(currency, "CNY (¥)")) {
            currency = "¥";
        } else if (Objects.equals(currency, "INR (₹)")) {
            currency = "₹";
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String[] columnsName = {"Type", "Amount(" + currency + ")", "Date(Y/M/D)"};
            DefaultTableModel model = (DefaultTableModel) toBeAddedTable.getModel();

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            Object[] tableLines = br.lines().toArray();

            model.setColumnIdentifiers(columnsName);

            for (Object tableLine : tableLines) {
                String line = tableLine.toString().trim();
                String[] dataRow = line.split("\\|");
                model.addRow(dataRow);
            }

            // sorting the amount value
            TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(model);
            rowSorter.setComparator(1,
                    new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Double.valueOf(o1).compareTo(Double.valueOf(o2));
                        }
                    });
            toBeAddedTable.setRowSorter(rowSorter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
