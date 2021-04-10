[33mcommit 6585d00f48994ae3ebc4ccda6c07515196d5a390[m[33m ([m[1;36mHEAD -> [m[1;32mjwt-test[m[33m, [m[1;31morigin/main[m[33m, [m[1;32mmain[m[33m)[m
Author: Hatung69 <keodeo19@gmail.com>
Date:   Wed Apr 7 22:21:47 2021 +0700

    khoi tao du an!

[1mdiff --git a/.gitignore b/.gitignore[m
[1mnew file mode 100644[m
[1mindex 0000000..549e00a[m
[1m--- /dev/null[m
[1m+++ b/.gitignore[m
[36m@@ -0,0 +1,33 @@[m
[32m+[m[32mHELP.md[m
[32m+[m[32mtarget/[m
[32m+[m[32m!.mvn/wrapper/maven-wrapper.jar[m
[32m+[m[32m!**/src/main/**/target/[m
[32m+[m[32m!**/src/test/**/target/[m
[32m+[m
[32m+[m[32m### STS ###[m
[32m+[m[32m.apt_generated[m
[32m+[m[32m.classpath[m
[32m+[m[32m.factorypath[m
[32m+[m[32m.project[m
[32m+[m[32m.settings[m
[32m+[m[32m.springBeans[m
[32m+[m[32m.sts4-cache[m
[32m+[m
[32m+[m[32m### IntelliJ IDEA ###[m
[32m+[m[32m.idea[m
[32m+[m[32m*.iws[m
[32m+[m[32m*.iml[m
[32m+[m[32m*.ipr[m
[32m+[m
[32m+[m[32m### NetBeans ###[m
[32m+[m[32m/nbproject/private/[m
[32m+[m[32m/nbbuild/[m
[32m+[m[32m/dist/[m
[32m+[m[32m/nbdist/[m
[32m+[m[32m/.nb-gradle/[m
[32m+[m[32mbuild/[m
[32m+[m[32m!**/src/main/**/build/[m
[32m+[m[32m!**/src/test/**/build/[m
[32m+[m
[32m+[m[32m### VS Code ###[m
[32m+[m[32m.vscode/[m
[1mdiff --git a/.mvn/wrapper/MavenWrapperDownloader.java b/.mvn/wrapper/MavenWrapperDownloader.java[m
[1mnew file mode 100644[m
[1mindex 0000000..e76d1f3[m
[1m--- /dev/null[m
[1m+++ b/.mvn/wrapper/MavenWrapperDownloader.java[m
[36m@@ -0,0 +1,117 @@[m
[32m+[m[32m/*[m
[32m+[m[32m * Copyright 2007-present the original author or authors.[m
[32m+[m[32m *[m
[32m+[m[32m * Licensed under the Apache License, Version 2.0 (the "License");[m
[32m+[m[32m * you may not use this file except in compliance with the License.[m
[32m+[m[32m * You may obtain a copy of the License at[m
[32m+[m[32m *[m
[32m+[m[32m *      https://www.apache.org/licenses/LICENSE-2.0[m
[32m+[m[32m *[m
[32m+[m[32m * Unless required by applicable law or agreed to in writing, software[m
[32m+[m[32m * distributed under the License is distributed on an "AS IS" BASIS,[m
[32m+[m[32m * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.[m
[32m+[m[32m * See the License for the specific language governing permissions and[m
[32m+[m[32m * limitations under the License.[m
[32m+[m[32m */[m
[32m+[m[32mimport java.net.*;[m
[32m+[m[32mimport java.io.*;[m
[32m+[m[32mimport java.nio.channels.*;[m
[32m+[m[32mimport java.util.Properties;[m
[32m+[m
[32m+[m[32mpublic class MavenWrapperDownloader {[m
[32m+[m
[32m+[m[32m    private static final String WRAPPER_VERSION = "0.5.6";[m
[32m+[m[32m    /**[m
[32m+[m[32m     * Default URL to download the maven-wrapper.jar from, if no 'downloadUrl' is provided.[m
[32m+[m[32m     */[m
[32m+[m[32m    private static final String DEFAULT_DOWNLOAD_URL = "https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/"[m
[32m+[m[32m        + WRAPPER_VERSION + "/maven-wrapper-" + WRAPPER_VERSION + ".jar";[m
[32m+[m
[32m+[m[32m    /**[m
[32m+[m[32m     * Path to the maven-wrapper.properties file, which might contain a downloadUrl property to[m
[32m+[m[32m     * use instead of the default one.[m
[32m+[m[32m     */[m
[32m+[m[32m    private static final String MAVEN_WRAPPER_PROPERTIES_PATH =[m
[32m+[m[32m            ".mvn/wrapper/maven-wrapper.properties";[m
[32m+[m
[32m+[m[32m    /**[m
[32m+[m[32m     * Path where the maven-wrapper.jar will be saved to.[m
[32m+[m[32m     */[m
[32m+[m[32m    private static final String MAVEN_WRAPPER_JAR_PATH =[m
[32m+[m[32m            ".mvn/wrapper/maven-wrapper.jar";[m
[32m+[m
[32m+[m[32m    /**[m
[32m+[m[32m     * Name of the property which should be used to override the default download url for the wrapper.[m
[32m+[m[32m     */[m
[32m+[m[32m    private static final String PROPERTY_NAME_WRAPPER_URL = "wrapperUrl";[m
[32m+[m
[32m+[m[32m    public static void main(String args[]) {[m
[32m+[m[32m        System.out.println("- Downloader started");[m
[32m+[m[32m        File baseDirectory = new File(args[0]);[m
[32m+[m[32m        System.out.println("- Using base directory: " + baseDirectory.getAbsolutePath());[m
[32m+[m
[32m+[m[32m        // If the maven-wrapper.properties exists, read it and check if it contains a custom[m
[32m+[m[32m        // wrapperUrl parameter.[m
[32m+[m[32m        File mavenWrapperPropertyFile = new File(baseDirectory, MAVEN_WRAPPER_PROPERTIES_PATH);[m
[32m+[m[32m        String url = DEFAULT_DOWNLOAD_URL;[m
[32m+[m[32m        if(mavenWrapperPropertyFile.exists()) {[m
[32m+[m[32m            FileInputStream mavenWrapperPropertyFileInputStream = null;[m
[32m+[m[32m            try {[m
[32m+[m[32m                mavenWrapperPropertyFileInputStream = new FileInputStream(mavenWrapperPropertyFile);[m
[32m+[m[32m                Properties mavenWrapperProperties = new Properties();[m
[32m+[m[32m                mavenWrapperProperties.load(mavenWrapperPropertyFileInputStream);[m
[32m+[m[32m                url = mavenWrapperProperties.getProperty(PROPERTY_NAME_WRAPPER_URL, url);[m
[32m+[m[32m            } catch (IOException e) {[m
[32m+[m[32m                System.out.println("- ERROR loading '" + MAVEN_WRAPPER_PROPERTIES_PATH + "'");[m
[32m+[m[32m            } finally {[m
[32m+[m[32m                try {[m
[32m+[m[32m                    if(mavenWrapperPropertyFileInputStream != null) {[m
[32m+[m[32m                        mavenWrapperPropertyFileInputStream.close();[m
[32m+[m[32m                    }[m
[32m+[m[32m                } catch (IOException e) {[m
[32m+[m[32m                    // Ignore ...[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m        System.out.println("- Downloading from: " + url);[m
[32m+[m
[32m+[m[32m        File outputFile = new File(baseDirectory.getAbsolutePath(), MAVEN_WRAPPER_JAR_PATH);[m
[32m+[m[32m        if(!outputFile.getParentFile().exists()) {[m
[32m+[m[32m            if(!outputFile.getParentFile().mkdirs()) {[m
[32m+[m[32m                System.out.println([m
[32m+[m[32m                        "- ERROR creating output directory '" + outputFile.getParentFile().getAbsolutePath() + "'");[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m[32m        System.out.println("- Downloading to: " + outputFile.getAbsolutePath());[m
[32m+[m[32m        try {[m
[32m+[m[32m            downloadFileFromURL(url, outputFile);[m
[32m+[m[32m            System.out.println("Done");[m
[32m+[m[32m            System.exit(0);[m
[32m+[m[32m        } catch (Throwable e) {[m
[32m+[m[32m            System.out.println("- Error downloading");[m
[32m+[m[32m            e.printStackTrace();[m
[32m+[m[32m            System.exit(1);[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private static void downloadFileFromURL(String urlString, File destination) throws Exception {[m
[32m+[m[32m        if (System.getenv("MVNW_USERNAME") != null && System.getenv("MVNW_PASSWORD") != null) {[m
[32m+[m[32m            String username = System.getenv("MVNW_USERNAME");[m
[32m+[m[32m            char[] password = System.getenv("MVNW_PASSWORD").toCharArray();[m
[32m+[m[32m            Authenticator.setDefault(new Authenticator() {[m
[32m+[m[32m                @Override[m
[32m+[m[32m                protected PasswordAuthentication getPasswordAuthentication() {[m
[32m+[m[32m                    return new PasswordAuthentication(username, password);[m
[32m+[m[32m                }[m
[32m+[m[32m            });[m
[32m+[m[32m        }[m
[32m+[m[32m        URL website = new URL(urlString);[m
[32m+[m[32m        ReadableByteChannel rbc;[m
[32m+[m[32m        rbc = Channels.newChannel(website.openStream());[m
[32m+[m