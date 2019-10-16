import metadata.entities.MetadataExtractor;
import metadata.entities.PDFMetadataExtractor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class PdfMetadataExtractorTest {

    @Test
    public void PdfExtractorTest() throws URISyntaxException, IOException {

        byte[] pdfDocument = Files.readAllBytes(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource(
                "example.pdf")).toURI()));
        MetadataExtractor metadataExtractor = MetadataExtractorFactory.getMetadata(pdfDocument, "application/pdf");
        Map<String, String> extractedMetadata = metadataExtractor.asMap();
        String firstExpectedValue = "D:20190725101641+04'00'";
        Assert.assertEquals(firstExpectedValue, extractedMetadata.get("CreationDate"));
    }
}
