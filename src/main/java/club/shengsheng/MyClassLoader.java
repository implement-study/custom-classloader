package club.shengsheng;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {
    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    public MyClassLoader() {
        super();
    }

    // 加载 链接 初始化
    // 链接 校验、准备、解析

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                // 打破双亲委派，只加载 tech.insight 包下的类
                if (name.startsWith("tech.insight")) {
                    try {
                        c = findClass(name);
                    } catch (ClassNotFoundException e) {
                        // 可以选择不处理或 fallback 给父加载器
                        throw e;
                    }
                } else {
                    // JDK类或第三方库依然委托父加载器
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
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.startsWith("tech.insight")) {
            // 从项目根目录读取加密的class文件
            File encryptedFile = new File("加密.class");
            byte[] bytes = new byte[0];
            try {
                bytes = Files.readAllBytes(encryptedFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 解密：每个字节-1
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] - 1);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } else {
            return super.findClass(name);
        }
    }
}
