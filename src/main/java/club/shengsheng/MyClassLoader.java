package club.shengsheng;


import java.io.*;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // 找到已经加载的类
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
        if (name.equals("tech.insight.ShengSheng")) {
            File file = new File("./加密.class");
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) (bytes[i] - 1);
                }
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return super.findClass(name);
    }
}
