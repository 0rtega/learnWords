

public class Identifier {

    private static Identifier instance=  new Identifier();
    private Identifier(){}

    private int number = 0;

    public void setNumber(int number){
        this.number = number;
    }

    public int getId(){
        number++;
        return number;
    }

    public static Identifier getInstance(){
        return  instance;
    }


}
