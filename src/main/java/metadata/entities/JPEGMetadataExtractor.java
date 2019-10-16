package metadata.entities;

import com.adobe.internal.xmp.XMPException;
import com.adobe.internal.xmp.XMPMeta;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.xmp.XmpDirectory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JPEGMetadataExtractor implements MetadataExtractor {

    private byte[] rawImage;

    public JPEGMetadataExtractor(byte[] rawImage) {
        this.rawImage = rawImage;
    }

    @Override
    public String getProperty(String propName) {
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(rawImage));

        Metadata metadata;
        try {
            metadata = ImageMetadataReader.readMetadata(is);
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException("Read metadata error: " + e);
        }

        XmpDirectory directory = metadata.getFirstDirectoryOfType(XmpDirectory.class);

        XMPMeta xmpMeta = directory.getXMPMeta();

        try {
            return xmpMeta.getPropertyString("http://ns.adobe.com/xap/1.0/",
                    "xmp:" + propName);
        } catch (XMPException e) {
            throw new RuntimeException("XMP parse error: " + e);
        }
    }

    @Override
    public Map<String, String> asMap() {
        // Do nothing
        return null;
    }
}
