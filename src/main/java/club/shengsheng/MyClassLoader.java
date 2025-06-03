package club.shengsheng;


import com.sun.source.tree.SynchronizedTree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized(getClassLoadingLock(name)){
            Class<?> c = findLoadedClass(name);
            if (c == null){
                if (name.startsWith("tech")){
                    c = findClass(name);
                }else{
                    c = getParent().loadClass(name);
                }
            }
            if(resolve){
                resolveClass(c);
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String replace = "加密.class";
        File classFile = new File(replace);
        try {
            byte[] bytes = Files.readAllBytes(classFile.toPath());
            for(int i = 0; i < bytes.length; i ++){
                bytes[i] = (byte)(bytes[i] - 1);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException(name);
        }
    }
}
