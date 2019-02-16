import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class OutputPanel extends JPanel {


    JTable table;
    Object[][] data;

    public OutputPanel() {
        Dimension size = getPreferredSize();
        size.width = 250;
        size.height = 200;
        setPreferredSize(size);


    }

    public void populate(HashTable ht) {
        String[] headers = {"Word", "Frequency"};
        data = new Object[ht.keySet().size()][2];

        int j = 0;
        for (int i = 0; i < ht.table.length; i++) {
            if (ht.table[i] != null) {
                data[j][0] = ht.table[i].word;
                data[j][1] = ht.table[i].freq;
                j++;
            }
        }
        table = new JTable(data, headers);

        table.setRowHeight(25);

        table.setPreferredScrollableViewportSize(new Dimension(500, 400));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        add(scrollPane);
    }
}

