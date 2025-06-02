package club.shengsheng;


import java.io.File;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException
    {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                if (name.startsWith("tech")) {
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
        String rootFile = System.getProperty("user.dir").concat(".加密".replace('.', File.separatorChar)).concat(".class");
        File classFile = new File(rootFile);
        byte[] classBytes;
        try {
            classBytes = Files.readAllBytes( classFile.toPath());
            for (int i = 0; i < classBytes.length; i++) {
                classBytes[i] -= 1;
            }

        } catch (Exception e) {
            throw new ClassNotFoundException();
        }
        return  defineClass(name, classBytes, 0, classBytes.length);

    }
}
