package AccountBookGUI;

import Utils.FileUtil;
import Utils.Security;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class PasswordGUI {
    private JPanel passwordPanel;
    private JButton backButton;
    private JTextField usernameTextField;
    private JTextField idTextField;
    private JButton findMyPasswordButton;

    public JPanel getPanel() {
        return passwordPanel;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getFindMyPasswordButton() {
        return findMyPasswordButton;
    }

    public void findPassword() {
        String username = usernameTextField.getText().toString();
        String id = idTextField.getText().toString();
        String password = null;
        String hint = "";
        FileUtil.findUser(username, id);

        if (FileUtil.findUser(username, id) != null) {
            password = Security.decrypt(FileUtil.findUser(username, id));

            for (int i = 0; i < Objects.requireNonNull(password).length(); i++) {
                if (i == 0) {
                    hint += password.charAt(0);
                } else if (i == Objects.requireNonNull(password).length() - 1) {
                    hint += password.charAt(i);
                } else {
                    hint += "*";
                }
            }

            JOptionPane.showMessageDialog(null, "Password Hint: " + hint);
            usernameTextField.setText("");
            idTextField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Please enter your correct Username or ID!");
        }
    }

}
