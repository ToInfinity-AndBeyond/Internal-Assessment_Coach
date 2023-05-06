package AccountBookGUI;

import Models.HistoryModel;
import Utils.FileUtil;
import Utils.OSValidator;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class MonetaryManagerGUI {
    private JPanel monetaryManagerPanel;
    private JTextField amountText;
    private JComboBox yearComboBox;
    private JComboBox dateComboBox;
    private JComboBox monthComboBox;
    private JComboBox paymentMonthComboBox;
    private JComboBox categoryComboBox;
    private JButton addButton;
    private JButton addAsMonthlyButton;
    private JButton backButton;
    private JTable historyTable;
    private JButton removeButton;
    private JRadioButton incomeRadioButton;
    private JRadioButton expenditureRadioButton;
    private JTextField noteTextField;

    public JPanel getPanel() {
        return monetaryManagerPanel;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getAddAsMonthlyButton() {
        return addAsMonthlyButton;
    }

    public JRadioButton getIncomeRadioButton() {
        return incomeRadioButton;
    }

    public JRadioButton getExpenditureRadioButton() {
        return expenditureRadioButton;
    }

    public JTable getHistoryTable() {
        return historyTable;
    }

    public JComboBox getCategoryComboBox() {
        return categoryComboBox;
    }

    public void setIncomeCategory(JComboBox jComboBox) {
        initCategory(jComboBox);

        try {
            String path = null; //폴더 경로
            if (OSValidator.isMac()) {
                path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_category.data";
            } else if (OSValidator.isWindows()) {
                path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_category.data";
            }

            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String line;

            line = bf.readLine();
            String[] temp = line.split("\\|");

            for (int i = 0; i < temp.length - 1; i++) {
                jComboBox.addItem(temp[i + 1]);
            }

            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void setExpenditureCategory(JComboBox jComboBox) {
        initCategory(jComboBox);

        try {
            String path = null; //폴더 경로
            if (OSValidator.isMac()) {
                path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_category.data";
            } else if (OSValidator.isWindows()) {
                path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_category.data";
            }

            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            String line;

            line = bf.readLine();
            line = bf.readLine();
            String[] temp = line.split("\\|");

            for (int i = 0; i < temp.length - 1; i++) {
                jComboBox.addItem(temp[i + 1]);
            }

            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void initCategory(JComboBox jComboBox) {
        jComboBox.removeAllItems();
        jComboBox.addItem("Not Selected");
    }

    public boolean addNew(boolean addType) {
        double totalIncome = InterfaceManager.sessionUtil.getTotalIncome();
        double totalExpenditure = InterfaceManager.sessionUtil.getTotalExpenditure();

        if (!incomeRadioButton.isSelected() && !expenditureRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select your Type");
        } else if (Objects.equals(amountText.getText(), "")) {
            JOptionPane.showMessageDialog(null, "Please enter the Amount");
        } else if (yearComboBox.getSelectedItem() == "Not Selected") {
            JOptionPane.showMessageDialog(null, "Please select Year");
        } else if (monthComboBox.getSelectedItem() == "Not Selected") {
            JOptionPane.showMessageDialog(null, "Please select Month");
        } else if (dateComboBox.getSelectedItem() == "Not Selected") {
            JOptionPane.showMessageDialog(null, "Please select Date");
        } else if (categoryComboBox.getSelectedItem() == "Not Selected") {
            JOptionPane.showMessageDialog(null, "Please select the Category");
        } else if (paymentMonthComboBox.getSelectedItem() == "") {
            JOptionPane.showMessageDialog(null, "Please select the Payment Method");
        } else if ((monthComboBox.getSelectedItem().toString() == "2"
                || monthComboBox.getSelectedItem().toString() == "4"
                || monthComboBox.getSelectedItem().toString() == "6"
                || monthComboBox.getSelectedItem().toString() == "9"
                || monthComboBox.getSelectedItem().toString() == "11") && dateComboBox.getSelectedItem().toString() == "31") {
            JOptionPane.showMessageDialog(null, "Please select the correct date");
        } else if ((monthComboBox.getSelectedItem().toString() == "2" && dateComboBox.getSelectedItem().toString() == "30")
                || (monthComboBox.getSelectedItem().toString() == "2" && dateComboBox.getSelectedItem().toString() == "29")) {
            JOptionPane.showMessageDialog(null, "Please select the correct date");
        } else {
            JOptionPane.showMessageDialog(null, String.format("Added Completely"));

            String type = null;
            String date;
            String payment_method = null;
            String isMonthly = null;
            String note;

            if (!addType) {
                isMonthly = "X";
            } else {
                isMonthly = "O";
            }

            if (Objects.equals(noteTextField.getText(), "")) {
                note = "ㅤ";
            } else {
                note = noteTextField.getText();
            }

            if (incomeRadioButton.isSelected()) {
                totalIncome = totalIncome + Double.parseDouble(amountText.getText());
                InterfaceManager.sessionUtil.setTotalIncome(totalIncome);
                type = "Income";
                payment_method = (String) paymentMonthComboBox.getSelectedItem();
            } else if (expenditureRadioButton.isSelected()) {
                totalExpenditure = totalExpenditure + Double.parseDouble(amountText.getText());
                InterfaceManager.sessionUtil.setTotalExpenditure(totalExpenditure);
                type = "Expenditure";
                payment_method = (String) paymentMonthComboBox.getSelectedItem();
            }

            date = yearComboBox.getSelectedItem() + "/" + monthComboBox.getSelectedItem() + "/" + dateComboBox.getSelectedItem();

            InterfaceManager.userHistory.add(new HistoryModel(type, Double.parseDouble(amountText.getText()), date, (String) categoryComboBox.getSelectedItem(), payment_method, isMonthly, note));
            FileUtil.saveTotalMonetary();
            FileUtil.saveHistory();

            incomeRadioButton.setSelected(false);
            expenditureRadioButton.setSelected(false);
            amountText.setText("");
            yearComboBox.setSelectedIndex(0);
            monthComboBox.setSelectedIndex(0);
            dateComboBox.setSelectedIndex(0);
            paymentMonthComboBox.setSelectedIndex(0);
            categoryComboBox.setSelectedIndex(0);
            noteTextField.setText("");

            return true;
        }
        return false;
    }

    public boolean addAsMonthlyData() {
        if (addNew(true)) {
            FileUtil.saveMonthlyData();
            return true;
        }
        return false;
    }

    public void addAsMonthly() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedNow = now.format(formatter);

        FileUtil.getMonthlyData();

        for (int i = 0; i < InterfaceManager.monthlyDateList.size(); i++) {
            String[] s = {formattedNow, InterfaceManager.monthlyDateList.get(i)};
            Arrays.sort(s);

            if (formattedNow.equals(s[1]) || formattedNow.equals(InterfaceManager.monthlyDateList.get(i))) {
                String monthlyInfo = InterfaceManager.monthlyList.get(i);
                String[] splitMonthly = monthlyInfo.split("\\|");

                double totalIncome = InterfaceManager.sessionUtil.getTotalIncome();
                double totalExpenditure = InterfaceManager.sessionUtil.getTotalExpenditure();

                String type = splitMonthly[0];
                double amount = Double.parseDouble(splitMonthly[1]);
                String date = splitMonthly[2];
                String category = splitMonthly[3];
                String payment_method = splitMonthly[4];
                String note = splitMonthly[6];

                if (Objects.equals(type, "Income")) {
                    totalIncome = totalIncome + amount;
                    InterfaceManager.sessionUtil.setTotalIncome(totalIncome);
                } else if (Objects.equals(type, "Expenditure")) {
                    totalExpenditure = totalExpenditure + amount;
                    InterfaceManager.sessionUtil.setTotalExpenditure(totalExpenditure);
                }

                InterfaceManager.userHistory.add(new HistoryModel(type, amount, date, category, payment_method, "O", note));
                FileUtil.saveTotalMonetary();
                FileUtil.saveHistory();
                FileUtil.saveNextMonthly(InterfaceManager.monthlyList.get(i));
            }
        }

        InterfaceManager.monthlyList.clear();
        InterfaceManager.monthlyDateList.clear();
    }

}
