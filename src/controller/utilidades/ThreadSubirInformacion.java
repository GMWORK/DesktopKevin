/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utilidades;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.net.Proxy.Type.HTTP;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;

/**
 *
 * @author Mateo
 */
public class ThreadSubirInformacion implements Runnable {

    private String url = "http://192.168.1.101:8080/WebGMWORK/webresources/";
    private TreeMap<String, List<String[]>> map;

    public ThreadSubirInformacion(TreeMap<String, List<String[]>> map) {
        this.map = map;

    }

    public void doOperation() {
        String[] list = null;
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            List<String[]> lista = (List<String[]>) map.get(key);
            for (int i = 0; i < lista.size(); i++) {
                list = lista.get(i);
                switch (list[0]) {
                    case "POST":
                        doOperationPost(list[1], list[2]);
                        break;
                    case "PUT":
                        doOperationPut(list[1], list[2]);
                        break;
                    case "DELETE":
                        doOperationDelete(list[1], list[2]);
                        break;
                }
            }
        }

    }

    private void doOperationDelete(String s, String s1) {

        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpResponse response;
        StringBuilder stringURL = new StringBuilder();
        stringURL.append(url).append(s).append(s1);
        String stringResponse = null;
        try {
            HttpDelete delete = new HttpDelete(new URI(stringURL.toString()));
            //StringEntity se = new StringEntity(postMessage);
            //se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            //put.setEntity(se);
            response = client.execute(delete);
            if (response != null) {
                InputStream in = response.getEntity().getContent();
                stringResponse = getStringFromInputStream(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doOperationPut(String s, String s1) {

        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpResponse response;
        String stringResponse = null;
        StringBuilder stringURL = new StringBuilder();
        stringURL.append(url).append(s);
        try {
            HttpPut put = new HttpPut(new URI(stringURL.toString()));
            StringEntity se = new StringEntity(s1);
            se.setContentType("application/json");
            put.setEntity(se);
            response = client.execute(put);
            if (response != null) {
                InputStream in = response.getEntity().getContent();
                stringResponse = getStringFromInputStream(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doOperationPost(String ruta, String json) {
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        HttpResponse response;
        String stringResponse = null;
        StringBuilder stringURL = new StringBuilder();
        stringURL.append(url).append(ruta);

        try {
            HttpPost post = new HttpPost(new URI(stringURL.toString()));
            StringEntity se = new StringEntity(json);
            se.setContentType("application/json");
            post.setEntity(se);
            response = client.execute(post);
            if (response != null) {
                InputStream in = response.getEntity().getContent();
                stringResponse = getStringFromInputStream(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            InputStreamReader isr = new InputStreamReader(bis);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            //LogAndToastMaker.makeErrorLog(e.getMessage());
        }
        return sb.toString();
    }

    @Override
    public void run() {
        try {
            doOperation();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

    }
}
