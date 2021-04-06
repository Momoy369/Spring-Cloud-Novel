package com.java2nb.novel.core.utils;

import lombok.SneakyThrows;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.List;

public class RestTemplateUtil {



    @SneakyThrows
    public static RestTemplate getInstance(String charset) {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        //Abaikan sertifikat
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", csf)
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);

        //Jumlah maksimum koneksi di kumpulan koneksi, 0 berarti tidak terbatas; jika Anda mengambil 0, Anda perlu mempertimbangkan konsekuensi dari crash sistem yang disebabkan oleh kebocoran koneksi
        connectionManager.setMaxTotal(1000);
        //Jumlah maksimum koneksi per rute Jika hanya satu alamat yang dipanggil, itu dapat diatur sebagai jumlah koneksi maksimum
        connectionManager.setDefaultMaxPerRoute(300);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();


        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectionRequestTimeout(3000);
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(30000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if(httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName(charset));
                break;
            }
        }
        return restTemplate;
    }

}
