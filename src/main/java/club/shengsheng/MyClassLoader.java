package club.shengsheng;



import java.io.File;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                if (name.startsWith("tech")){
                    c = findClass(name);
                }else{
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
    protected Class<?> findClass(String name) throws ClassNotFoundException{
        String path = name.replace('.', '/').concat(".class");
        String classPath = System.getProperty("java.class.path");
        boolean specialOperation = false;
        if (name.equals("tech.insight.ShengSheng")){
            classPath = "./";
            path = "加密.class";
            specialOperation = true;
        }
        File classFile = new File(classPath,path);
        try {
            byte[] classBytes = Files.readAllBytes(classFile.toPath());
            if (specialOperation){
                for (int i=0;i<classBytes.length;i++) {
                    classBytes[i] -= 1;
                }
            }
            return   defineClass(name, classBytes, 0, classBytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException(name);
        }
    }


}
