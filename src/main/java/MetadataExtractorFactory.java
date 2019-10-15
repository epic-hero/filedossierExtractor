import metadata.entities.JPEGMetadataExtractor;
import metadata.entities.MetadataExtractor;
import metadata.entities.PDFMetadataExtractor;

public final class MetadataExtractorFactory {

    @SuppressWarnings("unchecked")
    public static <T> T getMetadata(MetadataExtractor extractor) {
        if (extractor instanceof PDFMetadataExtractor) return (T) extractor.asMap();
        if (extractor instanceof JPEGMetadataExtractor) return (T) extractor.getProperty();
        else throw new RuntimeException("Invalid extractor");
    }
}
