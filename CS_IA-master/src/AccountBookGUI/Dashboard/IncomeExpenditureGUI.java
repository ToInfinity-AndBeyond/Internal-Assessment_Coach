package AccountBookGUI.Dashboard;

import AccountBookGUI.InterfaceManager;
import Utils.OSValidator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.*;
import java.util.*;

public class IncomeExpenditureGUI {
    private JTable incomeTable;
    private JButton backButton;
    private JComboBox incomeComboBox;
    private JButton lastButton;
    private JButton nextButton;
    private JButton refreshButton;
    private JPanel incomeExpenditurePanel;
    private JPanel panelGraph;
    private JComboBox graphComboBox;
    private JComboBox typeComboBox;
    private JButton paymentMethodButton;
    private JButton categoryButton;

    public JPanel getPanel() {
        return incomeExpenditurePanel;
    }

    public JTable getIncomeTable() {
        return incomeTable;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getLastButton() {
        return lastButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JComboBox getIncomeComboBox() {
        return incomeComboBox;
    }

    public JComboBox getTypeComboBox() {
        return typeComboBox;
    }

    public JComboBox getGraphComboBox() {
        return graphComboBox;
    }

    public JButton getCategoryButton() {
        return categoryButton;
    }

    public JButton getPaymentMethodButton() {
        return paymentMethodButton;
    }

    public void initIncomeHistory() {
        InterfaceManager.number.clear();
        InterfaceManager.amount.clear();

        String showType = incomeComboBox.getSelectedItem().toString();
        if (Objects.equals(showType, "Yearly")) {
            InterfaceManager.amount.put("2028", 0.0);
            InterfaceManager.amount.put("2027", 0.0);
            InterfaceManager.amount.put("2026", 0.0);
            InterfaceManager.amount.put("2025", 0.0);
            InterfaceManager.amount.put("2024", 0.0);
            InterfaceManager.amount.put("2023", 0.0);
            InterfaceManager.amount.put("2022", 0.0);
        } else if (Objects.equals(showType, "Monthly")) {
            InterfaceManager.amount.put("12", 0.0);
            InterfaceManager.amount.put("11", 0.0);
            InterfaceManager.amount.put("10", 0.0);
            InterfaceManager.amount.put("9", 0.0);
            InterfaceManager.amount.put("8", 0.0);
            InterfaceManager.amount.put("7", 0.0);
            InterfaceManager.amount.put("6", 0.0);
            InterfaceManager.amount.put("5", 0.0);
            InterfaceManager.amount.put("4", 0.0);
            InterfaceManager.amount.put("3", 0.0);
            InterfaceManager.amount.put("2", 0.0);
            InterfaceManager.amount.put("1", 0.0);
        } else if (Objects.equals(showType, "Daily")) {
            InterfaceManager.amount.put("31", 0.0);
            InterfaceManager.amount.put("30", 0.0);
            InterfaceManager.amount.put("29", 0.0);
            InterfaceManager.amount.put("28", 0.0);
            InterfaceManager.amount.put("27", 0.0);
            InterfaceManager.amount.put("26", 0.0);
            InterfaceManager.amount.put("25", 0.0);
            InterfaceManager.amount.put("24", 0.0);
            InterfaceManager.amount.put("23", 0.0);
            InterfaceManager.amount.put("22", 0.0);
            InterfaceManager.amount.put("21", 0.0);
            InterfaceManager.amount.put("20", 0.0);
            InterfaceManager.amount.put("19", 0.0);
            InterfaceManager.amount.put("18", 0.0);
            InterfaceManager.amount.put("17", 0.0);
            InterfaceManager.amount.put("16", 0.0);
            InterfaceManager.amount.put("15", 0.0);
            InterfaceManager.amount.put("14", 0.0);
            InterfaceManager.amount.put("13", 0.0);
            InterfaceManager.amount.put("12", 0.0);
            InterfaceManager.amount.put("11", 0.0);
            InterfaceManager.amount.put("10", 0.0);
            InterfaceManager.amount.put("9", 0.0);
            InterfaceManager.amount.put("8", 0.0);
            InterfaceManager.amount.put("7", 0.0);
            InterfaceManager.amount.put("6", 0.0);
            InterfaceManager.amount.put("5", 0.0);
            InterfaceManager.amount.put("4", 0.0);
            InterfaceManager.amount.put("3", 0.0);
            InterfaceManager.amount.put("2", 0.0);
            InterfaceManager.amount.put("1", 0.0);
        }
    }

    public void refresh() {
        typeComboBox.setSelectedIndex(0);
        graphComboBox.setSelectedIndex(0);
        incomeComboBox.setSelectedIndex(0);
        showChart();
    }

    public void showChart() {
        String mainType = null;
        if (typeComboBox.getSelectedIndex() == 0) {
            mainType = "Income";
        } else if (typeComboBox.getSelectedIndex() == 1) {
            mainType = "Expenditure";
        }

        initIncomeHistory();

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

                String[] divDateData = date.split("/");
                String divYear = divDateData[0];
                String divMonth = divDateData[1];
                String divDate = divDateData[2];

                String comMonthly = String.valueOf(InterfaceManager.year.get("year"));
                String comDaily = InterfaceManager.year.get("year") + "/" + InterfaceManager.month.get("month");

                int month = Integer.parseInt(divMonth);
                int date1 = Integer.parseInt(divDate);

                String finalMonth = String.valueOf(month);
                String finalDate = String.valueOf(date1);

                String daily = divYear + "/" + finalMonth;

                if (Objects.equals(type, mainType) && incomeComboBox.getSelectedIndex() == 0) {
                    double val = InterfaceManager.amount.get(divYear);
                    val = val + amount;
                    InterfaceManager.amount.put(divYear, val);
                } else if (Objects.equals(type, mainType) && incomeComboBox.getSelectedIndex() == 1 && Objects.equals(divYear, comMonthly)) {
                    double val = InterfaceManager.amount.get(finalMonth);
                    val = val + amount;
                    InterfaceManager.amount.put(finalMonth, val);
                } else if (Objects.equals(type, mainType) && incomeComboBox.getSelectedIndex() == 2 && Objects.equals(daily, comDaily)) {
                    double val = InterfaceManager.amount.get(finalDate);
                    val = val + amount;
                    InterfaceManager.amount.put(finalDate, val);
                }
            }
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }

        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Set<String> keys = InterfaceManager.amount.keySet();
        Collection<String> a = InterfaceManager.amount.keySet();
        Collection<Double> amountValues = InterfaceManager.amount.values();

        int count = 0;

        for (int i = 1; i < keys.size() + 1; i++) {
            double amountValue = (double) amountValues.toArray()[keys.size() - i];
            if (amountValue != 0) {
                count = count + 1;
            }
        }

        if (count != 0) {
            for (int i = 1; i < keys.size() + 1; i++) {
                String year = (String) a.toArray()[keys.size() - i];
                double amountValue = (double) amountValues.toArray()[keys.size() - i];
                dataset.setValue(amountValue, "Amount", year);
            }
        }

        String title = null;
        String categoryAxisLabel = null;

        if (incomeComboBox.getSelectedIndex() == 0) {
            title = mainType;
            categoryAxisLabel = "Year";
        } else if (incomeComboBox.getSelectedIndex() == 1) {
            title = mainType + "-" + String.valueOf(InterfaceManager.year.get("year"));
            categoryAxisLabel = "Month";
        } else if (incomeComboBox.getSelectedIndex() == 2) {
            title = mainType + "-" + InterfaceManager.year.get("year") + "/" + InterfaceManager.month.get("month");
            categoryAxisLabel = "Date";
        }

        //create chart
        if (graphComboBox.getSelectedIndex() == 0) {
            JFreeChart lineChart = ChartFactory.createLineChart(title, categoryAxisLabel, "Amount ($)",
                    dataset, PlotOrientation.VERTICAL, false, true, false);

            //create plot object
            CategoryPlot lineCategoryPlot = lineChart.getCategoryPlot();
            // lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
            lineCategoryPlot.setBackgroundPaint(Color.white);

            //create render object to change the moficy the line properties like color
            LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
            Color lineChartColor = new Color(204, 0, 51);
            lineRenderer.setSeriesPaint(0, lineChartColor);

            //create chartPanel to display chart(graph)
            ChartPanel lineChartPanel = new ChartPanel(lineChart);
            panelGraph.removeAll();
            panelGraph.add(lineChartPanel, BorderLayout.CENTER);
            panelGraph.validate();
        } else if (graphComboBox.getSelectedIndex() == 1) {
            JFreeChart barChart = ChartFactory.createBarChart(title, categoryAxisLabel, "Amount ($)",
                    dataset, PlotOrientation.VERTICAL, false, true, false);

            CategoryPlot categoryPlot = barChart.getCategoryPlot();

            //categoryPlot.setRangeGridlinePaint(Color.BLUE);
            categoryPlot.setBackgroundPaint(Color.WHITE);
            BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
            Color clr3 = new Color(204, 0, 51);
            renderer.setSeriesPaint(0, clr3);

            ChartPanel barChartPanel = new ChartPanel(barChart);
            panelGraph.removeAll();
            panelGraph.add(barChartPanel, BorderLayout.CENTER);
            panelGraph.validate();
        }

        // Create JTable
        DefaultTableModel model = (DefaultTableModel) incomeTable.getModel();
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

        String header[] = {categoryAxisLabel, "Amount (" + currency + ")", "Percentage (%)"};
        model.setColumnIdentifiers(header);
        incomeTable.setRowSorter(myTableRowSorter);

        for (int j = 0; j < keys.size(); j++) {
            double totalAmount = 0.0;
            for (int i2 = 0; i2 < keys.size(); i2++) {
                totalAmount = totalAmount + (double) amountValues.toArray()[i2];
            }
            double amountPercentage = ((double) amountValues.toArray()[j] / totalAmount) * 100;

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
        incomeTable.setRowSorter(rowSorter);
    }

}