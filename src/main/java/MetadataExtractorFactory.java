
import metadata.entities.JPEGMetadataExtractor;
import metadata.entities.MetadataExtractor;
import metadata.entities.PDFMetadataExtractor;
import mimeTypeUtil.MimeTypeUtil;

import java.awt.datatransfer.MimeTypeParseException;

public final class MetadataExtractorFactory {

    public static MetadataExtractor getMetadata(byte[] file) {
        return getExtractorOfMimeType(MimeTypeUtil.guessMimeTypeFromByteArray(file), file);
    }

    public static MetadataExtractor getMetadata(byte[] file, String mimeType) {
        return getExtractorOfMimeType(mimeType, file);
    }

    private static MetadataExtractor getExtractorOfMimeType(String mimeType, byte[] file) {
        try {
            switch (mimeType) {
                case ("image/jpeg"):
                    return new JPEGMetadataExtractor(file);
                case ("application/pdf"):
                    return new PDFMetadataExtractor(file);
                default:
                    throw new MimeTypeParseException("Unknown mime type");
            }
        } catch (MimeTypeParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
