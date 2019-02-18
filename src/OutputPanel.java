import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Set;

public class OutputPanel extends JPanel {


    JTable table;
    Object[][] data;

    public OutputPanel() {
        Dimension size = getPreferredSize();
        size.width = 250;
        size.height = 200;
        setPreferredSize(size);
        setVisible(true);

    }

    public void populate(HashTable ht) {
        String[] headers = {"Word", "Frequency"};
        Set<String> keys= ht.keySet();
        data = new Object[keys.size()][2];

        int i = 0;
        for (String key : keys) {
            data[i][0] = key;
            data[i][1] = ht.get(key);
            i++;
        }
        table = new JTable(data, headers);

        table.setRowHeight(25);

        table.setPreferredScrollableViewportSize(new Dimension(400, 400));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        add(scrollPane);
    }
}

