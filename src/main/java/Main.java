import java.util.Date;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {

        WorkerWithBD.getWork().fillWords();

        Application app = new Application();
        app.createWindow();
    }
}
