package com.ninjamind.maven.plugin.util;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This class util have several method to manage files
 */
public class ConfmanFileUtils {

    /**
     * Write the properties in a property file on the filesystem
     * @throws MojoExecutionException
     */
    public static void writePropertiesInFile(String propertiespath,
                               String propertiesprefix,
                               String env,
                               String instance,
                               Log logger,
                               Charset encoding,
                               List<String> properties) throws MojoExecutionException{
        BufferedWriter writer = null;
        try {
            //We see if the file exist
            Path path = Paths.get(propertiespath, String.format("%s%s%s.properties", propertiesprefix, env, instance != null ? "-" + instance : ""));

            File file = path.toFile();
            if(file==null){
                throw new MojoExecutionException(String.format("Path file is invalid [%s]", path));
            }

            //if file existe we delete it
            if (file.exists()) {
                FileUtils.forceDelete(file);
                logger.info(String.format("The file %s is deleted and a new one will be created with the parameters values linked to the version specified like plugin argument", path.getFileName()));
            }

            //we see if directory is present
            if(!file.getParentFile().exists()){
                FileUtils.mkdir(file.getParent());
            }

            //Create the file
            logger.info(String.format("The file %s is created with the encoding %s", path.getFileName(), encoding));
            writer = Files.newBufferedWriter(path, encoding, StandardOpenOption.CREATE_NEW);
            writer.write("#File generated with the maven plugin Confman on " + DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE).format(new Date()));
            writer.newLine();
            for (String property : properties) {
                writer.write(property);
                writer.newLine();
            }
        }
        catch (IOException e) {
            throw new MojoExecutionException("error when attempt to write properties in a fil");
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new MojoExecutionException("error when attempt to close writer");
                }
            }
        }
    }
}
