import java.io.File;
import java.util.List;

public class ConvertFromXMLToCSVFromDirAndWriteDB {

    private String  pathNetworkDrive;
    private String pathForDB;
    private WriterCSV writer;

    public ConvertFromXMLToCSVFromDirAndWriteDB(String pathForDB, String pathNetworkDrive,
                                                WriterCSV writerCSV) {
        this.pathNetworkDrive = pathNetworkDrive;
        this.pathForDB = pathForDB;
        writer = writerCSV;
    }
    
    public Boolean convert(){

        GetterFiles getterFiles = new GetterFiles();
        ConverterToCSV converterToCSV = new ConverterToCSV(writer,',');
        ReaderXMLFile reader;
        ConverterFromXML converterFromXML;

        List<String> files = getterFiles.getFiles(pathNetworkDrive, ".xml");

        if (files == null) {
            System.out.println("no files");
            return false;
        }

        for (var i : files) {

            reader = new ReaderXMLFile(i);
            converterFromXML = new ConverterFromXML(converterToCSV, reader);

            if (!converterFromXML.convertAndWrite())
                return false;
        }

        return true;
    }
}
