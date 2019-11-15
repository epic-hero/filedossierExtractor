
import metadata.entities.MetadataExtractor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class PdfMetadataExtractorTest {

    @Test
    public void PdfExtractorTest() throws URISyntaxException, IOException {

        byte[] pdfDocument = Files.readAllBytes(Paths.get(Objects.requireNonNull(this.getClass().getClassLoader().getResource(
                "example.pdf")).toURI()));
        MetadataExtractor metadataExtractor = MetadataExtractorFactory.getMetadata(pdfDocument, "application/pdf");
        Assert.assertEquals("D:20190725101641+04'00'",
                metadataExtractor.getProperty("CreationDate"));
        Assert.assertEquals("iText® 5.3.4 ©2000-2012 1T3XT BVBA (AGPL-version)",
                metadataExtractor.getProperty("Producer"));
        Assert.assertEquals("D:20190725101641+04'00'",
                metadataExtractor.getProperty("ModDate"));
    }
}
