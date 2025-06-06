package club.shengsheng;


import java.io.File;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // 获取项目根目录路径
            String projectRoot = System.getProperty("user.dir");
            File encryptedFile = new File(projectRoot + File.separator + "加密.class");
            byte[] bytes = Files.readAllBytes(encryptedFile.toPath());

            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] - 1);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException(name, e);
        }
    }


    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // copy原ClassLoader中的实现，将双亲委派逻辑去掉，并判断tech包下的类由自定义的类加载器加载
        synchronized (getClassLoadingLock(name)) {
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
}
