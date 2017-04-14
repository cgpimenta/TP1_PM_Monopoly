package IO;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by cgpimenta on 09/04/17.
 */
public class InputHandler {

    private String fileName;
    private BufferedReader br;

    public InputHandler(String fileName){
        this.fileName = fileName;
        try {
            FileReader fr = new FileReader(fileName);
            this.br = new BufferedReader(fr);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getLine(){
        try {
            return br.readLine();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
