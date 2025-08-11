package club.shengsheng;



import java.io.File;
import java.io.FileInputStream;


/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                if (name.startsWith("tech.insight")){
                    c = this.findClass(name);
                }else {
                    c = getParent().loadClass(name);
                }
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        File file = new File("加密.class");
        try (FileInputStream fileInputStream = new FileInputStream(file);){
            byte[] bytes = fileInputStream.readAllBytes();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] -= 1;
            }
            return defineClass(name,bytes,0,bytes.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
