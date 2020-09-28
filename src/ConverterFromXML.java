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

            if (spacesSubRootObj != null) {
                if (spacesSubRootObj.equals(listSpaces.get(i)) && listLines.get(i).charAt(1) == '/') {
                    result.add(new ArrayList<>());
                    indexForResult++;
                }
            } else {
                if (!getNameParam(listLines.get(i)).equals(getNameParam(listLines.get(i + 1)))) {
                    if (listSpaces.get(i).equals(listSpaces.get(i + 1))) {
                        spacesSubRootObj = 0;
                    } else {
                        spacesSubRootObj = listSpaces.get(i);
                    }
                } else {
                    spacesSubRootObj = listSpaces.get(i);
                }
            }

            if (line.startsWith("</"))
                continue;

            if (line.startsWith("<")) {

                if (line.contains("=\""))

                    for (int j = 0; line.charAt(j) != '>'; j++) {
                        if (line.charAt(j) == '\"')
                            for (int p = j + 1; line.charAt(p) != '\"'; p++) {
                                value.append(line.charAt(p));
                                j = p + 1;
                            }
                        if (!value.toString().equals("")) {
                            result.get(indexForResult).add(value.toString());
                            value = new StringBuilder();
                        }
                    }
                if (!listSpaces.get(i).equals(spacesSubRootObj))
                    result.get(indexForResult).add(getValueFromLine(listLines, i));
            }
        }

        converterTo.convertTo(screen(result));
        return true;
    }

    private String getValueFromLine(List<String> listLines, int indexList){

        boolean flag = true;
        int startIndex = 1;

        StringBuilder value = new StringBuilder();

        for (; startIndex < listLines.get(indexList).length(); startIndex++) {
            if (listLines.get(indexList).charAt(startIndex) == '>')
                if (listLines.get(indexList).length() == startIndex + 1) {
                    startIndex = 0;
                } else {
                    startIndex++;
                    break;
                }
        }

        while (flag) {
            for (; startIndex < listLines.get(indexList).length(); startIndex++) {
                if (listLines.get(indexList).charAt(startIndex) == '<') {
                    flag = false;
                    break;
                }
                value.append(listLines.get(indexList).charAt(startIndex));
            }
            indexList++;
            startIndex = 0;
        }

        return value.toString();
    }

    private String getNameParam(String line){
        StringBuilder nameParam = new StringBuilder();

        for (int i = 1; line.charAt(i) != ' ' && line.charAt(i) != '>'
                && line.charAt(i) != '/'; i++) {
            nameParam.append(line.charAt(i));
        }

        return nameParam.toString();
    }

    private List<List<String>> screen(List<List<String>> result){

        for (int k = 0; k < result.size(); k++) {
            List<String> i = result.get(k);
            for (int i1 = 0; i1 < i.size(); i1++) {
                String j = i.get(i1);

                j = j.replace("&quot;", "\"");
                j = j.replace("&apos;", "'");
                j = j.replace("&amp;", "&");
                j = j.replace("&gt;", ">");
                j = j.replace("&lt;", "<");

                i.set(i1, j);
            }
        }

        return result;
    }
}