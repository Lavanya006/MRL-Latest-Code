package com.mt_ag.bayer.cmc.core.mapping;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.mt_ag.bayer.cmc.constants.SystemConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebClientFactory {
    @Value("${bayer.proxy.status}")
    private String PROXY_STATUS;
    @Value("${bayer.proxy.host}")
    private String PROXY_HOST;
    @Value("${bayer.proxy.port}")
    private Integer PROXY_PORT;
    @Value("${bayer.proxy.user}")
    private String PROXY_USER;
    @Value("${bayer.proxy.password}")
    private String PROXY_PASSWORD;

    public WebClient create() {
        WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getCookieManager().setCookiesEnabled(true);

        if (PROXY_STATUS != null) {
            if (this.PROXY_STATUS.equals(SystemConstants.IS_ON)) {
                ProxyConfig proxyConfig = new ProxyConfig();
                proxyConfig.setSocksProxy(false); //Set to false if it is a http server
                proxyConfig.setProxyHost(PROXY_HOST); //your proxy IP
                proxyConfig.setProxyPort(PROXY_PORT);

                final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
                credentialsProvider.addCredentials(PROXY_USER, PROXY_PASSWORD);

                webClient.setCredentialsProvider(credentialsProvider);
                webClient.getOptions().setProxyConfig(proxyConfig);
            }
        }
        return webClient;
    }
}
