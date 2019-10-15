import metadata.entities.JPEGMetadataExtractor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ImageMetadataExtractorTest {

    @Test
    public void ImageExtractorTest() throws URISyntaxException, IOException {
        byte[] rawImage = Files.readAllBytes(Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("image.jpg")).toURI()));
        Assert.assertEquals("doctree:11f462ebdb14a5673ff41a5c75c5176552fad343:2:2",
                MetadataExtractorFactory.getMetadata(new JPEGMetadataExtractor(rawImage, "barcode")));
        Assert.assertEquals("16:114:101:129;110:114:195:129;16:267:101:284;110:267:195:284",
                MetadataExtractorFactory.getMetadata(new JPEGMetadataExtractor(rawImage, "signatures")));
    }
}
