import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new MainFrame("Webpage Similarity");
                frame.setSize(500, 500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });


    }

}
