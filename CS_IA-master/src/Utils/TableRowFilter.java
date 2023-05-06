package Utils;

import javax.swing.*;

public class TableRowFilter extends RowFilter {
    private String searchText;

    public TableRowFilter(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public boolean include(Entry entry) {
        return entry.getStringValue(0).toLowerCase().indexOf(searchText.toLowerCase()) >= 0 || entry.getStringValue(1).toLowerCase().indexOf(searchText.toLowerCase()) >= 0 || entry.getStringValue(2).toLowerCase().indexOf(searchText.toLowerCase()) >= 0 || entry.getStringValue(3).toLowerCase().indexOf(searchText.toLowerCase()) >= 0 || entry.getStringValue(4).toLowerCase().indexOf(searchText.toLowerCase()) >= 0 || entry.getStringValue(5).toLowerCase().indexOf(searchText.toLowerCase()) >= 0 || entry.getStringValue(6).toLowerCase().indexOf(searchText.toLowerCase()) >= 0;
    }
}
