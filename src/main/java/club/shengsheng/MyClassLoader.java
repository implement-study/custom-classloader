package club.shengsheng;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    private static final String classFile = "加密.class";

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name != null && name.startsWith("java.")) {
            return super.loadClass(name);
        }
        return findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = null;
        try {
            classData = readClassFile();
        } catch (IOException e) {
            throw new ClassNotFoundException("Could not read class file: " + classFile);
        }
        return defineClass(name, classData, 0, classData.length);
    }

    private byte[] readClassFile() throws IOException {
        try (FileInputStream fis = new FileInputStream(classFile);
             ByteArrayOutputStream byteArrStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                byteArrStream.write(buffer, 0, bytesRead);
            }

            byte[] byteArray = byteArrStream.toByteArray();
            for (int i = 0; i < byteArray.length; i++) {
                byteArray[i] = (byte)(byteArray[i] - 1);
            }
            return byteArray;
        }
    }
}
