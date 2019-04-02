import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Set;

public class OutputPanel extends JPanel {

    DefaultTableModel tableModel1;
    DefaultTableModel tableModel2;
    JTable table1;
    JTable table2;
    Object[][] data;
    JLabel info1;
    JLabel info2;


    public OutputPanel() {

        table1 = new JTable(new DefaultTableModel());
        table2 = new JTable(new DefaultTableModel());
        info1 = new JLabel("Word frequency of your webpage");
        info2 = new JLabel("Word frequency of most similar webpage");

        String[] headers = {"Word", "Frequency"};
        tableModel1 = (DefaultTableModel) table1.getModel();
        tableModel1.setColumnIdentifiers(headers);
        tableModel2 = (DefaultTableModel) table2.getModel();
        tableModel2.setColumnIdentifiers(headers);
        Dimension size = getPreferredSize();
        size.width = 600;
        size.height = 800;
        setPreferredSize(size);
        setVisible(true);

    }

    public void populateT1(HashTable ht) {

        Set<String> keys = ht.keySet();
        data = new Object[keys.size()][2];

        int i = 0;
        for (String key : keys) {
            data[i][0] = key;
            data[i][1] = ht.get(key);
            i++;
        }


        for (int j = 0; j < data.length; j++) {
            tableModel1.addRow(data[j]);

        }
        tableModel1.fireTableDataChanged();


        table1.setModel(tableModel1);

        table1.setRowHeight(25);

        table1.setPreferredScrollableViewportSize(new Dimension(400, 480));
        JScrollPane scrollPane = new JScrollPane(table1);
        scrollPane.setBorder(BorderFactory.createTitledBorder(ht.getUrl()));
        table1.setFillsViewportHeight(true);

        info1.setVisible(true);

        add(scrollPane);
        //add(info);


    }

    public void populateT2(HashTable ht) {

        Set<String> keys = ht.keySet();
        data = new Object[keys.size()][2];

        int i = 0;
        for (String key : keys) {
            data[i][0] = key;
            data[i][1] = ht.get(key);
            i++;
        }


        for (int j = 0; j < data.length; j++) {
            tableModel2.addRow(data[j]);

        }
        tableModel1.fireTableDataChanged();


        table2.setModel(tableModel2);

        table2.setRowHeight(25);

        table2.setPreferredScrollableViewportSize(new Dimension(400, 480));
        JScrollPane scrollPane = new JScrollPane(table2);
        scrollPane.setBorder(BorderFactory.createTitledBorder(ht.getUrl()));
        table2.setFillsViewportHeight(true);

        info2.setVisible(true);

        add(scrollPane);
        //add(info);


    }
}

