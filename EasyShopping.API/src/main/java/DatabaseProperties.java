import java.io.*;
import java.util.Properties;

class DatabaseProperties {
    DatabaseProperties() {
    }

    private InputStream inputStream;

    public String getValue(String value) throws IOException {
        String result = "";
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            result += prop.getProperty(value);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }


}
