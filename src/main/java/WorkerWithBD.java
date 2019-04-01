
import java.sql.*;
import java.util.Date;


public class WorkerWithBD {


    private static WorkerWithBD work = new WorkerWithBD();
    private ConnectionDB connectionDB = ConnectionDB.getConnectionDB();

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
                    "VALUES (1, 'кот', 'cat', "+ new Date().getTime() +", 0);";
            stmt.executeUpdate(sql1);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void fillWords(){
        Statement stmt;
        Connection c = connectionDB.getConnection();
        try {
            stmt = c.createStatement();
            ResultSet r1 = stmt.executeQuery("SELECT * FROM WORDS;");
            while (r1.next()) {
                Words.Word word =   Words.getInstance().new Word();
                word.id = r1.getInt("id");
                word.russian = r1.getString("RUSSIAN");
                word.english = r1.getString("ENGLISH");
                word.date = new Date(Long.parseLong(r1.getString("DATE")));
                word.levelKnow = r1.getInt("LEVEL");
                Words.getInstance().addWord(word);
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
