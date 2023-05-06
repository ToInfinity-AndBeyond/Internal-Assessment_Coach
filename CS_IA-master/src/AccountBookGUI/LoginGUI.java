package AccountBookGUI;

import Manager.UserManager;
import Utils.Security;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class LoginGUI extends JButton {
    private JPanel loginPanel;
    public JTextField loginIdTextField;
    private JPasswordField loginPasswordField;
    private JButton signUpButton;
    private JButton loginButton;
    private JButton forgotPasswordButton;

    public JPanel getPanel() {
        return loginPanel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public JTextField getLoginIdTextField() {
        return loginIdTextField;
    }

    public JPasswordField getLoginPasswordField() {
        return loginPasswordField;
    }

    public JButton getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    public boolean login() {
        String id = loginIdTextField.getText();
        String password = Security.encrypt(loginPasswordField.getText().toString());

        if (Objects.equals(id, "")) {
            JOptionPane.showMessageDialog(null, "Please enter your ID!");
        } else if (loginPasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Please enter your password!");
        } else if (!UserManager.isMatched(id, password)) {
            JOptionPane.showMessageDialog(null, "Your ID or password is not validated!");
        } else {
            loginIdTextField.setText("");
            loginPasswordField.setText("");
            return true;
        }
        return false;
    }

}
