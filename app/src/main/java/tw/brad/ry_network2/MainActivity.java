package tw.brad.ry_network2;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test1(View view) {
        new Thread(){
            @Override
            public void run() {
                postConnect();
            }
        }.start();
    }

    private void postConnect(){
        try{
            URL url = new URL("https://www.bradchao.com/v2/apptest/postTest1.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            ContentValues params = new ContentValues();
            params.put("account", "brad");
            params.put("passwd", "1234");

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(conn.getOutputStream()));
            writer.write(queryString(params));
            writer.flush();
            writer.close();

            conn.connect();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String data = reader.readLine();
            reader.close();

            Log.v("brad", data);

        }catch (Exception e){
            Log.v("brad", e.toString());
        }

    }

    // acccount=brad&passwd=1234
    // return => url encode
    private String queryString(ContentValues params){
        Set<String> keys = params.keySet();
        StringBuffer sb = new StringBuffer();
        try {
            for (String key : keys) {
                sb.append(URLEncoder.encode(key, "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(params.getAsString(key), "UTF-8"));
                sb.append("&");
            }
            sb.deleteCharAt(sb.length()-1);
            return sb.toString();
        }catch (Exception e){
            return null;
        }
    }




}
