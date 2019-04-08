


public class Main {

    public static void main(String[] args) {
        try {
            WorkerWithBD.getWork().fillWords();
            Identifier.getInstance().setNumber(WorkerWithBD.getWork().getMaxId());
            Application app = new Application();
            app.createWindow();
        }finally {
            WorkerWithBD.getWork().updateWords();
        }
    }
}
