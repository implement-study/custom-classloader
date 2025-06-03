package club.shengsheng;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {
    //
    public static void main(String[] args) {
        System.out.println("hello");
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        synchronized (getClassLoadingLock(name)) {
            Class<?> loadClass = findLoadedClass(name);
            if (loadClass != null) {
                return loadClass;
            }
            if (name.startsWith("tech")) {
                loadClass = findClass(name);
            } else {
                loadClass = getParent().loadClass(name);
            }
            if (resolve) {
                resolveClass(loadClass);
            }
            return loadClass;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            File file = new File(System.getProperty("user.dir") + File.separatorChar + "加密.class");
            byte[] bytes = Files.readAllBytes(file.toPath());
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] - 1);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }

}
