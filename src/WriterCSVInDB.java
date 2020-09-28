public class WriterCSVInDB extends WriterCSV {

    WriterCSVInDB(String PATH, String USER, String PASSWORD){
        this.PATH = PATH;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }

    String PATH;
    String USER;
    String PASSWORD;
    // ...

    @Override
    Boolean write(String data) {
        // setting data im DB
        return true;
    }
}
