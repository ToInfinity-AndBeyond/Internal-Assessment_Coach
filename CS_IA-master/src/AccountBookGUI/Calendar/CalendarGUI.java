package AccountBookGUI.Calendar;

import AccountBookGUI.InterfaceManager;
import Utils.OSValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

class CalendarManager {
    static final int CAL_WIDTH = 7;
    final static int CAL_HEIGHT = 6;
    int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
    int selectedYear;
    int selectedMonth;
    int selectedDay;
    final int calLastDateOfMonth[] = {31,28,31,30,31,30,31,31,30,31,30,31};
    int calLastDate;
    Calendar today = Calendar.getInstance();
    Calendar cal;

    public CalendarManager(){
        setToday();
    }
    public void setToday(){
        selectedYear = today.get(Calendar.YEAR);
        selectedMonth = today.get(Calendar.MONTH);
        selectedDay = today.get(Calendar.DAY_OF_MONTH);
        makeCalendar(today);
    }
    private void makeCalendar(Calendar cal){
        int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
        if(selectedMonth == 1) calLastDate = calLastDateOfMonth[selectedMonth] + leapCheck(selectedYear);
        else calLastDate = calLastDateOfMonth[selectedMonth];

        for(int i = 0 ; i<CAL_HEIGHT ; i++){
            for(int j = 0 ; j<CAL_WIDTH ; j++){
                calDates[i][j] = 0;
            }
        }

        for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){
            if(i == 0) k = calStartingPos;
            else k = 0;
            for(int j = k ; j<CAL_WIDTH ; j++){
                if(num <= calLastDate) calDates[i][j]=num++;
            }
        }
    }
    private int leapCheck(int year){
        if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
        else return 0;
    }
    public void moveMonth(int mon){
        selectedMonth += mon;
        if(selectedMonth>11) while(selectedMonth>11){
            selectedYear++;
            selectedMonth -= 12;
        } else if (selectedMonth<0) while(selectedMonth<0){
            selectedYear--;
            selectedMonth += 12;
        }
        cal = new GregorianCalendar(selectedYear,selectedMonth,selectedDay);
        makeCalendar(cal);
    }
}

public class CalendarGUI extends CalendarManager {
    // JPanel calOpPanel;
    private JButton todayButton;
    private JLabel todayLabel;
    private JButton lastYearButton;
    private JButton lastMonthButton;
    private JLabel currentDateLabel;
    private JButton nextMonthButton;
    private JButton nextYearButton;
    ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();

    JPanel calPanel;
    JPanel cal2Panel;
    JButton weekDaysName[];
    JButton dateButtons[][] = new JButton[6][7];
    listenFordateButtons listenFordateButtons = new listenFordateButtons();

    //    JPanel infoPanel;
    private JLabel infoClock;
    private JLabel selectedDate;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton backButton;
    private JPanel calendarPanel;
    private JTable calendarTable;
    //상수, 메세지
    final String WEEK_DAY_NAME[] = {"SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT"};

    public JPanel getPanel() {
        return calendarPanel;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTable getCalendarTable() {
        return calendarTable;
    }

    public void CalendarGUI() {

        todayButton.addActionListener(lForCalOpButtons);
        todayLabel.setText(today.get(Calendar.MONTH) + 1 + "/" + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR));
        lastYearButton.addActionListener(lForCalOpButtons);
        lastMonthButton.addActionListener(lForCalOpButtons);
        currentDateLabel.setText("<html><table width=100><tr><th><font size=4>" + ((selectedMonth + 1) < 10 ? "&nbsp;" : "") + (selectedMonth + 1) + " / " + selectedYear + "</th></tr></table></html>");
        nextMonthButton.addActionListener(lForCalOpButtons);
        nextYearButton.addActionListener(lForCalOpButtons);

        cal2Panel = new JPanel();
        weekDaysName = new JButton[7];
        for (int i = 0; i < CAL_WIDTH; i++) {
            weekDaysName[i] = new JButton(WEEK_DAY_NAME[i]);
            weekDaysName[i].setBorderPainted(false);
            weekDaysName[i].setContentAreaFilled(false);
            weekDaysName[i].setForeground(Color.WHITE);
            if (i == 0) weekDaysName[i].setBackground(new Color(200, 50, 50));
            else if (i == 6) weekDaysName[i].setBackground(new Color(50, 100, 200));
            else weekDaysName[i].setBackground(new Color(150, 150, 150));
            weekDaysName[i].setOpaque(true);
            weekDaysName[i].setFocusPainted(false);
            cal2Panel.add(weekDaysName[i]);
        }

        for (int i = 0; i < CAL_HEIGHT; i++) {
            for (int j = 0; j < CAL_WIDTH; j++) {
                dateButtons[i][j] = new JButton();
                dateButtons[i][j].setBorderPainted(true);
                dateButtons[i][j].setContentAreaFilled(false);
                dateButtons[i][j].setBackground(Color.WHITE);
                dateButtons[i][j].setOpaque(true);
                dateButtons[i][j].addActionListener(listenFordateButtons);
                cal2Panel.add(dateButtons[i][j]);
            }
        }

        cal2Panel.setLayout(new GridLayout(0, 7, 2, 2));
        cal2Panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        cal2Panel.setBackground(new Color(255, 255, 255));
        showCal();

        infoClock.setText("");
        selectedDate.setText("<Html><font size=3>" + (today.get(Calendar.MONTH) + 1) + "/" + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR) + "&nbsp;(Today)</html>");

        JPanel frameSubPanelWest = new JPanel();
        frameSubPanelWest.setLayout(new BorderLayout());
        frameSubPanelWest.add(cal2Panel, BorderLayout.CENTER);

        calPanel.setLayout(new BorderLayout());
        calPanel.add(frameSubPanelWest);

        focusToday();

        ThreadControl threadCnl = new ThreadControl();
        threadCnl.start();

        String year = String.valueOf(today.get(Calendar.YEAR));
        String month = String.valueOf(today.get(Calendar.MONTH) + 1);
        int tempMonth = today.get(Calendar.MONTH) + 1;
        if (today.get(Calendar.MONTH) + 1 < 10) {
            month = "0" + tempMonth;
        }
        String date = String.valueOf(today.get(Calendar.DAY_OF_MONTH));
        if (today.get(Calendar.DAY_OF_MONTH) < 10) {
            date = "0" + today.get(Calendar.DAY_OF_MONTH);
        }
        showTable(year, month, date);
    }

    private void focusToday() {
        if (today.get(Calendar.DAY_OF_WEEK) == 1)
            dateButtons[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
        else
            dateButtons[today.get(Calendar.WEEK_OF_MONTH) - 1][today.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
    }

    private void showCal() {
        for (int i = 0; i < CAL_HEIGHT; i++) {
            for (int j = 0; j < CAL_WIDTH; j++) {
                String fontColor = "black";
                if (j == 0) fontColor = "red";
                else if (j == 6) fontColor = "blue";

                File f = new File("MemoData/" + selectedYear + ((selectedMonth + 1) < 10 ? "0" : "") + (selectedMonth + 1) + (calDates[i][j] < 10 ? "0" : "") + calDates[i][j] + ".txt");
                if (f.exists()) {
                    dateButtons[i][j].setText("<html><b><font color=" + fontColor + ">" + calDates[i][j] + "</font></b></html>");
                } else
                    dateButtons[i][j].setText("<html><font color=" + fontColor + ">" + calDates[i][j] + "</font></html>");

                JLabel todayMark = new JLabel("<html><font color=green><font size = 20>*</html>");
                dateButtons[i][j].removeAll();
                if (selectedMonth == today.get(Calendar.MONTH) &&
                        selectedYear == today.get(Calendar.YEAR) &&
                        calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)) {
                    dateButtons[i][j].add(todayMark);
                    dateButtons[i][j].setToolTipText("Today");
                }

                if (calDates[i][j] == 0) dateButtons[i][j].setVisible(false);
                else dateButtons[i][j].setVisible(true);
            }
        }
    }

    private class ListenForCalOpButtons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == todayButton) {
                setToday();
                listenFordateButtons.actionPerformed(e);
//                focusToday();
            } else if (e.getSource() == lastYearButton) moveMonth(-12);
            else if (e.getSource() == lastMonthButton) moveMonth(-1);
            else if (e.getSource() == nextMonthButton) moveMonth(1);
            else if (e.getSource() == nextYearButton) moveMonth(12);

            currentDateLabel.setText("<html><table width=100><tr><th><font size=4>" + ((selectedMonth + 1) < 10 ? "&nbsp;" : "") + (selectedMonth + 1) + " / " + selectedYear + "</th></tr></table></html>");
            showCal();
        }
    }

    private class listenFordateButtons implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int k = 0, l = 0;
            for (int i = 0; i < CAL_HEIGHT; i++) {
                for (int j = 0; j < CAL_WIDTH; j++) {
                    if (e.getSource() == dateButtons[i][j]) {
                        k = i;
                        l = j;
                    }
                }
            }

            if (!(k == 0 && l == 0)) selectedDay = calDates[k][l];

            cal = new GregorianCalendar(selectedYear, selectedMonth, selectedDay);

            String dDayString = new String();
            int dDay = ((int) ((cal.getTimeInMillis() - today.getTimeInMillis()) / 1000 / 60 / 60 / 24));
            if (dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR))
                    && (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
                    && (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today";
            else if (dDay >= 0) dDayString = "D-" + (dDay + 1);
            else if (dDay < 0) dDayString = "D+" + (dDay) * (-1);

            selectedDate.setText("<Html><font size=3>" + (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear + "&nbsp;(" + dDayString + ")</html>");

            String year = String.valueOf(selectedYear);
            String month = String.valueOf(selectedMonth + 1);
            int tempMonth = selectedMonth + 1;
            if (selectedMonth + 1 < 10 || selectedMonth == 12) {
                month = "0" + tempMonth;
            }
            String date = String.valueOf(selectedDay);
            if (selectedDay < 10) {
                date = "0" + selectedDay;
            }
            showTable(year, month, date);
        }
    }

    private class ThreadControl extends Thread {
        public void run() {
            while (true) {
                try {
                    today = Calendar.getInstance();
                    String amPm = (today.get(Calendar.AM_PM) == 0 ? "AM" : "PM");
                    String hour;
                    if (today.get(Calendar.HOUR) == 0) hour = "12";
                    else if (today.get(Calendar.HOUR) == 12) hour = " 0";
                    else hour = (today.get(Calendar.HOUR) < 10 ? " " : "") + today.get(Calendar.HOUR);
                    String min = (today.get(Calendar.MINUTE) < 10 ? "0" : "") + today.get(Calendar.MINUTE);
                    String sec = (today.get(Calendar.SECOND) < 10 ? "0" : "") + today.get(Calendar.SECOND);
                    infoClock.setText(amPm + " " + hour + ":" + min + ":" + sec);

                    sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread:Error");
                }
            }
        }
    }

    public void showTable(String year, String month, String date) {
        String selectedDate = year + "/" + month + "/" + date;

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

            String[] columnsName = {"Type", "Amount(" + currency + ")", "Date(Y/M/D)", "Category", "Payment Method", "Monthly", "Note"};
            DefaultTableModel model = (DefaultTableModel) calendarTable.getModel();
            model.setColumnIdentifiers(columnsName);
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();

            Object[] tableLines = bf.lines().toArray();

            for (int i = 0; i < tableLines.length; i++) {
                String line2 = tableLines[i].toString().trim();
                String[] dataRow = line2.split("\\|");

                String historyDate = dataRow[2];
                if (selectedDate.equals(historyDate)) {
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
            calendarTable.setRowSorter(rowSorter);
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void removeTableRow(JTable t) {
        ((DefaultTableModel) t.getModel()).removeRow(t.getSelectedRow());
    }
}