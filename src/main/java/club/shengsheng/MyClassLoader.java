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
            if (c == null) {
                if (name.startsWith("tech.insight")) {
                    c = findClass(name);
                } else {
                    // 交给系统加载器
                    c = ClassLoader.getSystemClassLoader().loadClass(name);
                }
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("加密.class");
        try {
            byte b = 1;
            byte[] bytes = Files.readAllBytes(file.toPath());
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] - b);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }
}
