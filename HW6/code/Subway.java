import java.io.*;

public class Subway {
    private static void command() {

    }

    private static void readAndSaveData(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    public static void main(String[] args) {
        String fileName = args[0];
        readAndSaveData(fileName);

    }
}
