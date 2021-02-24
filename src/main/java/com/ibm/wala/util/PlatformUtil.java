package com.ibm.wala.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;


/*
    Author: Ali Shokri  (as8308@rit.edu)
    This class is a replacement for com.ibm.wala.util.PlatformUtil to make an adhoc fix on getBootClassPathJars() method
 */

public class PlatformUtil {
    public PlatformUtil() {
    }

    public static boolean onMacOSX() {
        String osname = System.getProperty("os.name");
        return osname.toLowerCase().contains("mac");
    }

    public static boolean onLinux() {
        String osname = System.getProperty("os.name");
        return osname.equalsIgnoreCase("linux");
    }

    public static boolean onWindows() {
        String osname = System.getProperty("os.name");
        return osname.toLowerCase().contains("windows");
    }

    public static boolean onIKVM() {
        return "IKVM.NET".equals(System.getProperty("java.runtime.name"));
    }

    public static String[] getBootClassPathJars() {
        String classpath = null;
        String javaVersion = System.getProperty("java.specification.version");
        if (!javaVersion.equals("1.8")) {

            // Ali: Below try-catch is put in an if condition to cover the situation that this method is called in the run-time (e.g. creating class hierarchy at runtime.
            // jmods folder is not available in the runtime!

            if( Paths.get(System.getProperty("java.home"), "jmods").toFile().exists() ) {
                try {
                    classpath = String.join(File.pathSeparator, (Iterable) Files.list(Paths.get(System.getProperty("java.home"), "jmods")).map(Path::toString).collect(Collectors.toList()));
                } catch (IOException var8) {
                    throw new IllegalStateException(var8);
                }
            }
            else
                classpath = System.getProperty("java.class.path");

        } else {
            classpath = System.getProperty("sun.boot.class.path");
        }

        if (classpath == null) {
            throw new IllegalStateException("could not find boot classpath");
        } else {
            String[] jars = classpath.split(File.pathSeparator);
            ArrayList<String> result = new ArrayList();
            String[] var4 = jars;
            int var5 = jars.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String jar = var4[var6];
                if ((jar.endsWith(".jar") || jar.endsWith(".jmod")) && (new File(jar)).exists()) {
                    result.add(jar);
                }
            }

            return (String[])result.toArray( new String[result.size()] );
        }
    }
}