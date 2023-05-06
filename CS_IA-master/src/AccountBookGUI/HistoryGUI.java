package AccountBookGUI;

import javax.swing.*;

public class HistoryGUI {
    private JPanel historyPanel;
    private JTable historyTable;
    private JButton backButton;
    private JButton refreshButton;
    private JButton deleteButton;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton saveButton;

    public JPanel getPanel() {
        return historyPanel;
    }

    public JTable getHistoryTable() {
        return historyTable;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getRemoveButton() {
        return deleteButton;
    }

    public JTextField getSearchTextField() {
        return searchTextField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }
}
