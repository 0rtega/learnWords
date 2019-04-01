import java.util.*;


public class Words {

    private static Words instance = new Words();
    private  Words(){}

    public static Words getInstance(){
        return instance;
    }

    private Map<Integer, Word> words = new HashMap<>();

    public void addWord(Word word){
        words.put(word.id, word);
        System.out.println(words.size());
    }

    public void addWord(String russian, String english){

    }

    public void addWord(int id, String russian, String english, Date date, int level){

    }

    public List<Word> getListWordsFoeExecizeByLevel(int level){
        return new ArrayList<>();
    }

    public class Word{
        public int id;
        public String russian;
        public String english;
        public Date date;
        public int levelKnow;
    }
}
