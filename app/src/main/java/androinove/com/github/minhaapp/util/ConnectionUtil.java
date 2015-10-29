package androinove.com.github.minhaapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by PedroFelipe on 28/10/2015.
 */
public class ConnectionUtil {

    public static boolean hasConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static HttpURLConnection connect(String url, String httpMethod, boolean doOutput, byte[] postData) {
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dataOutputStream = null;
        URL urlConnection;

        try {
            urlConnection = new URL(url);

            httpURLConnection = (HttpURLConnection) urlConnection.openConnection();
            httpURLConnection.setReadTimeout(15 * Constants.SECONDS);
            httpURLConnection.setConnectTimeout(15 * Constants.SECONDS);
            httpURLConnection.setRequestMethod(httpMethod);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(doOutput);

            if (doOutput) {
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setRequestProperty("charset", "utf-8");
                httpURLConnection.setRequestProperty("Content-Length", Integer.toString(postData.length));
                httpURLConnection.setUseCaches(false);

                dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.write(postData);
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            httpURLConnection.connect();
        } catch (Exception e) {
            Log.e(Constants.LOG_KEY, "ConnectionUtil.connect() -> " + e.getMessage());
        }
        return httpURLConnection;
    }

    public static void closeConnection(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

}
