import java.util.*;

public class Word {
        public int id;
        public String russian;
        public String english;
        public GregorianCalendar date;
        public int levelKnow;

        @Override
        public String toString() {
                return "Word{" +
                        "id=" + id +
                        ", russian='" + russian + '\'' +
                        ", english='" + english + '\'' +
                        ", date=" + date.get(GregorianCalendar.DAY_OF_YEAR) +
                        ", levelKnow=" + levelKnow +
                        '}';
        }
}

