import java.util.List;

public class ConverterToCSV extends ConverterTo{

    ConverterToCSV(WriterCSV writerCSV, char separator){
        this.writerCSV = writerCSV;
        setSeparator(separator);
    }

    @Override
    public Boolean convertTo(List<List<String>> resultInOldFormat) {
        StringBuilder result = new StringBuilder();

        setStringsInOldFormat(resultInOldFormat);

        for (var i : getStringsInOldFormat()) {
            for (var j : i) {
                result.append(j).append(getSeparator());
            }
            result.deleteCharAt(result.length() - 1);
            result.append("\n");
        }

        return writerCSV.write(result.toString());
    }

    @Override
    List<List<String>> screen(List<List<String>> result) {
        for (var i : result) {
            for (var j : i) {
                j = j.replace("\"", "\"\"");
                if (j.contains(",") || j.contains("\n") || j.contains("\""))
                    j = "\"" + j + "\"";
            }
        }
        return result;
    }
}