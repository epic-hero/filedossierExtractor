package metadata.entities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PDFMetadataExtractor implements MetadataExtractor {

    private byte[] document;

    public PDFMetadataExtractor(byte[] document) {
        this.document = document;
    }

    @Override
    public String getProperty(String prop) {
        // Do nothing
        return null;
    }

    @Override
    public Map<String, String> asMap() {
        try {
            PDDocument pdfDocument = PDDocument.load(document);
            System.out.println(pdfDocument.getDocumentInformation().getMetadataKeys());
            PDDocumentInformation info = pdfDocument.getDocumentInformation();

            pdfDocument.close();

            Map<String, String> metadata = new HashMap<>();

            Set<String> keys = info.getMetadataKeys();
            keys.forEach(key -> metadata.put(key, info.getCustomMetadataValue(key)));

            return metadata;
        } catch (IOException ex) {
            throw new RuntimeException("Bad document: " + ex);
        }
    }
}
