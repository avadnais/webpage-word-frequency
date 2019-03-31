public class WordCount implements Comparable<WordCount>{

    private String word;
    private int count;

    public WordCount(String word){
        this.word = word;
        this.count = 1;
    }

    public WordCount(String word, int count){
        this.word = word;
        this.count = count;
    }

    public String getWord(){
        return word;
    }
    public int getCount(){
        return count;
    }
    public void addOne(){
        count++;
    }

    public int compareTo(WordCount w) {

        if(word.charAt(0) == w.word.charAt(0)) return 0;
        else if(word.charAt(0) > w.word.charAt(0)) return 0;
        else return -1;

    }
}
