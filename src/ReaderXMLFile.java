import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderXMLFile extends ReaderXML{

    private List<String> lines = new ArrayList();
    private List<Integer> spasec = new ArrayList();
    private BufferedReader readerFile;

    public ReaderXMLFile(String pathXMLFile){
        if (pathXMLFile != null)
            try {
                readerFile = new BufferedReader(new FileReader(pathXMLFile));
                setLists();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        else throw new NullPointerException();
    }

    private void setLists() throws IOException {

        String line;

        while ((line = readerFile.readLine()) != null) {

            line = countSpaces(line);

            lines.add(line);
        }
    }

    private String countSpaces(String st){

        StringBuilder line = new StringBuilder(st);
        int counter = 0;

        for (int i = 0; line.charAt(i) == ' '; i++) {
            counter++;
        }

        spasec.add(counter);

        return line.substring(counter);
    }

    @Override
    public List<String> getListLines() {
        return lines;
    }

    @Override
    public List<Integer> getListSpaces() {
        return spasec;
    }
}
