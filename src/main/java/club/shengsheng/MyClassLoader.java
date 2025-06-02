package club.shengsheng;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c != null) {
                return c;
            }
            if (!"tech.insight.ShengSheng".equals(name)) {
                return super.loadClass(name);
            }
            return findClass(name);
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File classFile = new File(".", "加密.class");
        try {
            byte[] b = Files.readAllBytes(classFile.toPath());
            for (int i = 0; i < b.length; i++) {
                b[i]--;
            }
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("加密.class");
        }
    }
}
