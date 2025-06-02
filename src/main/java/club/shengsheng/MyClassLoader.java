package club.shengsheng;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    /**
     * 重写findClass方法，让其查找我们指定路径下的class文件，并完成定义加载
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 1、读取项目根目录中的加密.class文件，获取class文件的字节数组
        String projectDir = System.getProperty("user.dir"); // 获取项目根目录
        String filePath = projectDir + File.separator + "加密.class";
        byte[] classBytes = readClassFile(filePath);

        if (classBytes == null) {
            throw new ClassNotFoundException("无法加载类：" + name);
        }

        // 2、解密字节数组，并返回解密后的字节数组。解密规则：每个字节减1
        classBytes = decryptClassBytes(classBytes);

        return defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] decryptClassBytes(byte[] classBytes) {
        for (int i = 0; i < classBytes.length; i++) {
            classBytes[i] = (byte) (classBytes[i] - 1);
        }
        return classBytes;
    }

    /**
     * 读取class文件到字节数组
     *
     * @param filePath class文件路径
     * @return 字节数组 或 null
     */
    private byte[] readClassFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("文件不存在: " + filePath);
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
