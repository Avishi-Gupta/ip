package koala;

import java.io.IOException;

public class Koala {
    public static void main(String[] args) throws KoalaException, IOException {
        // TaskList taskList = new TaskList("../data/koala.txt");
        Parser parser = new Parser();
        parser.run();
   }
}
