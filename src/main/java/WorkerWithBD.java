

import java.sql.*;
import java.util.*;
import java.util.Date;


public class WorkerWithBD {


    private static WorkerWithBD work = new WorkerWithBD();
    private ConnectionDB connectionDB = ConnectionDB.getConnectionDB();

    private Map<Integer, Word> words = new HashMap<>();
    private List<Word> sortWords = new ArrayList<>();

    public void fillWords(){
        Statement stmt;
        Connection c = connectionDB.getConnection();
        try {
            stmt = c.createStatement();
            ResultSet r1 = stmt.executeQuery("SELECT * FROM WORDS;");
            while (r1.next()) {
                Word word = new Word();
                word.id = r1.getInt("id");
                word.russian = r1.getString("RUSSIAN");
                word.english = r1.getString("ENGLISH");
                GregorianCalendar g =  new GregorianCalendar();
                g.setTime(new Date(Long.parseLong(r1.getString("DATE"))));
                word.date =g;
                word.levelKnow = r1.getInt("LEVEL");
                words.put(word.id, word);
                sortWords.add(word);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateWords(){

    }

    private WorkerWithBD() {
        if(!isExistDB()){
            createDB();
        }
    }

    public static WorkerWithBD getWork() {
        return work;
    }

    private boolean isExistDB() {
        boolean isExist = true;
        Statement stmt;
        Connection c = connectionDB.getConnection();
        try {
            stmt = c.createStatement();
            ResultSet r1 = stmt.executeQuery("SELECT * FROM WORDS;");
            while (r1.next()) {
                r1.getInt("id");
                r1.getString("RUSSIAN");
                r1.getString("ENGLISH");
                r1.getString("DATE");
                r1.getInt("LEVEL");
            }

            stmt.close();
        } catch (SQLException e) {
            isExist = false;
            e.printStackTrace();
        }
        return isExist;
    }

    private void createDB() {
        Statement stmt;
        Connection c = connectionDB.getConnection();
        try {
            stmt = c.createStatement();

            String sql = "CREATE TABLE WORDS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " RUSSIAN           TEXT    NOT NULL," +
                    " ENGLISH           TEXT NOT NULL," +
                    " DATE              TEXT NOT NULL," +
                    " LEVEL             INT NOT NULL)";
            stmt.executeUpdate(sql);

            String sql1 = "INSERT INTO WORDS (ID,RUSSIAN,ENGLISH, DATE, LEVEL)" +
                    "VALUES (1, 'кот', 'cat', "+ new Date().getTime() +", 1);";
            stmt.executeUpdate(sql1);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getMaxId() {
        int a = 0;
        String sql = null;
        sql = "SELECT * FROM WORDS ORDER BY ID DESC LIMIT 1";
        Statement stmt;
        try {
            stmt = connectionDB.getConnection().createStatement();
            ResultSet r1 = stmt.executeQuery(sql);
            while (r1.next()) {
                a = r1.getInt("id");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public void addWord(String russian, String english){
        Word word = new Word();
        word.id = Identifier.getInstance().getId();
        word.russian = russian;
        word.english = english;
        GregorianCalendar g = new GregorianCalendar();
        g.set(GregorianCalendar.DAY_OF_YEAR, 1);
        word.date = g;
        word.levelKnow = 1;
        words.put(word.id, word);
        sortWords.add(word);
    }

    public List<Word> getListForTrain(){
        List<Word> list = new ArrayList<>();
        int currentDay = new GregorianCalendar().get(GregorianCalendar.DAY_OF_YEAR);
        sortWords.sort(Comparator.comparingInt(o -> o.levelKnow));
        for(int i = sortWords.size() - 1 ; i >= 0 ; i --){
            Word word = sortWords.get(i);
            System.out.println(word);
            if(word.date.get(GregorianCalendar.DAY_OF_YEAR) != currentDay){
                list.add(word);
            }
            if(list.size() == 30)break;
        }
        return list;
    }
}
