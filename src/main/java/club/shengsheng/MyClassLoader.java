package club.shengsheng;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        if (c == null) {
            if (name.startsWith("tech.insight.")) {
                c = findClass(name);
            }else {
                c = loadClass(name,false);
            }
        }
        return c;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // tech.insight.ShengSheng
        String path = "加密.class";
        File file = new File(path);
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            decoderBytes(bytes);
            Files.write(new File("shengsheng.class").toPath(), bytes);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }

    private void decoderBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            bytes[i] = (byte) (b-1);
        }
    }
}
