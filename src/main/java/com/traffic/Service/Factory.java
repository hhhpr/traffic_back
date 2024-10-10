package com.traffic.Service;

import com.traffic.Mapper.FetchData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Factory {

    @Autowired
    private FetchData fetchdata;

    public void fetchfacdata() {
        try {
            List<Map<String, String>> pois = fetchPOIs();
            storePOIs(pois);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取poi点，加入数据库
    private List<Map<String, String>> fetchPOIs() throws Exception {
        String url = "https://restapi.amap.com/v3/place/text?key=bee11f0b69f2ab082be9307b1538a1d4&keywords=%E5%AE%B6%E5%85%B7&city=%E6%88%90%E9%83%BD&page_num=1&page_size=50";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpClient.execute(request);
        String json = EntityUtils.toString(response.getEntity());

        JsonElement je = JsonParser.parseString(json);
        JsonArray pois = je.getAsJsonObject().getAsJsonArray("pois");

        Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
        List<Map<String, Object>> originalPois = new Gson().fromJson(pois, listType);

        // Convert each Map<String, Object> to Map<String, String>
        List<Map<String, String>> stringPois = new ArrayList<>();
        for (Map<String, Object> originalPoi : originalPois) {
            Map<String, String> stringPoi = new HashMap<>();
            for (Map.Entry<String, Object> entry : originalPoi.entrySet()) {
                if (entry.getKey().equals("location")) {
                    String[] locationArray = String.valueOf(entry.getValue()).split(",");
                    stringPoi.put("longitude", locationArray[0]);
                    stringPoi.put("latitude", locationArray[1]);
                } else {
                    stringPoi.put(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
            stringPois.add(stringPoi);
        }

        return stringPois;
    }

    private void storePOIs(List<Map<String, String>> pois) throws Exception {
        for (Map<String, String> poi : pois) {
            fetchdata.storePOIs(poi);
        }
    }


}
