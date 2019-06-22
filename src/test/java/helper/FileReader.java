package helper;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class FileReader {

    private static final Logger LOG = LoggerFactory.getLogger(FileReader.class);

    public JSONObject generatePageContent() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(
                    new YAMLMapper().readTree(readResourceFile("PageContents/ApiTestExpectedContent.yml")).toString());
        } catch (IOException exception) {
            LOG.error("IOException " + exception);
        }
        return jsonObject;
    }




    private String readResourceFile(String filePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            return IOUtils.toString(classLoader.getResourceAsStream(filePath));
        } catch (IOException exception) {
            LOG.error("Exception: " + exception);
        }
        return filePath;
    }

}
