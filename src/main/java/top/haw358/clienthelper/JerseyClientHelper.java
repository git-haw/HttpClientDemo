package top.haw358.clienthelper;

import java.net.HttpURLConnection;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 使用Jersey Client的 REST客户端.
 * @author 黄爱伟
 * 联系方式：haw358linux@163.com
 * 		http://www.haw358.top
 * 时间：2016-12-8
 */

public final class JerseyClientHelper {

    private static WebResource client;

    public static void setBaseUrl(String baseUrl) {
        Client jerseyClient = Client.create(new DefaultClientConfig());
        JerseyClientHelper.client = jerseyClient.resource(baseUrl);
    }

    /**
     * @param subPath
     * @param param
     * @param requestType
     * @return
     */
    private static String request(String subPath, MultivaluedMap<String, String> param, String requestType) {
        ClientResponse response = null;
        String msg = "";
        int status = 0;
        Object result = null;
        try {
            if ("GET".equals(requestType)) {
                response = client.path(subPath).queryParams(param).accept(MediaType.APPLICATION_JSON)
                        .get(ClientResponse.class);
            } else if ("POST".equals(requestType)) {
                response = client.path(subPath).queryParams(param).accept(MediaType.APPLICATION_JSON)
                        .post(ClientResponse.class);
            } else {
                throw new Exception("请求方式只有GET/POST");
            }
            status = response.getStatus();
            result = response.getEntity(Object.class);
            System.out.println("response:" + response);
            System.out.println("response:" + response.getType());
        } catch (UniformInterfaceException e) {
            e.printStackTrace();
            msg = "url地址有误或者对应服务异常";
        } catch (ClientHandlerException e) {
            e.printStackTrace();
            msg = "处理该地址的请求或响应失败\n" + "原因：1主机地址错误，拒绝连接主机，请检查IP地址、端口是否正确，否则服务异常\n" + "解决办法：ping一下该IP地址->监听指定端口的程序是否启动";
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (status == HttpURLConnection.HTTP_OK) {
            msg = "服务正常";
        } else if ("".equals(msg)) {
            msg = "服务异常";
        }

        String responseStr = (response == null) ? "" : response.toString();
        String data = (result == null) ? "" : result.toString();

        return "响应：" + responseStr + "\n" + "消息：" + msg + "\n" + "数据：" + data;
    }

    /**
     * @param subPath
     * @param param
     * @return
     */
    public static String get(String subPath, MultivaluedMap<String, String> param) {
        return JerseyClientHelper.request(subPath, param, "GET");
    }

    /**
     * @param subPath
     * @param param
     * @return
     */
    public static String post(String subPath, MultivaluedMap<String, String> param) {
        return JerseyClientHelper.request(subPath, param, "POST");
    }
}
