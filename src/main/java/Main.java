import java.util.Date;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args) {
        Identifier.getInstance().setNumber(WorkerWithBD.getWork().getMaxId());
        Application app = new Application();
        app.createWindow();
    }
}
