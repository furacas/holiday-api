package com.furacas.api.holiday.utils;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

    @SneakyThrows
    public static <T> T get(String url,Class<T> clz) {
        @Cleanup CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        @Cleanup CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            String resp = EntityUtils.toString(responseEntity);
            return JsonUtils.fromJson(resp,clz);
        }
        return null;
    }
}
