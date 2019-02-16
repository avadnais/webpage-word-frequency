import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        String webpageString;

        webpageString = URLReader.read("https://en.wikipedia.org/wiki/Hash_table").replaceAll(
                "[.,\"]", "");

        HashTable ht = new HashTable();

        List<String> words = Arrays.asList(webpageString.split(" "));

        for(int i = 0; i < words.size(); i++)
            ht.addOne(words.get(i));

        ht.printAll();
    }
}
