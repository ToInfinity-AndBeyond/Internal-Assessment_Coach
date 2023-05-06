package AccountBookGUI;

import Utils.FileUtil;
import Utils.Security;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class AccountGUI {
    private JButton backButton;
    private JPanel accountPanel;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JComboBox currencyComboBox;
    private JButton changeCurrencyButton;
    private JButton changePasswordButton;
    private JLabel usernameLabel;
    private JLabel idLabel;
    private JLabel currencyLabel;
    private JPasswordField newPasswordField2;

    public JPanel getPanel() {
        return accountPanel;
    }

    public JButton getChangeCurrencyButton() {
        return changeCurrencyButton;
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void showAccountInfo() {
        usernameLabel.setText("Username: " + InterfaceManager.sessionUtil.getUsername());
        idLabel.setText("ID: " + InterfaceManager.sessionUtil.getId());
        currencyLabel.setText("Current Currency: " + InterfaceManager.sessionUtil.getCurrency());
    }

    public void changePassword() {
        String currentPassword = Security.encrypt(currentPasswordField.getText().toString());
        String newPassword = Security.encrypt(newPasswordField.getText().toString());
        String newPassword2 = Security.encrypt(newPasswordField2.getText().toString());

        if (currentPasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Please enter the current Password!");
        } else if (newPasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Please enter the new Password!");
        } else if (!Objects.equals(currentPassword, InterfaceManager.sessionUtil.getPassword())) {
            JOptionPane.showMessageDialog(null, "You entered the wrong current Password!");
        } else if (!Objects.equals(newPassword, newPassword2) || Objects.equals(newPassword2, "")) {
            JOptionPane.showMessageDialog(null, "Please confirm your Password!");
        } else if (Objects.equals(Security.encrypt(currentPasswordField.getText().toString()), InterfaceManager.sessionUtil.getPassword())) {
            InterfaceManager.sessionUtil.setPassword(newPassword);
            FileUtil.changePassword(newPassword);
            JOptionPane.showMessageDialog(null, "Your Password has been changed!");
            currentPasswordField.setText("");
            newPasswordField.setText("");
            newPasswordField2.setText("");
        }
    }

    public void changeCurrency() {
        String selectedCurrency = currencyComboBox.getSelectedItem().toString();

        if (currencyComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select the Currency!");
        } else {
            FileUtil.changeCurrency(selectedCurrency);
            InterfaceManager.sessionUtil.setCurrency(selectedCurrency);
            JOptionPane.showMessageDialog(null, "Your Currency has changed to " + selectedCurrency);
            currencyComboBox.setSelectedIndex(0);
            showAccountInfo();
        }
    }

}
