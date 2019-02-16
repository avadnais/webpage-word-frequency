import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                    outputPanel.populate(Main.createTable(inputPanel.textField.getText()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });


    }
}
