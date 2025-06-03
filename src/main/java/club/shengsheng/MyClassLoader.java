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
            if (name.startsWith("tech")){
                c = findClass(name);
            }else{
                c = getParent().loadClass(name);
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File classFile = new File(".","加密.class");
        try {
            byte[] bytes = Files.readAllBytes(classFile.toPath());
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] == Byte.MIN_VALUE){
                    bytes[i] = Byte.MAX_VALUE;
                }else {
                    bytes[i]--;
                }
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }
}
