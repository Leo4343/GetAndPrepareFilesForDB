import java.util.List;

abstract class ConverterTo {

    public List<List<String>> getStringsInOldFormat() {
        return StringsInOldFormat;
    }

    public void setStringsInOldFormat(List<List<String>> stringsInOldFormat) {
        StringsInOldFormat = screen(stringsInOldFormat);
    }

    public char getSeparator() {
        return separator;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    private List<List<String>> StringsInOldFormat;
    private char separator;
    protected WriterCSV writerCSV;


    public abstract Boolean convertTo(List<List<String>> resultInOldFormat);
    abstract List<List<String>> screen(List<List<String>> result);
}
