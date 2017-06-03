package enfo.android.mc.fhooe.at.enfo.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class JSONTask extends AsyncTask<String, String, String> {

    public interface AsyncResponse {
        void processFinish(String output);
    }

    /**Key will be used to authenticate your application on the Toornament API*/
    private final String API_KEY = "JK5nCbHtb9yEGHDYNCdYgCvHGXRD7r-3HwVOJDjSMME";
    /**adds the API KEY to the HTTP Header to authenticate the App*/
    private final String API_KEY_HTTP_HEADER = "X-Api-Key";
    private HttpURLConnection connection = null;
    private BufferedReader reader = null;
    private ProgressDialog dialog;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AsyncResponse mListener = null;


    public JSONTask(Context _context, SwipeRefreshLayout _srl, AsyncResponse listener){
            dialog = new ProgressDialog(_context);
            mSwipeRefreshLayout = _srl;
            mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mSwipeRefreshLayout != null){
            mSwipeRefreshLayout.setRefreshing(true);
        }else{
            dialog.setMessage("Please Wait");
            dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty(API_KEY_HTTP_HEADER ,API_KEY);
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer result = new StringBuffer();
            String line = "";

            while((line = reader.readLine())!=null){
                result.append(line+"\n");
            }
            return result.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (mSwipeRefreshLayout != null) {
            if(mSwipeRefreshLayout.isRefreshing()){
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
        mListener.processFinish(result);
        //parseDisciplineJSON();
    }
}
