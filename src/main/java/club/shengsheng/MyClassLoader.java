package club.shengsheng;


import java.io.File;
import java.io.FileInputStream;


/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes =null;
        if ("tech.insight.ShengSheng".equals(name)){
            File file = new File("./加密.class");
            try (FileInputStream inputStream = new FileInputStream(file);){

                bytes = inputStream.readAllBytes();
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte) (bytes[i]-1);
                }
                return defineClass(name,bytes,0,bytes.length);
            } catch (Exception e) {
                throw new ClassNotFoundException();
            }
        }
        return super.findClass(name);
    }
}
