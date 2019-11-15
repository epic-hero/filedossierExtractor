
import metadata.entities.MetadataExtractor;
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
        MetadataExtractor metadataExtractor = MetadataExtractorFactory.getMetadata(rawImage);
        imageExtractorEquals(metadataExtractor);
        metadataExtractor = MetadataExtractorFactory.getMetadata(rawImage, "image/jpeg");
        imageExtractorEquals(metadataExtractor);
    }

    private void imageExtractorEquals(MetadataExtractor metadataExtractor) {
        Assert.assertEquals("doctree:11f462ebdb14a5673ff41a5c75c5176552fad343:2:2",
                metadataExtractor.getProperty("barcode"));
        Assert.assertEquals("16:114:101:129;110:114:195:129;16:267:101:284;110:267:195:284",
                metadataExtractor.getProperty("signatures"));

    }
}
