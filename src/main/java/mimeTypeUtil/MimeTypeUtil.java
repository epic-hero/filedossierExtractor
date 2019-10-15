package mimeTypeUtil;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;

public class MimeTypeUtil {

    private static MimeTypeRepository mimeTypeRepository = new MimeTypeRepository();

    public static String getExtension(String mimeType) {
        Iterator<String> itr = mimeTypeRepository
                .getMimeTypes().getOrDefault(mimeType, Collections.<String>emptyList()).iterator();
        return itr.hasNext() ? itr.next() : null;
    }

    public static String guessMimeTypeFromByteArray(byte[] data) {
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
        try {
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            return mimeType;
        } catch (IOException e) {
            throw new RuntimeException("Bad byte array data: " + e);
        }
    }

    public static String guessMimeTypeFromFile(File file) {
        return guessMimeType(file.toPath());
    }

    private static String guessMimeType(Path path) {
        try {
            return Files.probeContentType(path);
        } catch (IOException e) {
            throw new RuntimeException("Error guessing mime type: " + e);
        }
    }
}
