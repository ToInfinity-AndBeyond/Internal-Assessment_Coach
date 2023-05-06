package AccountBookGUI;

import Manager.UserManager;
import Models.UserModel;
import Utils.FileUtil;
import Utils.Security;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class SignupGUI {
    private JPanel signupPanel;
    private JTextField signupUsernameTextField;
    private JPasswordField signupPasswordField;
    private JPasswordField signupPasswordField2;
    private JComboBox currencyComboBox;
    private JButton signUpButton;
    private JButton backButton;
    private JTextField signupidTextField;

    public JPanel getPanel() {
        return signupPanel;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public JTextField getSignupUsernameTextField() {
        return signupUsernameTextField;
    }

    public JTextField getSignupidTextField() {
        return signupidTextField;
    }

    public JPasswordField getSignupPasswordField() {
        return signupPasswordField;
    }

    public JPasswordField getSignupPasswordField2() {
        return signupPasswordField2;
    }

    public JComboBox getCurrencyComboBox() {
        return currencyComboBox;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public boolean signup() {
        String username = signupUsernameTextField.getText();
        String id = signupidTextField.getText();
        String password = Security.encrypt(signupPasswordField.getText().toString());
        String currency = (String) currencyComboBox.getSelectedItem();

        if (username.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter your Username!");
        } else if (id.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter your ID!");
        } else if (UserManager.findUser(id) > -1) {
            JOptionPane.showMessageDialog(null, "The ID is already in used!");
        } else if (signupPasswordField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter your Password!");
        } else if (!signupPasswordField.getText().toString().equals(signupPasswordField2.getText().toString())) {
            JOptionPane.showMessageDialog(null, "Please confirm your Password!");
        } else if (currencyComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please select your Currency!");
        } else {
            JOptionPane.showMessageDialog(null, String.format("Thank you for registration, %s!", username));

            InterfaceManager.userList.add(new UserModel(username, id, password, 0.0, 0.0, currency));
            FileUtil.saveUserList();

            signupUsernameTextField.setText("");
            signupidTextField.setText("");
            signupPasswordField.setText("");
            signupPasswordField2.setText("");
            currencyComboBox.setSelectedIndex(0);

            return true;
        }
        return false;
    }

}
