package AccountBookGUI.Dashboard;

import AccountBookGUI.InterfaceManager;
import Utils.FileUtil;
import Utils.OSValidator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.*;
import java.util.*;

public class CategoryGUI {
    private JPanel panelPieChart;
    private JRadioButton incomeRadioButton;
    private JRadioButton expenditureRadioButton;
    private JPanel categoryPanel;
    private JComboBox yearComboBox;
    private JComboBox monthComboBox;
    private JComboBox dateComboBox;
    private JButton searchButton;
    private JButton backButton;
    private JTable categoryTable;
    private JButton paymentMethodButton;
    private JButton incomeExpButton;

    public JPanel getPanel() {
        return categoryPanel;
    }

    public JRadioButton getIncomeRadioButton() {
        return incomeRadioButton;
    }

    public JRadioButton getExpenditureRadioButton() {
        return expenditureRadioButton;
    }

    public JComboBox getYearComboBox() {
        return yearComboBox;
    }

    public JComboBox getMonthComboBox() {
        return monthComboBox;
    }

    public JComboBox getDateComboBox() {
        return dateComboBox;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTable getCategoryTable() {
        return categoryTable;
    }

    public JButton getPaymentMethodButton() {
        return paymentMethodButton;
    }

    public JButton getIncomeExpButton() {
        return incomeExpButton;
    }

    public void showPieChart() {
        String initType = null;
        if (incomeRadioButton.isSelected()) {
            initType = "income";
        } else {
            initType = "expenditure";
        }

        FileUtil.readCategoryFile(initType);

        double totalIncome = 0;
        double totalExpenditure = 0;

        String selectedYear = yearComboBox.getSelectedItem().toString();
        String selectedMonth = monthComboBox.getSelectedItem().toString();
        String selectedDate = dateComboBox.getSelectedItem().toString();

        if (yearComboBox.getSelectedIndex() == 0) {
            monthComboBox.setSelectedIndex(0);
            dateComboBox.setSelectedIndex(0);
        }

        if ((monthComboBox.getSelectedItem().toString() == "2"
                || monthComboBox.getSelectedItem().toString() == "4"
                || monthComboBox.getSelectedItem().toString() == "6"
                || monthComboBox.getSelectedItem().toString() == "9"
                || monthComboBox.getSelectedItem().toString() == "11") && dateComboBox.getSelectedItem().toString() == "31") {
            JOptionPane.showMessageDialog(null, "Please select the correct date");
            return;
        } else if ((monthComboBox.getSelectedItem().toString() == "2" && dateComboBox.getSelectedItem().toString() == "30")
                || (monthComboBox.getSelectedItem().toString() == "2" && dateComboBox.getSelectedItem().toString() == "29")) {
            JOptionPane.showMessageDialog(null, "Please select the correct date");
            return;
        }

        if (yearComboBox.getSelectedIndex() == 0) {
            FileUtil.readCategoryFile(initType);
            try {
                String path = null;
                if (OSValidator.isMac()) {
                    path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
                } else if (OSValidator.isWindows()) {
                    path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
                }

                File file = new File(path);
                FileReader fr = new FileReader(file);
                BufferedReader bf = new BufferedReader(fr);
                String line = "";
                while ((line = bf.readLine()) != null) {
                    String[] temp = line.split("\\|");
                    String type = temp[0];
                    double amount = Double.parseDouble(temp[1]);
                    String category = temp[3];

                    if (Objects.equals(type.toLowerCase(), "income") && incomeRadioButton.isSelected()) {
                        totalIncome = totalIncome + amount;
                        int val = InterfaceManager.number.get(category);
                        val = val + 1;
                        InterfaceManager.number.put(category, val);

                        double val2 = InterfaceManager.amount.get(category);
                        val2 = val2 + amount;
                        InterfaceManager.amount.put(category, val2);
                    } else if (Objects.equals(type.toLowerCase(), "expenditure") && expenditureRadioButton.isSelected()) {
                        totalExpenditure = totalExpenditure + amount;
                        int val = InterfaceManager.number.get(category);
                        val = val + 1;
                        InterfaceManager.number.put(category, val);

                        double val2 = InterfaceManager.amount.get(category);
                        val2 = val2 + amount;
                        InterfaceManager.amount.put(category, val2);
                    }
                }
                bf.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println(e);
            }
        } else if (yearComboBox.getSelectedIndex() == 0 && monthComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select the Year!");
        } else if (yearComboBox.getSelectedIndex() == 0 && monthComboBox.getSelectedIndex() != 0) {
            JOptionPane.showMessageDialog(null, "Please select the Year!");
        } else if (yearComboBox.getSelectedIndex() != 0 && monthComboBox.getSelectedIndex() == 0 && dateComboBox.getSelectedIndex() != 0) {
            JOptionPane.showMessageDialog(null, "Please select the Month!");
        } else if (yearComboBox.getSelectedIndex() == 0 && monthComboBox.getSelectedIndex() == 0 && dateComboBox.getSelectedIndex() != 0) {
            JOptionPane.showMessageDialog(null, "Please select the Year and Month!");
        } else {
            if (monthComboBox.getSelectedIndex() == 0) {
                FileUtil.readCategoryFile(initType);
                try {
                    String path = null;
                    if (OSValidator.isMac()) {
                        path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
                    } else if (OSValidator.isWindows()) {
                        path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
                    }

                    FileUtil.readCategoryFile(initType);
                    File file = new File(path);
                    FileReader fr = new FileReader(file);
                    BufferedReader bf = new BufferedReader(fr);
                    String line = "";
                    while ((line = bf.readLine()) != null) {
                        String[] temp = line.split("\\|");
                        String type = temp[0];
                        double amount = Double.parseDouble(temp[1]);
                        String date = temp[2];
                        String category = temp[3];

                        String[] divDateData = date.split("/");
                        String divYear = divDateData[0];

                        if (Objects.equals(type, "Income") && Objects.equals(divYear, selectedYear)) {
                            totalIncome = totalIncome + amount;
                            int val = InterfaceManager.number.get(category);
                            val = val + 1;
                            InterfaceManager.number.put(category, val);

                            double val2 = InterfaceManager.amount.get(category);
                            val2 = val2 + amount;
                            InterfaceManager.amount.put(category, val2);
                        } else if (Objects.equals(type, "Expenditure") && Objects.equals(divYear, selectedYear)) {
                            totalExpenditure = totalExpenditure + amount;
                            int val = InterfaceManager.number.get(category);
                            val = val + 1;
                            InterfaceManager.number.put(category, val);

                            double val2 = InterfaceManager.amount.get(category);
                            val2 = val2 + amount;
                            InterfaceManager.amount.put(category, val2);
                        }
                    }
                    bf.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else if (dateComboBox.getSelectedIndex() == 0) {
                FileUtil.readCategoryFile(initType);
                try {
                    String path = null;
                    if (OSValidator.isMac()) {
                        path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
                    } else if (OSValidator.isWindows()) {
                        path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
                    }

                    FileUtil.readCategoryFile(initType);
                    File file = new File(path);
                    FileReader fr = new FileReader(file);
                    BufferedReader bf = new BufferedReader(fr);
                    String line = "";
                    while ((line = bf.readLine()) != null) {
                        String[] temp = line.split("\\|");
                        String type = temp[0];
                        double amount = Double.parseDouble(temp[1]);
                        String date = temp[2];
                        String category = temp[3];

                        String[] divDateData = date.split("/");
                        String divYear = divDateData[0];
                        String divMonth = divDateData[1];

                        if (Objects.equals(type, "Income") && Objects.equals(divYear, selectedYear) && Objects.equals(divMonth, selectedMonth)) {
                            totalIncome = totalIncome + amount;
                            int val = InterfaceManager.number.get(category);
                            val = val + 1;
                            InterfaceManager.number.put(category, val);

                            double val2 = InterfaceManager.amount.get(category);
                            val2 = val2 + amount;
                            InterfaceManager.amount.put(category, val2);
                        } else if (Objects.equals(type, "Expenditure") && Objects.equals(divYear, selectedYear) && Objects.equals(divMonth, selectedMonth)) {
                            totalExpenditure = totalExpenditure + amount;
                            int val = InterfaceManager.number.get(category);
                            val = val + 1;
                            InterfaceManager.number.put(category, val);

                            double val2 = InterfaceManager.amount.get(category);
                            val2 = val2 + amount;
                            InterfaceManager.amount.put(category, val2);
                        }
                    }
                    bf.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else {
                FileUtil.readCategoryFile(initType);
                try {
                    String path = null;
                    if (OSValidator.isMac()) {
                        path = "/Users/" + System.getProperty("user.name") + "/Library/Application Support/Account Book/" + InterfaceManager.sessionUtil.getId() + "_history.data";
                    } else if (OSValidator.isWindows()) {
                        path = "C:\\Account Book\\" + InterfaceManager.sessionUtil.getId() + "_history.data";
                    }

                    File file = new File(path);
                    FileReader fr = new FileReader(file);
                    BufferedReader bf = new BufferedReader(fr);
                    String line = "";
                    while ((line = bf.readLine()) != null) {
                        String[] temp = line.split("\\|");
                        String type = temp[0];
                        double amount = Double.parseDouble(temp[1]);
                        String date = temp[2];
                        String category = temp[3];

                        String[] divDateData = date.split("/");
                        String divYear = divDateData[0];
                        String divMonth = divDateData[1];
                        String divDate = divDateData[2];

                        if (Objects.equals(type, "Income") && Objects.equals(divYear, selectedYear) && Objects.equals(divMonth, selectedMonth) && Objects.equals(divDate, selectedDate)) {
                            totalIncome = totalIncome + amount;
                            int val = InterfaceManager.number.get(category);
                            val = val + 1;
                            InterfaceManager.number.put(category, val);

                            double val2 = InterfaceManager.amount.get(category);
                            val2 = val2 + amount;
                            InterfaceManager.amount.put(category, val2);
                        } else if (Objects.equals(type, "Expenditure") && Objects.equals(divYear, selectedYear) && Objects.equals(divMonth, selectedMonth) && Objects.equals(divDate, selectedDate)) {
                            totalExpenditure = totalExpenditure + amount;
                            int val = InterfaceManager.number.get(category);
                            val = val + 1;
                            InterfaceManager.number.put(category, val);

                            double val2 = InterfaceManager.amount.get(category);
                            val2 = val2 + amount;
                            InterfaceManager.amount.put(category, val2);
                        }
                    }
                    bf.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }

        //create dataset
        DefaultPieDataset barDataset = new DefaultPieDataset();

        Set<String> keys = null;
        Collection<Integer> values = null;
        Collection<Double> amountValues = null;

        if (incomeRadioButton.isSelected()) {
            keys = InterfaceManager.number.keySet();
            values = InterfaceManager.number.values();
            amountValues = InterfaceManager.amount.values();

            int i = 0;
            for (String key : keys) {
                int value = (int) values.toArray()[i];
                double amountValue = (double) amountValues.toArray()[i];
                if (value != 0) {
                    barDataset.setValue(key, amountValue);
                }
                i++;
            }
        } else if (expenditureRadioButton.isSelected()) {
            keys = InterfaceManager.number.keySet();
            values = InterfaceManager.number.values();
            amountValues = InterfaceManager.amount.values();

            int i = 0;
            for (String key : keys) {
                int value = (int) values.toArray()[i];
                double amountValue = (double) amountValues.toArray()[i];
                if (value != 0) {
                    barDataset.setValue(key, amountValue);
                }
                i++;
            }
        }

        //create chart
        JFreeChart piechart = ChartFactory.createPieChart("Category", barDataset, false, true, false);//explain
        PiePlot piePlot = (PiePlot) piechart.getPlot();
        piePlot.setBackgroundPaint(Color.white);

        //create chartPanel to display chart(graph)
        ChartPanel pieChartPanel = new ChartPanel(piechart);
        panelPieChart.removeAll();
        panelPieChart.add(pieChartPanel, BorderLayout.CENTER);
        panelPieChart.validate();

        //create JTable
        DefaultTableModel model = (DefaultTableModel) categoryTable.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        TableRowSorter myTableRowSorter = new TableRowSorter(model);

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

        String header[] = {"Category", "Amount (" + currency + ")", "Percentage (%)"};
        model.setColumnIdentifiers(header);
        categoryTable.setRowSorter(myTableRowSorter);

        for (int j = 0; j < keys.size(); j++) {
            double totalAmount = 0.0;
            for (int i2 = 0; i2 < keys.size(); i2++) {
                totalAmount = totalAmount + (double) amountValues.toArray()[i2];
            }
            double amountPercentage = ((double) amountValues.toArray()[j] / totalAmount) * 100;

            int totalNumber = 0;
            double currentAmount = (int) values.toArray()[j];
            for (int i2 = 0; i2 < keys.size(); i2++) {
                totalNumber = totalNumber + (int) values.toArray()[i2];
            }
            double percentage = (currentAmount / totalNumber) * 100;

            if ((double) amountValues.toArray()[j] != 0.0) {
                String[] dataRow = {keys.toArray()[j].toString(), amountValues.toArray()[j].toString(), String.valueOf(amountPercentage)};
                model.addRow(dataRow);
            }
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
        categoryTable.setRowSorter(rowSorter);
    }

}
