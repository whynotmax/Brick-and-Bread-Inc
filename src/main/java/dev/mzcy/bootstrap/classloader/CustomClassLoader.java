package dev.mzcy.bootstrap.classloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CustomClassLoader extends ClassLoader {

    static Logger logger = LoggerFactory.getLogger(CustomClassLoader.class);

    private final Map<String, Class<?>> loadedClasses = new HashMap<>();
    private final JarFile jarFile;

    public CustomClassLoader(Path jarPath) throws IOException {
        super(CustomClassLoader.class.getClassLoader());
        this.jarFile = new JarFile(jarPath.toFile());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (loadedClasses.containsKey(name)) {
            return loadedClasses.get(name);
        }

        String entryName = name.replace('.', '/') + ".class";
        JarEntry entry = jarFile.getJarEntry(entryName);

        if (entry == null) {
            throw new ClassNotFoundException(name);
        }

        try (InputStream is = jarFile.getInputStream(entry)) {
            byte[] classBytes = is.readAllBytes();
            Class<?> clazz = defineClass(name, classBytes, 0, classBytes.length);
            loadedClasses.put(name, clazz);
            logger.info("Loaded class: {}", name);
            return clazz;
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class " + name, e);
        }
    }

    public static Path downloadIfNeeded(String url, Path targetDir) throws IOException, URISyntaxException {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        Path targetPath = targetDir.resolve(fileName);

        if (Files.exists(targetPath)) {
            logger.info("File {} already exists, skipping download.", targetPath);
            return targetPath;
        }

        logger.info("Downloading {} to {}", url, targetPath);

        Files.createDirectories(targetDir);

        URLConnection conn = new URI(url).toURL().openConnection();
        try (InputStream in = conn.getInputStream();
             OutputStream out = Files.newOutputStream(targetPath)) {
            in.transferTo(out);
        }

        logger.info("Downloaded {} to {}", url, targetPath);

        return targetPath;
    }
}
