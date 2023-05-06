package AccountBookGUI;

import Utils.FileUtil;
import Utils.OSValidator;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class ManagerGUI {
    private JPanel managerPanel;
    private JTextField newCategoryTextField;
    private JButton newCategoryButton;
    private JRadioButton incomeRadioButton;
    private JRadioButton expenditureRadioButton;
    private JComboBox incomeCategoryComboBox;
    private JButton incomeDeleteButton;
    private JButton expenditureDeleteButton;
    private JComboBox expenditureCategoryComboBox;
    private JButton backButton;
    private JTable monthlyTable;
    private JButton monthlySaveButton;
    private JButton monthlyDeleteButton;

    public JPanel getPanel() {
        return managerPanel;
    }

    public JButton getNewCategoryButton() {
        return newCategoryButton;
    }

    public JRadioButton getIncomeRadioButton() {
        return incomeRadioButton;
    }

    public JRadioButton getExpenditureRadioButton() {
        return expenditureRadioButton;
    }

    public JComboBox getIncomeCategoryComboBox() {
        return incomeCategoryComboBox;
    }

    public JComboBox getExpenditureCategoryComboBox() {
        return expenditureCategoryComboBox;
    }

    public JButton getIncomeDeleteButton() {
        return incomeDeleteButton;
    }

    public JButton getExpenditureDeleteButton() {
        return expenditureDeleteButton;
    }

    public JTable getMonthlyTable() {
        return monthlyTable;
    }

    public JButton getMonthlySaveButton() {
        return monthlySaveButton;
    }

    public JButton getMonthlyDeleteButton() {
        return monthlyDeleteButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void addNewCategory() {
        String type = null;
        if (incomeRadioButton.isSelected()) {
            type = "income";
        } else if (expenditureRadioButton.isSelected()) {
            type = "expenditure";
        }
        if (Objects.equals(newCategoryTextField.getText(), "")) {
            JOptionPane.showMessageDialog(null, "Please enter new Category!");
        } else if (!incomeRadioButton.isSelected() && !expenditureRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select the Monetary Type!");
        } else {
            FileUtil.saveCategory(newCategoryTextField.getText().toString(), type);
            JOptionPane.showMessageDialog(null, "New Category added!");
            newCategoryTextField.setText("");
            incomeRadioButton.setSelected(false);
            expenditureRadioButton.setSelected(false);
        }
    }

    public void deleteIncomeCategory() {
        String selectedCategory = incomeCategoryComboBox.getSelectedItem().toString();
        String type = "income";

        if (incomeCategoryComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select the Category!");
        } else {
            FileUtil.deleteCategory(selectedCategory, type);
            JOptionPane.showMessageDialog(null, "Selected Category Deleted!");
            incomeCategoryComboBox.setSelectedIndex(0);
        }
    }

    public void deleteExpenditureCategory() {
        String selectedCategory = Objects.requireNonNull(expenditureCategoryComboBox.getSelectedItem()).toString();
        String type = "expenditure";

        if (expenditureCategoryComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select the Category!");
        } else {
            FileUtil.deleteCategory(selectedCategory, type);
            JOptionPane.showMessageDialog(null, "Selected Category Deleted!");
            expenditureCategoryComboBox.setSelectedIndex(0);
        }
    }

    public void showMonthlyData(JTable t) {
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
            String[] columnsName = {"Type", "Amount(" + currency + ")", "Date(Y/M/D)", "Category", "Payment Method", "Monthly", "Note"};
            DefaultTableModel model = (DefaultTableModel) t.getModel();

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
            t.setRowSorter(rowSorter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMonthlyData() {
        if (monthlyTable.getSelectedRowCount() == 1) {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to Delete?");

            if (result == JOptionPane.CLOSED_OPTION) {

            } else if (result == JOptionPane.YES_OPTION) {
                int row = monthlyTable.getSelectedRow();
                row = monthlyTable.convertRowIndexToModel(row);

                JOptionPane.showMessageDialog(null, "Selected Row has been Removed!");
                FileUtil.deleteMonthly(monthlyTable, row);
            }
        } else {
            if (monthlyTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Table is Empty!");
            } else {
                JOptionPane.showMessageDialog(null, "Please Select Single Row For Modify!");
            }
        }
    }

    // TODO: Add Filtering. Ex) Income or Expenditure can't be changed
    public void saveMonthly() {
        String selectedHistory = InterfaceManager.selectedHistory;
        String modifiedHistory = "";

        int row = monthlyTable.getSelectedRow();

        for (int i = 0; i < monthlyTable.getColumnCount(); i++) {
            if (i != monthlyTable.getColumnCount() - 1) {
                modifiedHistory += monthlyTable.getValueAt(row, i).toString() + "|";
            } else {
                modifiedHistory += monthlyTable.getValueAt(row, i).toString();
            }
        }

        String[] str = selectedHistory.split("\\|");
        String[] str2 = modifiedHistory.split("\\|");

        if (!Objects.equals(str2[0].toLowerCase(), "income") && !Objects.equals(str2[0].toLowerCase(), "expenditure")) {
            JOptionPane.showMessageDialog(null, "Type Cannot be \"" + str2[0] + "\"");
        } else if (monthlyTable.getSelectedRowCount() == 1) {
//            InterfaceManager.userHistory.remove(row);

            JOptionPane.showMessageDialog(null, "Your Change has been Saved!");
            FileUtil.saveModifiedMonthly(selectedHistory, modifiedHistory);
            InterfaceManager.selectedHistory = modifiedHistory;
        } else {
            if (monthlyTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Table is Empty!");
            } else {
                JOptionPane.showMessageDialog(null, "Please Select Single Row For Modify!");
            }
        }
    }

}
