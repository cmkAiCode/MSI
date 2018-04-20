package cn.fanrunqi.materiallogin.httputil;

/**
 * Created by cmk on 2018/3/24.
 */



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 该工具类用于连接服务器
 */
public class HttpUtil {

    public static void sendOkHttpRequest(String address, RequestBody requestBody, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 将ip 和 端口号 以及Servlet位置转为url地址
     * @param ip IP地址
     * @param port 端口号
     * @param path Servlet地址
     * @return url地址
     */
    public static String toUrl(String ip,String port,String path){
        return "http://" + ip + ":" + port + path;
    }

    /**
     * 判断是否联网
     * @param context 上下文
     * @return true/false
     */
    public static boolean isNetworkConnected(Context context){
        if(context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
            //mNetworkInfo.isAvailable();
                return true;//有网
            }
        }
        return false;//没有网
    }
}

