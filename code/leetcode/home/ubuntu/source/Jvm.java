package source;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Jvm {

    /**
     * 用来查看源码
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("输出");
    }

    public void study() {
        Map hashMap = new HashMap();
        Map currentHashMap = new ConcurrentHashMap<>();
    }

}
