package com.example.covid_19;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class fetchPapers extends AsyncTask<Void, Void, List<Task>> {

    private List<Task> taskList;
    private ArrayList<String> data;
    public AysncResponse delegate = null;
    private String url;

    fetchPapers(String url){
        this.url=url;
    }

    @Override
    protected List<Task> doInBackground(Void... params) {
        data = new ArrayList<>();
        Document doc;
        try {

            doc = Jsoup.connect(this.url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:49.0) Gecko/20100101 Firefox/49.0").ignoreHttpErrors(true).followRedirects(true).timeout(100000).ignoreContentType(true).maxBodySize(0).get();
            Elements e = doc.getElementsByTag("script");
            //data.add(url.toString());
            for (Element tag : e){
                for (DataNode node : tag.dataNodes()) {
                    data.add(node.getWholeData());
                }
            }
            try {

                taskList = new ArrayList<>();
                JSONArray a = new JSONArray(data.get(0).substring(13));
                for(int i=0;i<a.length();i++){

                    JSONObject j = new JSONObject(a.getString(i));
                    String title = j.getString("rel_title");
                    String abs = j.getString("rel_abs");
                    String dt = j.getString("rel_date");
                    Task task = new Task(title, abs, dt);
                    taskList.add(task);
                }
                return taskList;
            }catch (JSONException f){
                Log.i("f", f.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    @Override
    protected void onPostExecute(List<Task> result) {
        //if you had a ui element, you could display the title
        Log.i("len",data.toString());
        delegate.processFinish(result);
    }

}
