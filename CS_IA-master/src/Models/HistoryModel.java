package Models;

import AccountBookGUI.InterfaceManager;
import Utils.FileUtil;
import Utils.OSValidator;
import Utils.TableRowFilter;

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

public class HistoryModel extends MonetaryModel{
    public HistoryModel() {
    }

    // Inheritance
    public HistoryModel(String type, double amount, String date, String category, String payment_method, String add_type, String note) {
        super(type, amount, date, category, payment_method, add_type, note);
    }

    // Polymorphism
    public void showHistory(JTable t, String s) {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
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
            String[] columnsName = {"Type", "Amount(" + currency + ")", "Date(Y/M/D)", "Category", "Payment Method", "Monthly", "Note"};
            DefaultTableModel model = (DefaultTableModel) t.getModel();
            model.setColumnIdentifiers(columnsName);

            Object[] tableLines = br.lines().toArray();

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            for (int i = 0; i < tableLines.length; i++) {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("\\|");
                model.addRow(dataRow);
            }

            // sorting the amount value
            TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(model);
            rowSorter.setRowFilter(new TableRowFilter(s));
            rowSorter.setComparator(1,
                    new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return Double.valueOf(o1).compareTo(Double.valueOf(o2));
                        }
                    });
            t.setRowSorter(rowSorter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Polymorphism
    public void showHistory(JTable t) {
        String path = null;
        if (OSValidator.isMac()) {
            path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
        } else if (OSValidator.isWindows()) {
            path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
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
            String[] columnsName = {"Type", "Amount(" + currency + ")", "Date(Y/M/D)", "Category", "Payment Method", "Monthly", "Note"};
            DefaultTableModel model = (DefaultTableModel) t.getModel();

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            Object[] tableLines = br.lines().toArray();

            model.setColumnIdentifiers(columnsName);

            for (int i = 0; i < tableLines.length; i++) {
                String line = tableLines[i].toString().trim();
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
            t.setRowSorter(rowSorter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initTable(JTable t) {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
    }

    // TODO: Add Filtering. Ex) Income or Expenditure can't be changed
    public void saveHistory(JTable t) {
        double totalIncome = InterfaceManager.sessionUtil.getTotalIncome();
        double totalExpenditure = InterfaceManager.sessionUtil.getTotalExpenditure();
        String selectedHistory = InterfaceManager.selectedHistory;
        String modifiedHistory = "";

        int row = t.getSelectedRow();

        for (int i = 0; i < t.getColumnCount(); i++) {
            if (i != t.getColumnCount() - 1) {
                modifiedHistory += t.getValueAt(row, i).toString() + "|";
            } else {
                modifiedHistory += t.getValueAt(row, i).toString();
            }
        }

        String[] str = selectedHistory.split("\\|");
        String[] str2 = modifiedHistory.split("\\|");

        if (!Objects.equals(str2[0].toLowerCase(), "income") && !Objects.equals(str2[0].toLowerCase(), "expenditure")) {
            JOptionPane.showMessageDialog(null, "Type Cannot be \"" + str2[0] + "\"");
        } else if (t.getSelectedRowCount() == 1) {
            row = t.convertRowIndexToModel(row);

            int columnAmount = 1;
            int columnType = 0;
            double selectedAmount = Double.parseDouble(str[1]);
            double modifiedAmount = Double.parseDouble(str2[1]);

            if (Objects.equals(t.getModel().getValueAt(row, columnType).toString(), "Income")) {
                if (!Objects.equals(str[1], str2[1])) {
                    totalIncome = totalIncome - selectedAmount;
                    totalIncome = totalIncome + modifiedAmount;
                    InterfaceManager.sessionUtil.setTotalIncome(totalIncome);
                }
            } else if (Objects.equals(t.getModel().getValueAt(row, columnType).toString(), "Expenditure")) {
                if (!Objects.equals(str[1], str2[1])) {
                    totalExpenditure = totalExpenditure - selectedAmount;
                    totalExpenditure = totalExpenditure + modifiedAmount;
                    InterfaceManager.sessionUtil.setTotalExpenditure(totalExpenditure);
                }
            }

            FileUtil.saveTotalMonetary();
//            InterfaceManager.userHistory.remove(row);

            JOptionPane.showMessageDialog(null, "Your Change has been Saved!");
            FileUtil.saveModifiedHistory(selectedHistory, modifiedHistory);
            InterfaceManager.selectedHistory = modifiedHistory;
        } else {
            if (t.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Table is Empty!");
            } else {
                JOptionPane.showMessageDialog(null, "Please Select Single Row For Modify!");
            }
        }
    }

    public void removeHistory(JTable t) {
        double totalIncome = InterfaceManager.sessionUtil.getTotalIncome();
        double totalExpenditure = InterfaceManager.sessionUtil.getTotalExpenditure();

        if (t.getSelectedRowCount() == 1) {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to Delete?");

            if (result == JOptionPane.CLOSED_OPTION) {

            } else if (result == JOptionPane.YES_OPTION) {
                int row = t.getSelectedRow();
                row = t.convertRowIndexToModel(row);
                int columnAmount = 1;
                int columnType = 0;
                double amount = Double.parseDouble(t.getModel().getValueAt(row, columnAmount).toString());

                if (Objects.equals(t.getModel().getValueAt(row, columnType).toString(), "Income")) {
                    totalIncome = totalIncome - amount;
                    InterfaceManager.sessionUtil.setTotalIncome(totalIncome);
                } else if (Objects.equals(t.getModel().getValueAt(row, columnType).toString(), "Expenditure")) {
                    totalExpenditure = totalExpenditure - amount;
                    InterfaceManager.sessionUtil.setTotalExpenditure(totalExpenditure);
                }

                FileUtil.saveTotalMonetary();
                InterfaceManager.userHistory.remove(row);

                JOptionPane.showMessageDialog(null, "Selected Row has been Removed!");
                FileUtil.removeHistory(t, row);
            }
        } else {
            if (t.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Table is Empty!");
            } else {
                JOptionPane.showMessageDialog(null, "Please Select Single Row For Modify!");
            }
        }
    }
}
