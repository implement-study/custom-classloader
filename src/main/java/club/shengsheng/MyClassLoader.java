package club.shengsheng;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {


    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                if (name.startsWith("tech.insight.ShengSheng")){
                    c = this.findClass(name);
                }
                else {
                    c = getParent().loadClass(name);
                }
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {


        File classFile = new File("/Users/anyifei/projects/custom-classloader/加密.class");

        try {
            byte[] bytes = Files.readAllBytes(classFile.toPath());
            byte[] newBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                byte aByte = bytes[i];
                aByte = (byte) (aByte - 1);
                newBytes[i] = aByte;
            }
            return defineClass(name, newBytes, 0, bytes.length);
        }catch (Exception e) {
            throw new ClassNotFoundException(name);
        }
    }
}
