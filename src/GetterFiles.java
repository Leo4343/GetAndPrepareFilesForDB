import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GetterFiles {

    private List<String> result = new ArrayList<>();

    public List<String> getFiles(String path, String typeFiles){
        try (Stream<Path> walk = Files.walk(Paths.get(path))) {

            result = walk.map(Path::toString)
                    .filter(f -> f.endsWith(typeFiles)).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (result == null)
            return null;
        return result;
    }
}
