
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
//                System.out.println( r1.getInt("id"));
//                System.out.println(r1.getString("RUSSIAN"));
//                System.out.println( r1.getString("ENGLISH"));
//                System.out.println(new Date(Long.parseLong( r1.getString("DATE"))));
//                System.out.println(r1.getInt("LEVEL"));
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
        Connection c = connectionDB.getConnection();
        try {
            try {
                PreparedStatement statement = c.prepareStatement(
                        "INSERT INTO WORDS (ID, RUSSIAN, ENGLISH, DATE, LEVEL) " +
                                "VALUES (?, ?, ?, ?, ?)");
                statement.setInt(1, Identifier.getInstance().getId());
                statement.setString(2, russian);
                statement.setString(3, english);
                statement.setString(4, new Date().getTime() + "");
                statement.setInt(5, 1);
                statement.executeUpdate();
            } catch (SQLException ex) {
                c.rollback();
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Word> getListForTrain(){
        List<Word> list = new ArrayList<>();
        Statement stmt;
        Connection c = connectionDB.getConnection();
        try {
            stmt = c.createStatement();
            ResultSet r1 = stmt.executeQuery("SELECT * FROM WORDS;");
            while (r1.next()) {
                Word w = new Word();
                w.id = r1.getInt("id");
                w.russian = r1.getString("RUSSIAN");
                w.english = r1.getString("ENGLISH");
                w.date = new Date(Long.parseLong(r1.getString("DATE")));
                w.levelKnow =  r1.getInt("LEVEL");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
