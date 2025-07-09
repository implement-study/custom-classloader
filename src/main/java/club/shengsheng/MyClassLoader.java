package club.shengsheng;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (!"tech.insight.ShengSheng".equals(name)) {
            return super.findClass(name);
        }
        try {
            Path path = Path.of("加密.class");
            byte[] encryptedBytes = Files.readAllBytes(path);

            for (int i = 0; i < encryptedBytes.length; i++) {
                encryptedBytes[i] = (byte) (encryptedBytes[i] - 1);
            }


            return defineClass(name,encryptedBytes,0,encryptedBytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
