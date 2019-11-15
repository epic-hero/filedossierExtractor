package metadata.entities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PDFMetadataExtractor implements MetadataExtractor {

    private byte[] document;
    private Map<String, String> metadata = new HashMap<>();

    public PDFMetadataExtractor(byte[] document) {
        this.document = document;
        setProperty();
    }

    @Override
    public String getProperty(String propName) {
        return metadata.get(propName);
    }

    @Override
    public Map<String, String> asMap() {
        return metadata;
    }

    private Map<String, String> setProperty() {
        try {
            PDDocument pdfDocument = PDDocument.load(document);
            PDDocumentInformation info = pdfDocument.getDocumentInformation();

            pdfDocument.close();

            Set<String> keys = info.getMetadataKeys();
            keys.forEach(key -> metadata.put(key, info.getCustomMetadataValue(key)));

            return metadata;
        } catch (IOException ex) {
            throw new RuntimeException("Bad document: " + ex);
        }
    }
}
