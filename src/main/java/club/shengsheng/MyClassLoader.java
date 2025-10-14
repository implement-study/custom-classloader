package club.shengsheng;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            //如果是 java.* 就使用父类加载器加载
            if (c == null){
                if (name.startsWith("tech")) {
                    c = findClass(name);
                }else {
                    c = this.getParent().loadClass(name);
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
        File classFile = new File("/Users/Lin/Documents/workSpace/ideaProjects/custom-classloader/加密.class");
        try {
            byte[] bytes = Files.readAllBytes(classFile.toPath());
            for (int i = 0; i < bytes.length; i++) {
                byte aByte = bytes[i];
                //每一个字节都减 1
                aByte = (byte) (aByte - 1);
                //生产新的 bytes 数组
                bytes[i] = aByte;
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException();
        }
    }
}
