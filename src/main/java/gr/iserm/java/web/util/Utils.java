package gr.iserm.java.web.util;

public interface Utils {

    static  void sleep(int i) {
        try {
            Thread.sleep(i*1000);
        } catch (InterruptedException e) {}
    }

}
