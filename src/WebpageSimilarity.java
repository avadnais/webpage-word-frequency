import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class WebpageSimilarity {
    public static HashTable closestTo(ArrayList<HashTable> field, HashTable ht){
        HashTable closestTo = field.get(0);
        double highScore = 0;
        double score = 0;
        for(HashTable table : field) {
            Set<String> bothKeySets = new TreeSet<String>(ht.keySet());
            bothKeySets.addAll(table.keySet());
            //System.out.println(bothKeySets);
            double dot = 0;
            double mag1 = 0;
            double mag2 = 0;
            for (String key : bothKeySets) {
                dot += ht.get(key) * table.get(key);
                mag1 += Math.pow(ht.get(key), 2);
                mag2 += Math.pow(table.get(key), 2);
            }

            mag1 = Math.sqrt(mag1);
            mag2 = Math.sqrt(mag2);

            score = getSimilarityScore(dot, (mag1 * mag2));
            System.out.println(score + " "  + table.getUrl());
            if(score > highScore) {
                highScore = score;
                closestTo = table;
            }
        }
        System.out.println("Score: " + highScore);
        System.out.println("Closest to " + closestTo.getUrl());

        return closestTo;
    }

    private static double cosineD(double num, double den){
        return Math.acos(num / den);
    }

    private static double getSimilarityScore(double num, double den){
        return 1/cosineD(num, den);
    }

}
