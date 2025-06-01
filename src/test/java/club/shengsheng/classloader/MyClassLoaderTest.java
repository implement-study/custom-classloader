package club.shengsheng.classloader;

import club.shengsheng.MyClassLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author gongxuanzhangmelt@gmail.com
 **/
class MyClassLoaderTest {

    @Test
    void testLoadShengSheng() throws Exception {
        Class<?> shengshengClass = new MyClassLoader().loadClass("tech.insight.ShengSheng");
        Constructor<?> constructor = shengshengClass.getConstructor();
        Object shengsheng = constructor.newInstance();
        Method declaredMethod = shengshengClass.getDeclaredMethod("say");
        Object invoke = declaredMethod.invoke(shengsheng);
        Assertions.assertEquals(31, invoke);
    }


}
