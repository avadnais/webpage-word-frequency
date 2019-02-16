import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputPanel extends JPanel {

    protected JButton goBtn;
    protected JTextField textField;

    public InputPanel(){
        Dimension size = getPreferredSize();
        size.width=250;
        size.height=30;
        setPreferredSize(size);

        textField = new JTextField(30);
        textField.setHorizontalAlignment(0);

        goBtn = new JButton("Go");

        add(textField);
        add(goBtn);

    }

}
