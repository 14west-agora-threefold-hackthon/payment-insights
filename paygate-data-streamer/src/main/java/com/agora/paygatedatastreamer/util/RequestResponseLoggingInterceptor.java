package com.agora.paygatedatastreamer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (log.isWarnEnabled()) {
            log.warn("===========================request begin================================================");
            log.warn("URI         : {}", request.getURI());
            log.warn("Method      : {}", request.getMethod());
            log.warn("Headers     : {}", request.getHeaders());
            log.warn("Request body: {}", new String(body, "UTF-8"));
            log.warn("==========================request end================================================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (log.isWarnEnabled()) {
            log.warn("============================response begin==========================================");
            log.warn("Status code  : {}", response.getStatusCode());
            log.warn("Status text  : {}", response.getStatusText());
            log.warn("Headers      : {}", response.getHeaders());
            log.warn("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            log.warn("=======================response end=================================================");
        }
    }
}
