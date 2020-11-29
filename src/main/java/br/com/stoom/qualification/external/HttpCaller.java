package br.com.stoom.qualification.external;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class HttpCaller {

    OkHttpClient okHttpClient = new OkHttpClient();

    public String run(@NonNull String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        log.info("About to request URL: {}", url);
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.body() != null) {
                String s = response.body().string();
                log.info("Response body: {}", s);
                return s;
            }
            log.warn("Request returned a null response.");
        } catch (IOException e) {
            log.error("Request failed: {}", e.getMessage());
        }
        return null;
    }
}
