package IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputHandler {

    private String fileName;
    private BufferedWriter bw;

    public OutputHandler(String fileName) {
        this.fileName = fileName;

        try {
            FileWriter fw = new FileWriter(fileName);
            this.bw = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putLine(String line) {
        try {
            this.bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFile() {
        try {
            if (this.bw != null) {
                this.bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
