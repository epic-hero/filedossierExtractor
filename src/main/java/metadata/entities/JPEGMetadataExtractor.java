package metadata.entities;

import com.adobe.internal.xmp.XMPException;
import com.adobe.internal.xmp.XMPMeta;
import com.adobe.internal.xmp.properties.XMPPropertyInfo;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.xmp.XmpDirectory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JPEGMetadataExtractor implements MetadataExtractor {

    private byte[] rawImage;
    public String prefix = "xmp";
    private Map<String, String> metadata = new HashMap<>();

    public JPEGMetadataExtractor(byte[] rawImage) {
        this.rawImage = rawImage;
        setProperty();
    }

    @Override
    public String getProperty(String propName) {
        if (!prefix.isEmpty()) {
            propName = prefix + ":" + propName;
        }
        return metadata.get(propName);
    }

    @Override
    public Map<String, String> asMap() {
        return metadata;
    }

    private Map<String, String> setProperty() {
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(rawImage));

        Metadata metadates;
        try {
            metadates = ImageMetadataReader.readMetadata(is);
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException("Read metadata error: " + e);
        }

        XmpDirectory directory = metadates.getFirstDirectoryOfType(XmpDirectory.class);

        XMPMeta xmpMeta = directory.getXMPMeta();

        try {
            for (Iterator i = xmpMeta.iterator(); i.hasNext();) {
                XMPPropertyInfo prop = (XMPPropertyInfo) i.next();
                String key = prop.getPath();
                String value = prop.getValue();
                metadata.put(key, value);
            }
            return metadata;
        } catch (XMPException e) {
            throw new RuntimeException("XMP parse error: " + e);
        }
    }
}
