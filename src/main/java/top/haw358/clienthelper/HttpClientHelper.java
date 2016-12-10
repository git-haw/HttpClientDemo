package top.haw358.clienthelper;

import java.net.ConnectException;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 使用HttpClient的客户端.
 * @author 黄爱伟
 * 联系方式：haw358linux@163.com
 * 		http://www.haw358.top
 * 时间：2016-12-8
 */

public class HttpClientHelper {

    @SuppressWarnings("deprecation")
    public static String httpGet(String uri) {
        HttpResponse response = null;
        int httpCode = 0;
        String msg = "";
        String dataStr = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            response = httpClient.execute(new HttpGet(uri));
            httpCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();
            dataStr = EntityUtils.toString(entity);
            System.out.println("entity:" + dataStr);
        } catch (ConnectException e) {
            e.printStackTrace();
            msg = "主机地址错误，拒绝连接主机，请检查IP地址、端口是否正确，否则服务异常\n" + "解决办法：ping一下该IP地址->监听指定端口的程序是否启动";
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (httpCode == HttpURLConnection.HTTP_OK && response != null) {
            msg = "服务正常";
        } else if ("".equals(msg)) {
            msg = "服务异常";
        }

        String responseStr = (response == null) ? "" : response.toString();
        return "响应：" + responseStr + "\n" + "消息：" + msg + "\n" + "数据：" + dataStr;
    }

    public static String httpPost(String uri, String param) {
        HttpResponse response = null;
        int httpCode = 0;
        String msg = "";
        String dataStr = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            StringEntity stringEntity = new StringEntity(param);
            stringEntity.setContentType("application/x-www-form-urlencoded");
            //stringEntity.setContentEncoding("utf-8");
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            httpCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();
            dataStr = EntityUtils.toString(entity);
            System.out.println("entity:" + dataStr);
        } catch (ConnectException e) {
            e.printStackTrace();
            msg = "主机地址错误，拒绝连接主机，请检查IP地址、端口是否正确，否则服务异常\n" + "解决办法：ping一下该IP地址->监听指定端口的程序是否启动";
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (httpCode == HttpURLConnection.HTTP_OK && response != null) {
            msg = "服务正常";
        } else if ("".equals(msg)) {
            msg = "服务异常";
        }

        String responseStr = (response == null) ? "" : response.toString();
        return "响应：" + responseStr + "\n" + "消息：" + msg + "\n" + "数据：" + dataStr;
    }

}