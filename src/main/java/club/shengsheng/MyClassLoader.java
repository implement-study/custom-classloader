package club.shengsheng;


import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
public class MyClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                if (name.startsWith("tech")){ //加载我们自己的包名
                    c = findClass(name);
                }else{ //如果是其他包名,用父类加载器去加载
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
        String absolutePath = Paths.get("").toAbsolutePath().toString();
        String filePath = absolutePath + "\\加密.class";
        System.out.println("filePath = " + filePath);
        File file = new File(filePath);
        ByteBuffer byteBuffer = handlerClass(file);

        return defineClass(name, byteBuffer.array(), 0, byteBuffer.limit());
    }

    private static ByteBuffer handlerClass(File file) {
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        System.out.println("file.length(): " + file.length());
        try(FileChannel channel = new RandomAccessFile(file,"r").getChannel()){
            //准备缓冲区
            ByteBuffer tempBuff = ByteBuffer.allocate(20); //10字节
            while(true){
                //从 channel 中读取数据, 向 buffer 中写入
                int len = channel.read(tempBuff);
                if (len == -1){
                    break; //没有数据了
                }
                //切换到读模式
                tempBuff.flip();
                //打印 buffer 中的内容
                while(tempBuff.hasRemaining()){
                    byte b = tempBuff.get(); //一个字节一个字节取
                    b -= (byte)1;
                    System.out.print(String.format("0x%02X ", b));
                    buffer.put(b);
                }
                System.out.println();
                tempBuff.clear(); //切换为写模式
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
