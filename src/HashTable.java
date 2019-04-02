import java.util.HashSet;
import java.util.Set;

class HashTable {

    static final class Node {

        final String word;
        int freq;
        int hash;
        Node next;


        Node(String word, int freq, Node next) {
            this.word = word;
            this.freq = freq;
            this.next = next;
        }
    }
    static final int INITIAL_CAP = (16);
    String url;
    int count = 0;
    int resizes = 0;
    Node[] table = new Node[INITIAL_CAP];

    int hashCode(String word){
        return word.hashCode() ^ word.length() * 31;
    }

    int get(String word) {
        int h = hashCode(word);
        int i = h & (this.table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (h == hashCode(e.word) && e.word.equals(word))
                return e.freq;
        }
        return -1;
    }

    void addOne(String word) {
        int h = hashCode(word);
        int i = h & (this.table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (word.equals(e.word)) {
                e.freq++;
                count++;
                return;
            }
        }
        Node q = new Node(word, 1, table[i]);
        table[i] = q;
        if(++count > table.length >> 2 + table.length >> 1){
            resize();
        }

    }

    private void resize() {
        resizes++;
        Node[] newtable = new Node[table.length * 2];
        for (int i = 0; i < table.length; i++) {
            Node list = table[i];
            while (list != null) {
                Node next = list.next;
                int h = hashCode(list.word);
                int j = h & (newtable.length - 1);
                list.next = newtable[j];
                newtable[j] = list;
                list = next;
            }
        }
        table = newtable;
    }

    void printAll() {
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                System.out.println(e.word + " : "+ e.freq);
            }
        }
        System.out.println(count + " items");
        System.out.println("resized " + resizes + " times");
    }

    void printAllWords(){
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                System.out.println(e.word);
            }
        }
    }

    Set<String> keySet(){
        Set<String> keys = new HashSet<String>();
        for(int i = 0; i < table.length; i++){
            if(table[i] != null) {
                for (Node e = table[i]; e != null; e = e.next) {
                    keys.add(e.word);
                }
            }
        }
        return keys;

    }

    int collisions(){

        int collisions = 0;

        for(int i = 0; i < table.length; i++){
            if(table[i] != null){
                for(Node e = table[i]; e != null; e = e.next){
                    if(e.next != null)
                        collisions++;
                }
            }
        }
        return collisions;
    }

    void setUrl(String url){
        this.url = url;
    }

    String getUrl(){
        return this.url;
    }

}
