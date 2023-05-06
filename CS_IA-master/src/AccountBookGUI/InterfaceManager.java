package AccountBookGUI;

import AccountBookGUI.Calendar.CalendarGUI;
import AccountBookGUI.Dashboard.CategoryGUI;
import AccountBookGUI.Dashboard.IncomeExpenditureGUI;
import AccountBookGUI.Dashboard.PaymentMethodGUI;
import Models.HistoryModel;
import Models.MonetaryModel;
import Models.UserModel;
import Utils.FileUtil;
import Utils.SessionUtil;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class InterfaceManager extends JFrame {
    public int calendarCount = 0;
    private LoginGUI loginGUI;
    private SignupGUI signupGUI;
    private MainGUI mainGUI;
    private HistoryGUI historyGUI;
    private MonetaryManagerGUI monetaryManagerGUI;
    private CalendarGUI calendarGUI;
    private CategoryGUI categoryGUI;
    private PaymentMethodGUI paymentMethodGUI;
    private IncomeExpenditureGUI incomeExpenditureGUI;
    private ManagerGUI managerGUI;
    private AccountGUI accountGUI;
    private PasswordGUI passwordGUI;
    private HistoryModel historyModel;
    private MonetaryModel monetaryModel;
    private JPanel loginPanel, signupPanel, mainMenuPanel, historyPanel, monetaryManagerPanel, calenderPanel, categoryPanel, paymentMethodPanel, incomeExpenditurePanel, managerPanel, accountPanel, passwordPanel;
    public static SessionUtil sessionUtil = new SessionUtil();
    public static ArrayList<UserModel> userList = new ArrayList<>();
    public static ArrayList<MonetaryModel> userHistory = new ArrayList<>();
    public static ArrayList<String> monthlyList = new ArrayList<>();
    public static ArrayList<String> monthlyDateList = new ArrayList<>();
    public static Map<String, Integer> number = new LinkedHashMap<>();
    public static Map<String, Double> amount = new LinkedHashMap<>();
    public static Map<String, Integer> year = new LinkedHashMap<>();
    public static Map<String, Integer> month = new LinkedHashMap<>();
    public static String selectedHistory;

    private void changePanel(JPanel panel) {
        setContentPane(panel);
        validate();
        repaint();
    }

    private void init() {
        loginGUI = new LoginGUI();
        signupGUI = new SignupGUI();
        mainGUI = new MainGUI();
        historyGUI = new HistoryGUI();
        monetaryManagerGUI = new MonetaryManagerGUI();
        calendarGUI = new CalendarGUI();
        categoryGUI = new CategoryGUI();
        paymentMethodGUI = new PaymentMethodGUI();
        incomeExpenditureGUI = new IncomeExpenditureGUI();
        managerGUI = new ManagerGUI();
        accountGUI = new AccountGUI();
        passwordGUI = new PasswordGUI();
        historyModel = new HistoryModel();
        monetaryModel = new MonetaryModel();

        loginPanel = loginGUI.getPanel();
        signupPanel = signupGUI.getPanel();
        mainMenuPanel = mainGUI.getPanel();
        historyPanel = historyGUI.getPanel();
        monetaryManagerPanel = monetaryManagerGUI.getPanel();
        calenderPanel = calendarGUI.getPanel();
        categoryPanel = categoryGUI.getPanel();
        paymentMethodPanel = paymentMethodGUI.getPanel();
        incomeExpenditurePanel = incomeExpenditureGUI.getPanel();
        managerPanel = managerGUI.getPanel();
        accountPanel = accountGUI.getPanel();
        passwordPanel = passwordGUI.getPanel();

        initEvents();

        FileUtil.createUserFile();
        FileUtil.readUserFile();
    }

    public InterfaceManager() {
        FileUtil.createDirectory();
        init();

        setPreferredSize(new Dimension(1280,720));
        setMinimumSize(new Dimension(960, 540));

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Image logo = new ImageIcon(this.getClass().getResource("/appIcon.png")).getImage();
        final Taskbar taskbar = Taskbar.getTaskbar();

        try {
            //set icon for macOS (and other systems which do support this method)
            taskbar.setIconImage(logo);
        } catch (final UnsupportedOperationException e) {
            System.out.println("The os does not support: 'taskbar.setIconImage'");
        } catch (final SecurityException e) {
            System.out.println("There was a security exception for: 'taskbar.setIconImage'");
        }
        this.setIconImage(logo);

        setContentPane(loginPanel);
        setTitle("Account Book");
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initEvents() {
        addLoginEvent();
        addSignupEvent();
        addMainEvent();
        addMonetaryManagerEvent();
        addIncomeExpenditureEvent();
        addCategoryEvent();
        addPaymentMethodEvent();
        addCalenderEvent();
        addHistoryEvent();
        addManagerEvent();
        addAccountEvent();
        addPasswordEvent();
    }

    private void addSignupEvent() {
        signupGUI.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupGUI.getSignupidTextField().setText("");
                signupGUI.getSignupUsernameTextField().setText("");
                signupGUI.getSignupPasswordField().setText("");
                signupGUI.getSignupPasswordField2().setText("");
                signupGUI.getCurrencyComboBox().setSelectedIndex(0);
                changePanel(loginPanel);
            }
        });
        signupGUI.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (signupGUI.signup()) {
                    changePanel(loginPanel);
                }
            }
        });
    }

    private void addLoginEvent() {
        loginGUI.getLoginPasswordField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    if (loginGUI.login()) {
                        changePanel(mainMenuPanel);
                        mainGUI.getWelcomeText().setText(String.format("<html>Welcome Back <br/>" + sessionUtil.getUsername() + "!" + "</html>"));
                        mainGUI.showMainText();
                        loginGUI.getLoginIdTextField().setText("");
                        loginGUI.getLoginPasswordField().setText("");

                        FileUtil.createHistoryFile();
                        FileUtil.readHistoryFile();
                        FileUtil.createCategoryFile();
                        FileUtil.createMonthlyFile();

                        monetaryManagerGUI.addAsMonthly();
                        mainGUI.showToBeAdded();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        loginGUI.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginGUI.login()) {
                    changePanel(mainMenuPanel);
                    mainGUI.getWelcomeText().setText(String.format("<html>Welcome Back <br/>" + sessionUtil.getUsername() + "!" + "</html>"));
                    mainGUI.showMainText();
                    loginGUI.getLoginIdTextField().setText("");
                    loginGUI.getLoginPasswordField().setText("");
                    FileUtil.createHistoryFile();
                    FileUtil.readHistoryFile();
                    FileUtil.createCategoryFile();
                    FileUtil.createMonthlyFile();

                    monetaryManagerGUI.addAsMonthly();

                }
            }
        });
        loginGUI.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(signupPanel);
                loginGUI.getLoginIdTextField().setText("");
                loginGUI.getLoginPasswordField().setText("");
            }
        });
        loginGUI.getForgotPasswordButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(passwordPanel);
            }
        });
    }

    private void addPasswordEvent() {
        passwordGUI.getFindMyPasswordButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordGUI.findPassword();
            }
        });
        passwordGUI.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(loginPanel);
            }
        });
    }

    private void addMainEvent() {
        mainGUI.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to Logout?");

                if (result == JOptionPane.CLOSED_OPTION) {

                } else if (result == JOptionPane.YES_OPTION) {
                    sessionUtil.logout();
                    historyModel.initTable(monetaryManagerGUI.getHistoryTable());
                    historyModel.initTable(historyGUI.getHistoryTable());
                    historyModel.initTable(categoryGUI.getCategoryTable());
                    historyModel.initTable(paymentMethodGUI.getPaymentTable());
                    historyModel.initTable(incomeExpenditureGUI.getIncomeTable());
                    monetaryManagerGUI.initCategory(monetaryManagerGUI.getCategoryComboBox());
                    init();
                    changePanel(loginPanel);
                }
            }
        });
        mainGUI.getMyAccountButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountGUI.showAccountInfo();
                changePanel(accountPanel);
            }
        });
        mainGUI.getManagerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monetaryManagerGUI.setIncomeCategory(managerGUI.getIncomeCategoryComboBox());
                monetaryManagerGUI.setExpenditureCategory(managerGUI.getExpenditureCategoryComboBox());
                managerGUI.showMonthlyData(managerGUI.getMonthlyTable());
                changePanel(managerPanel);
            }
        });
        mainGUI.getAddNewButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyModel.showHistory(monetaryManagerGUI.getHistoryTable());
                changePanel(monetaryManagerPanel);
            }
        });
        mainGUI.getDashboardButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incomeExpenditureGUI.getTypeComboBox().setSelectedIndex(0);
                incomeExpenditureGUI.getGraphComboBox().setSelectedIndex(0);
                InterfaceManager.year.put("year", 2022);
                InterfaceManager.month.put("month", 1);
                incomeExpenditureGUI.showChart();
                changePanel(incomeExpenditurePanel);
            }
        });
        mainGUI.getCalenderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (calendarCount == 0) {
                    calendarGUI.CalendarGUI();
                }
                calendarCount += 1;
                changePanel(calenderPanel);
            }
        });
        mainGUI.getHistoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyModel.showHistory(historyGUI.getHistoryTable());
                changePanel(historyPanel);
            }
        });
    }

    public void addMonetaryManagerEvent() {
        monetaryManagerGUI.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (monetaryManagerGUI.addNew(false)) {
                    mainGUI.showMainText();
                    historyModel.showHistory(monetaryManagerGUI.getHistoryTable());
                }
            }
        });
        monetaryManagerGUI.getAddAsMonthlyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (monetaryManagerGUI.addAsMonthlyData()) {
                    mainGUI.showMainText();
                    historyModel.showHistory(monetaryManagerGUI.getHistoryTable());
                }
            }
        });
        monetaryManagerGUI.getIncomeRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monetaryManagerGUI.getExpenditureRadioButton().setSelected(false);
                monetaryManagerGUI.setIncomeCategory(monetaryManagerGUI.getCategoryComboBox());
            }
        });
        monetaryManagerGUI.getExpenditureRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monetaryManagerGUI.getIncomeRadioButton().setSelected(false);
                monetaryManagerGUI.setExpenditureCategory(monetaryManagerGUI.getCategoryComboBox());
            }
        });
        monetaryManagerGUI.getRemoveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyModel.removeHistory(monetaryManagerGUI.getHistoryTable());
                historyModel.showHistory(monetaryManagerGUI.getHistoryTable());
            }
        });
        monetaryManagerGUI.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monetaryManagerGUI.initCategory(monetaryManagerGUI.getCategoryComboBox());
                mainGUI.showMainText();
                mainGUI.showToBeAdded();
                changePanel(mainMenuPanel);
            }
        });
    }

    public void addCategoryEvent() {
        categoryGUI.getIncomeExpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incomeExpenditureGUI.getTypeComboBox().setSelectedIndex(0);
                incomeExpenditureGUI.getGraphComboBox().setSelectedIndex(0);
                InterfaceManager.year.put("year", 2022);
                InterfaceManager.month.put("month", 1);
                incomeExpenditureGUI.showChart();
                changePanel(incomeExpenditurePanel);
            }
        });
        categoryGUI.getPaymentMethodButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentMethodGUI.getIncomeRadioButton().setSelected(true);
                paymentMethodGUI.showPieChart();
                changePanel(paymentMethodPanel);
            }
        });
        categoryGUI.getIncomeRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!categoryGUI.getExpenditureRadioButton().isSelected()) {
                    categoryGUI.getIncomeRadioButton().setSelected(true);
                }
                categoryGUI.getExpenditureRadioButton().setSelected(false);
                categoryGUI.showPieChart();
            }
        });
        categoryGUI.getExpenditureRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!categoryGUI.getIncomeRadioButton().isSelected()) {
                    categoryGUI.getExpenditureRadioButton().setSelected(true);
                }
                categoryGUI.getIncomeRadioButton().setSelected(false);
                categoryGUI.showPieChart();
            }
        });
        categoryGUI.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryGUI.showPieChart();
            }
        });
        categoryGUI.getYearComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (categoryGUI.getYearComboBox().getSelectedIndex() == 0) {
                    categoryGUI.getMonthComboBox().setSelectedIndex(0);
                    categoryGUI.getDateComboBox().setSelectedIndex(0);
                }
            }
        });
        categoryGUI.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.showMainText();
                categoryGUI.getExpenditureRadioButton().setSelected(false);
                incomeExpenditureGUI.getIncomeComboBox().setSelectedIndex(0);
                mainGUI.showToBeAdded();
                changePanel(mainMenuPanel);
            }
        });
    }

    public void addPaymentMethodEvent() {
        paymentMethodGUI.getIncomeExpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incomeExpenditureGUI.getTypeComboBox().setSelectedIndex(0);
                incomeExpenditureGUI.getGraphComboBox().setSelectedIndex(0);
                InterfaceManager.year.put("year", 2022);
                InterfaceManager.month.put("month", 1);
                incomeExpenditureGUI.showChart();
                changePanel(incomeExpenditurePanel);
            }
        });
        paymentMethodGUI.getCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryGUI.getIncomeRadioButton().setSelected(true);
                categoryGUI.showPieChart();
                changePanel(categoryPanel);
            }
        });
        paymentMethodGUI.getIncomeRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!paymentMethodGUI.getExpenditureRadioButton().isSelected()) {
                    paymentMethodGUI.getIncomeRadioButton().setSelected(true);
                }
                paymentMethodGUI.getExpenditureRadioButton().setSelected(false);
                paymentMethodGUI.showPieChart();
            }
        });
        paymentMethodGUI.getExpenditureRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!paymentMethodGUI.getIncomeRadioButton().isSelected()) {
                    paymentMethodGUI.getExpenditureRadioButton().setSelected(true);
                }
                paymentMethodGUI.getIncomeRadioButton().setSelected(false);
                paymentMethodGUI.showPieChart();
            }
        });
        paymentMethodGUI.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentMethodGUI.showPieChart();
            }
        });
        paymentMethodGUI.getYearComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (categoryGUI.getYearComboBox().getSelectedIndex() == 0) {
                    categoryGUI.getMonthComboBox().setSelectedIndex(0);
                    categoryGUI.getDateComboBox().setSelectedIndex(0);
                }
            }
        });
        paymentMethodGUI.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.showMainText();
                paymentMethodGUI.getExpenditureRadioButton().setSelected(false);
                incomeExpenditureGUI.getIncomeComboBox().setSelectedIndex(0);
                mainGUI.showToBeAdded();
                changePanel(mainMenuPanel);
            }
        });
    }

    public void addIncomeExpenditureEvent() {
        incomeExpenditureGUI.getCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryGUI.getIncomeRadioButton().setSelected(true);
                categoryGUI.showPieChart();
                changePanel(categoryPanel);
            }
        });
        incomeExpenditureGUI.getPaymentMethodButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentMethodGUI.getIncomeRadioButton().setSelected(true);
                paymentMethodGUI.showPieChart();
                changePanel(paymentMethodPanel);
            }
        });
        incomeExpenditureGUI.getTypeComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incomeExpenditureGUI.showChart();
            }
        });
        incomeExpenditureGUI.getGraphComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incomeExpenditureGUI.showChart();
            }
        });
        incomeExpenditureGUI.getNextButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = year.get("year");
                int b = month.get("month");

                if (incomeExpenditureGUI.getIncomeComboBox().getSelectedIndex() == 1) {
                    if (a == 2028) {
                        JOptionPane.showMessageDialog(null, "Year can't be over 2028");
                    } else {
                        a = a + 1;
                        year.put("year", a);
                    }
                } else if (incomeExpenditureGUI.getIncomeComboBox().getSelectedIndex() == 2) {
                    if (b == 12 && a != 2028) {
                        month.put("month", 1);
                        a = a + 1;
                        year.put("year", a);
                    } else if (a != 2028) {
                        b = b + 1;
                        month.put("month", b);
                    } else if (b != 12) {
                        b = b + 1;
                        month.put("month", b);
                    } else {
                        JOptionPane.showMessageDialog(null, "Year can't be over 2028");
                    }
                }

                incomeExpenditureGUI.showChart();
            }
        });
        incomeExpenditureGUI.getLastButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = year.get("year");
                int b = month.get("month");

                if (incomeExpenditureGUI.getIncomeComboBox().getSelectedIndex() == 1) {
                    if (a == 2022) {
                        JOptionPane.showMessageDialog(null, "Year can't be under 2022");
                    } else {
                        a = a - 1;
                        year.put("year", a);
                    }
                } else if (incomeExpenditureGUI.getIncomeComboBox().getSelectedIndex() == 2) {
                    if (b == 1 && a != 2022) {
                        month.put("month", 12);
                        a = a - 1;
                        year.put("year", a);
                    } else if (a != 2022) {
                        b = b - 1;
                        month.put("month", b);
                    } else if (b != 1) {
                        b = b - 1;
                        month.put("month", b);
                    } else {
                        JOptionPane.showMessageDialog(null, "Year can't be under 2022");
                    }
                }

                incomeExpenditureGUI.showChart();
            }
        });
        incomeExpenditureGUI.getIncomeComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (incomeExpenditureGUI.getIncomeComboBox().getSelectedIndex() == 0) {
                    incomeExpenditureGUI.getLastButton().setText("Next");
                    incomeExpenditureGUI.getNextButton().setText("Last");
                } else if (incomeExpenditureGUI.getIncomeComboBox().getSelectedIndex() == 1) {
                    incomeExpenditureGUI.getLastButton().setText("Last Year");
                    incomeExpenditureGUI.getNextButton().setText("Next Year");
                } else if (incomeExpenditureGUI.getIncomeComboBox().getSelectedIndex() == 2) {
                    incomeExpenditureGUI.getLastButton().setText("Last Month");
                    incomeExpenditureGUI.getNextButton().setText("Next Month");
                }
                incomeExpenditureGUI.showChart();
            }
        });
        incomeExpenditureGUI.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incomeExpenditureGUI.refresh();
            }
        });
        incomeExpenditureGUI.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incomeExpenditureGUI.getIncomeComboBox().setSelectedIndex(0);
                mainGUI.showMainText();
                mainGUI.showToBeAdded();
                changePanel(mainMenuPanel);
            }
        });
    }

    public void addHistoryEvent() {
        historyGUI.getRemoveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyModel.removeHistory(historyGUI.getHistoryTable());
                historyModel.showHistory(historyGUI.getHistoryTable());
                mainGUI.showMainText();
            }
        });
        historyGUI.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyModel.showHistory(historyGUI.getHistoryTable());
            }
        });
        historyGUI.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyModel.showHistory(historyGUI.getHistoryTable(), historyGUI.getSearchTextField().getText());
            }
        });
        historyGUI.getHistoryTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (historyGUI.getHistoryTable().getSelectedRowCount() == 1) {
                    int row = historyGUI.getHistoryTable().getSelectedRow();
                    String selectedHistory = "";

                    for (int i = 0; i < historyGUI.getHistoryTable().getColumnCount(); i++) {
                        if (i != historyGUI.getHistoryTable().getColumnCount() - 1) {
                            selectedHistory += historyGUI.getHistoryTable().getValueAt(row, i).toString() + "|";
                        } else {
                            selectedHistory += historyGUI.getHistoryTable().getValueAt(row, i).toString();
                        }
                    }
                    InterfaceManager.selectedHistory = selectedHistory;
                }
            }
        });
        historyGUI.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyModel.saveHistory(historyGUI.getHistoryTable());
            }
        });
        historyGUI.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.showMainText();
                mainGUI.showToBeAdded();
                changePanel(mainMenuPanel);
            }
        });
    }

    public void addCalenderEvent() {
        calendarGUI.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyModel.removeHistory(calendarGUI.getCalendarTable());
                calendarGUI.removeTableRow(calendarGUI.getCalendarTable());
            }
        });
        calendarGUI.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.showMainText();
                mainGUI.showToBeAdded();
                changePanel(mainMenuPanel);
            }
        });
        calendarGUI.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyModel.saveHistory(calendarGUI.getCalendarTable());
            }
        });
    }

    public void addManagerEvent() {
        managerGUI.getIncomeRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerGUI.getExpenditureRadioButton().setSelected(false);
            }
        });
        managerGUI.getExpenditureRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerGUI.getIncomeRadioButton().setSelected(false);
            }
        });
        managerGUI.getNewCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerGUI.addNewCategory();
                monetaryManagerGUI.setIncomeCategory(managerGUI.getIncomeCategoryComboBox());
                monetaryManagerGUI.setExpenditureCategory(managerGUI.getExpenditureCategoryComboBox());
            }
        });
        managerGUI.getIncomeDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerGUI.deleteIncomeCategory();
                monetaryManagerGUI.setIncomeCategory(managerGUI.getIncomeCategoryComboBox());
                monetaryManagerGUI.setExpenditureCategory(managerGUI.getExpenditureCategoryComboBox());
            }
        });
        managerGUI.getExpenditureDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerGUI.deleteExpenditureCategory();
                monetaryManagerGUI.setIncomeCategory(managerGUI.getIncomeCategoryComboBox());
                monetaryManagerGUI.setExpenditureCategory(managerGUI.getExpenditureCategoryComboBox());
            }
        });
        managerGUI.getMonthlyDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerGUI.deleteMonthlyData();
                managerGUI.showMonthlyData(managerGUI.getMonthlyTable());
            }
        });
        managerGUI.getMonthlySaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerGUI.saveMonthly();
            }
        });
        managerGUI.getMonthlyTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (managerGUI.getMonthlyTable().getSelectedRowCount() == 1) {
                    int row = managerGUI.getMonthlyTable().getSelectedRow();
                    String selectedHistory = "";

                    for (int i = 0; i < managerGUI.getMonthlyTable().getColumnCount(); i++) {
                        if (i != managerGUI.getMonthlyTable().getColumnCount() - 1) {
                            selectedHistory += managerGUI.getMonthlyTable().getValueAt(row, i).toString() + "|";
                        } else {
                            selectedHistory += managerGUI.getMonthlyTable().getValueAt(row, i).toString();
                        }
                    }
                    InterfaceManager.selectedHistory = selectedHistory;
                }
            }
        });
        managerGUI.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerGUI.getIncomeRadioButton().setSelected(false);
                managerGUI.getExpenditureRadioButton().setSelected(false);
                managerGUI.getIncomeCategoryComboBox().setSelectedIndex(0);
                managerGUI.getExpenditureCategoryComboBox().setSelectedIndex(0);
                mainGUI.showMainText();
                mainGUI.showToBeAdded();
                changePanel(mainMenuPanel);
            }
        });
    }

    public void addAccountEvent() {
        accountGUI.getChangePasswordButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountGUI.changePassword();
            }
        });
        accountGUI.getChangeCurrencyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.showMainText();
                accountGUI.changeCurrency();
            }
        });
        accountGUI.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.showMainText();
                mainGUI.showToBeAdded();
                changePanel(mainMenuPanel);
            }
        });
    }
}
