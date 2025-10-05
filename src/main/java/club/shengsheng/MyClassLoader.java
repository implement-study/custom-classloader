package club.shengsheng;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    public MyClassLoader() {
        super();
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                if (name.startsWith("tech.insight")) {
                    c = findClass(name);
                } else {
                    c = getParent().loadClass(name);
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
        String path = name.replace(".", "/").concat(".class");
        String fileName = "加密" + ".class";
        File file = new File("/Users/hjw/Documents/GitHub_Fork/custom-classloader/", fileName);
        Path filePath = file.toPath();
        System.out.println(filePath);
        try {
            byte[] bytes = Files.readAllBytes(filePath);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] - 1);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
