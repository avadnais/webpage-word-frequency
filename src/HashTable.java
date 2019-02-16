import java.util.HashSet;
import java.util.Set;

class HashTable {
    static final int INITIAL_CAP = (16);

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
    int count = 0;
    int resizes = 0;
    Node[] table = new Node[INITIAL_CAP];

    int hashCode(String word){
        int hash = word.length();

        for(int i = 0; i < word.length(); i++){
            hash = (hash << 5) - hash;
        }

        return hash;
    }

    int get(String word) {
        int h = word.hashCode();
        int i = h & (this.table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (h == e.word.hashCode() && e.word.equals(word))
                return e.freq;
        }
        return -1;
    }

    void addOne(String word) {
        int h = word.hashCode();
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
                int h = list.word.hashCode();
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
            if(table[i] != null)
                keys.add(table[i].word);
        }
        return keys;

    }


  /*  public void writeObject(ObjectOutputStream s) throws IOException {
        //s.defaultWriteObject(this);

        for(int i = 0; i < table.length; ++i){
            for(Node p = table[i]; p != null; p = p.next){
                s.write(p.name);
                s.write(p.profile);
            }
        }
    }*/
}
