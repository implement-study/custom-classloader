package club.shengsheng;


import java.io.*;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.contains("tech.insight.ShengSheng")) {
            this.findClass(name);
        }
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("加密.class");
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int classByte;
            while ((classByte = inputStream.read()) != -1) {
                byteArrayOutputStream.write((classByte-1));
            }
            return defineClass(name, byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
