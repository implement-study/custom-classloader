package club.shengsheng;

import java.io.File;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    private static final String MY_CLASS_NAME = "tech.insight.ShengSheng";

    private static final String MY_CLASS_PATH = "加密.class";

    // 修改加载类的逻辑 当前这个类由我们自己定义的类加载器定义
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                try {
                    if(name.startsWith(MY_CLASS_NAME)) {
                        c = findClass(name);
                    } else {
                        return super.loadClass(name, resolve);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(final String name)
            throws ClassNotFoundException {
        try {
            File classFile = new File(".", MY_CLASS_PATH);
            byte[] bytes = Files.readAllBytes(classFile.toPath());
            for(int i = 0; i < bytes.length; i++) {
                bytes[i]--;
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException(name);
        }
    }
}
