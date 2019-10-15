package mimeTypeUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MimeTypeRepository {

    //private static final URI MIME_TYPES_URI = URI.create("classpath:mimetype/mime.types");
    private final Map<String, List<String>> mimeTypes;

    MimeTypeRepository() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(MimeTypeUtil.class.getClassLoader().getResource("mimetype/mime.types").toURI()));
//            lines = Files.readAllLines(Paths.get("mimetype/mime.types"));
        } catch (IOException | URISyntaxException ex) { //URISyntaxException
            throw new RuntimeException(ex);
        }
        mimeTypes = lines.stream()
                .filter(l -> !l.startsWith("#"))
                .map(l -> l.split("\\s+"))
                .collect(Collectors.toMap(l -> l[0], l -> Arrays.asList(Arrays.copyOfRange(l, 1, l.length))));
    }

    public Map<String, List<String>> getMimeTypes() {
        return mimeTypes;
    }

}
