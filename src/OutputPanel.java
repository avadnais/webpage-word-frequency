import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Set;

public class OutputPanel extends JPanel {

    DefaultTableModel tableModel;
    JTable table1;
    JTable table2;
    Object[][] data;


    public OutputPanel() {

        table1 = new JTable(new DefaultTableModel());
        table2 = new JTable(new DefaultTableModel());

        tableModel = (DefaultTableModel) table1.getModel();
        String[] headers = {"Word", "Frequency"};
        tableModel.setColumnIdentifiers(headers);
        Dimension size = getPreferredSize();
        size.width = 600;
        size.height = 200;
        setPreferredSize(size);
        setVisible(true);

    }

    public void populate(HashTable ht, JTable table) {

        Container c = new Container();
        //c.add(title,BorderLayout.NORTH);

        Set<String> keys = ht.keySet();
        data = new Object[keys.size()][2];

        int i = 0;
        for (String key : keys) {
            data[i][0] = key;
            data[i][1] = ht.get(key);
            i++;
        }


        for (int j = 0; j < data.length; j++) {
            tableModel.addRow(data[j]);

        }
        tableModel.fireTableDataChanged();


        table.setModel(tableModel);

        table.setRowHeight(25);

        table.setPreferredScrollableViewportSize(new Dimension(400, 480));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(ht.getUrl()));
        table.setFillsViewportHeight(true);

        c.add(scrollPane);
        //add(title);
        add(scrollPane);


    }
}

