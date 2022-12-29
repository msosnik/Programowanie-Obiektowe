package agh.ics.oop.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SimulationPropertyFile {

    Properties properties;

    public SimulationPropertyFile(File filePath) throws IOException {
        properties = new Properties();
        try (InputStream is = new FileInputStream(filePath)) {
            properties.load(is);
        }
    }

    public int getIntValue(ESimulationProperty propertyName) {
        String s = properties.getProperty(propertyName.toString());
        return Integer.parseInt(s);
    }


}
