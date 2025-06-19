package club.shengsheng;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                if (name.startsWith("tech.insight")) {
                    c = findClass(name);
                } else {
                    c = this.getParent().loadClass(name);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("win");
        String relativePath;
        if (name.equals("tech.insight.ShengSheng")) {
            relativePath = "加密.class";
        } else {
            relativePath = name.replaceAll("\\.", isWindows ? "\\".concat(File.separator) : File.separator).concat(".class");
        }
        File classFile = new File(relativePath);
        byte[] bytes;
        try {
            bytes = decodeBytes(Files.readAllBytes(classFile.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] decodeBytes(byte[] bytes) {
        byte[] decodedBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            decodedBytes[i] = (byte) (bytes[i] - 1);
        }
        return decodedBytes;
    }
}
