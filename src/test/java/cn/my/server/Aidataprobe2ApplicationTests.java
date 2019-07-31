/*
package cn.my.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;
@RunWith(SpringRunner.class)
@SpringBootTest
public class Aidataprobe2ApplicationTests {






*/
/**
 * Created with IDEA
 * author:QinWei
 * Date:2018/12/27
 * Time:11:08
 * 并发测试
 *//*

    public static void main(String[] args) {
        int count=1000;
        final CountDownLatch cdl=new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cdl.countDown();
                    try {
                        cdl.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        connect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    public static void connect() throws Exception {
        final String urlStr="https://blog.52itstyle.com/";
        URL url=new URL(urlStr);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.connect();
        System.out.println(urlConnection.getInputStream());
    }
}
*/
