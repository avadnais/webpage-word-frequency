import org.jsoup.*;
import org.jsoup.nodes.Document;

import java.net.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class URLReader {

    static Path PATH = FileSystems.getDefault().getPath("urls.txt");

    //private final static String URL_PATH = "/home/andy/csc365/webpage-word-frequency/src/urls.txt";
    private final static File f = new File(PATH.toString());

    public static ArrayList<HashTable> readAll() throws Exception {
        Scanner sc = new Scanner(f);
        ArrayList<String> urlStrings = new ArrayList<>(10);

        while (sc.hasNext()) urlStrings.add(sc.next());

        ArrayList<HashTable> tables = new ArrayList<>(10);

        for (String u : urlStrings) {
            tables.add(createTable(u));
        }

        return tables;
    }

    public static HashTable createTable(String url) throws Exception {
        HashTable ht = new HashTable();
        ht.setUrl(url);
        String webpageString = URLReader.read(url).replaceAll("[.,\"]", "");
        List<String> words = Arrays.asList(webpageString.split(" "));

        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).length() > 0)
                ht.addOne(words.get(i));
        }
        //ht.printAll();

        return ht;
    }

    public static BTree createBtree(HashTable ht) throws Exception {

        List<String> keys = new ArrayList<String>(ht.keySet());

        BTree bt = new BTree();

        for (int i = 0; i < keys.size(); i++) {
            bt.insert(new WordCount(keys.get(i), ht.get(keys.get(i))));
        }

        return bt;
    }


    public static String read(String urlString) throws Exception {
        System.out.println(urlString);
        URL url = new URL(urlString);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        StringBuilder sb = new StringBuilder();

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine);
        in.close();

        String html = sb.toString();

        Document doc = Jsoup.parse(html);
        String title = doc.title();
        String body = doc.body().text();


        return title + "\n" + body;
    }

    public static void main(String[] args) throws Exception {
        BTree bt = createBtree(createTable("https://en.wikipedia.org/wiki/Golf"));

        bt.writeRoot();

        System.out.println(bt.search(bt.root, new WordCount("the")).getWord());
        System.out.println(bt.search(bt.root, new WordCount("the")).getCount());
        System.out.println(bt.search(bt.root, new WordCount("birdie")).getWord());
        System.out.println(bt.search(bt.root, new WordCount("birdie")).getCount());

        System.out.println(bt.totalNumberOfNodes);
    }


}
