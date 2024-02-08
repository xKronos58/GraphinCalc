package com.example.gcalc.Launchers;

import com.example.gcalc.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class readOptions {
    Path filePath;
    static List<String> options;
    public readOptions(String filePath) throws IOException {
        this.filePath = util.convertFileToPath(filePath);
        if(!Files.exists(this.filePath)) {
            Files.createFile(this.filePath);
            defaults();
        }
        options = util.readFile(filePath);
        if(options.isEmpty()) {
            defaults();
            options = util.readFile(filePath);
        }
    }

    public double getAccuracy() {
        return Double.parseDouble(options.get(0).substring(util.until(0, options.get(0), '=')+ 1));
    }

    public void setAccuracy(double accuracy) throws IOException {
        // Replace everything after the '=' and set to the changed accuracy.
        StringBuilder acc = new StringBuilder().append(options.get(0).substring(util.until(0, options.get(0), '=') + 1)).append(accuracy);
        util.replaceLine(this.filePath, 1);
    }

    private void defaults() throws IOException {
        util.writeFile("options.txt", """
                accuracy=0.005
                scaleX=15
                scaleY=7.5
                """);
    }
}
