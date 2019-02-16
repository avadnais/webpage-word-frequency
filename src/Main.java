import java.util.Arrays;
import java.util.List;
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

    public static HashTable createTable(String url) throws Exception {

        String webpageString = URLReader.read(url).replaceAll("[.,\"]", "");

        HashTable ht = new HashTable();

        List<String> words = Arrays.asList(webpageString.split(" "));

        for(int i = 0; i < words.size(); i++) {
            if(words.get(i).length() > 0)
                ht.addOne(words.get(i));
        }

        ht.printAll();

        return ht;
    }
}
