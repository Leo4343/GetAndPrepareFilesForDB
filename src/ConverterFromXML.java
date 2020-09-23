import java.util.ArrayList;
import java.util.List;

public class ConverterFromXML {

    private ConverterTo converterTo;
    private ReaderXML reader;

    public ConverterFromXML(ConverterTo converterTo, ReaderXML reader){
        this.converterTo = converterTo;
        this.reader = reader;
    }

    public Boolean convertAndWrite(){
        List<String> listLines = reader.getListLines();
        List<Integer> listSpaces = reader.getListSpaces();
        List<List<String>> result = new ArrayList<>();
        StringBuilder value = new StringBuilder();

        result.add(new ArrayList<>());

        int indexForResult = 0;
        Integer spacesSubRootObj = null;

        for (int i = 0; i < listLines.size(); i++) {

            String line = listLines.get(i);

            if (line.contains("<!"))
                continue;

            if (listSpaces.get(i) == 0)
                continue;

            if (spacesSubRootObj == null)
                if (!getNameParam(listLines.get(i)).equals(getNameParam(listLines.get(i + 1)))) {
                    if (listSpaces.get(i).equals(listSpaces.get(i + 1))) {
                        spacesSubRootObj = 0;
                    } else {
                        spacesSubRootObj = listSpaces.get(i);
                    }
                } else {
                    spacesSubRootObj = listSpaces.get(i);
                }


                if (line.startsWith("</"))
                    continue;

                if (line.contains("=\""))

                    for (int j = 0; line.charAt(j) != '>'; j++) {
                        if (line.charAt(j) == '\"')
                            for (int p = j + 1; line.charAt(p) != '\"'; p++) {
                                value.append(line.charAt(p));
                                j = p;
                            }
                        result.get(indexForResult).add(value.toString());
                    }
                if (!listSpaces.get(i).equals(spacesSubRootObj))
                    result.get(indexForResult).add(getValueFromLine(listLines, i));
            }

            if (spacesSubRootObj.equals(listSpaces.get(i))) {
                result.add(new ArrayList<>());
                indexForResult++;
            }
        }

        converterTo.convertTo(screen(result));
        return true;
    }

    private String getValueFromLine(List<String> listLines, int indexList){

        boolean flag = true;

        StringBuilder value = new StringBuilder();

        while (flag) {
            for (int i = 1; i < listLines.get(indexList).length(); i++) {
                if (listLines.get(indexList).charAt(i) == '<')
                    flag = false;
                value.append(listLines.get(indexList).charAt(i));
            }
            indexList++;
        }

        return value.toString();
    }

    private String getNameParam(String line){
        StringBuilder nameParam = new StringBuilder();

        for (int i = 1; line.charAt(i) == ' ' && line.charAt(i) == '>'
                && line.charAt(i) == '/'; i++) {
            nameParam.append(line.charAt(i));
        }

        return nameParam.toString();
    }

    private List<List<String>> screen(List<List<String>> result){
        for (var i : result) {
            for (var j : i) {
                j = j.replace("&quot;", "\"");
                j = j.replace("&apos;", "'");
                j = j.replace("&amp;", "&");
                j = j.replace("&gt;", ">");
                j = j.replace("&lt;", "<");
            }
        }

        return result;
    }
}
