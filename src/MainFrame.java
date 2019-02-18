import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private InputPanel inputPanel;
    private OutputPanel outputPanel;



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
        c.add(outputPanel,BorderLayout.CENTER);


        //add behavior
        inputPanel.goBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HashTable ht = URLReader.createTable(inputPanel.textField.getText());
                    outputPanel.populate(ht);
                    ArrayList<HashTable> tables = URLReader.readAll();
                    WebpageSimilarity.closestTo(tables, ht);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });


    }
}
