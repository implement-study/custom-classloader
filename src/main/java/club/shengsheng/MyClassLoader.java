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
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if(c == null) {
                if(name.equals("tech.insight.ShengSheng")) {
                    c = findClass(name);
                }else {
                    c = getParent().loadClass(name);
                }
            }
            return c;
        }
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = "加密.class";
        File file = new File(".", path);
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            for(int i = 0; i < bytes.length; i++) {
                bytes[i] = --bytes[i];
            }
            return defineClass(name, bytes, 0, bytes.length);

        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }
}
