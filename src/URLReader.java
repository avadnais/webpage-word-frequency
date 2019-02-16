import org.jsoup.*;
import org.jsoup.nodes.Document;

import java.net.*;
import java.io.*;

public class URLReader {

    public static String read(String urlString) throws Exception{

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



        System.out.printf("Title: %s%n", title);
        System.out.printf("Body: %s", body);

        return title + "\n" + body;
    }
}
