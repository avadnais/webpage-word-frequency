import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private InputPanel inputPanel;
    private OutputPanel outputPanel;

    ScrollPane scrollPane;


    public MainFrame(String title){
        super(title);

        //set layout manager
        setLayout(new BorderLayout());

        //create swing component
        inputPanel = new InputPanel();
        outputPanel = new OutputPanel();


        //add swing components to content pane
        Container c = getContentPane();

        c.add(inputPanel,BorderLayout.NORTH);
        //c.add(outputPanel,BorderLayout.CENTER);


        //add behavior
        inputPanel.goBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    c.remove(outputPanel);
                    outputPanel = new OutputPanel();
                    HashTable ht = URLReader.createTable(inputPanel.textField.getText());
                    outputPanel.populate(ht, outputPanel.table1);
                    ArrayList<HashTable> tables = URLReader.readAll();
                    HashTable closestTo = WebpageSimilarity.closestTo(tables, ht);
                    outputPanel.populate(closestTo, outputPanel.table2);
                    c.add(outputPanel);
                    c.validate();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
