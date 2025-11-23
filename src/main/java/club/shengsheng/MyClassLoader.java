package club.shengsheng;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(System.getProperty("user.dir"), "加密.class");
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] -= 1;
        }

        return defineClass(name, bytes, 0, bytes.length);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // 判断类是否已经加载
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                if ("tech.insight.ShengSheng".equals(name)) {
                    // 只有在符合条件的情况下走我们的逻辑
                    c = findClass(name);
                } else {
                    // Object类需要通过BootStrap加载
                    c = getParent().loadClass(name);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
}
