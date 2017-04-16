package IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputHandler {

    private String fileName;
    private BufferedReader br;

    public InputHandler(String fileName){
        this.fileName = fileName;

        try {
            FileReader fr = new FileReader(fileName);
            this.br = new BufferedReader(fr);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getLine(){
        try {
            return br.readLine();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void closeFile() {
        try {
            this.br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
