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
        return compareTo(w, 0);
    }

    public int compareTo(WordCount w, int i){

        if (i > w.getWord().length() - 1 && i > word.length() - 1)
            return 0;
        else if(i > w.getWord().length() - 1 ) return -1;
        else if(i > word.length() - 1) return 1;

        while(word.charAt(i) == w.word.charAt(i)) {
            return compareTo(w, i + 1);
        }

        if(word.charAt(i) > w.getWord().charAt(i)) return 1;
        else return -1;
    }
}
