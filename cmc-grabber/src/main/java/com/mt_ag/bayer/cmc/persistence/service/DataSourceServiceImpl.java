package com.mt_ag.bayer.cmc.persistence.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mt_ag.bayer.cmc.core.mapping.WebClientFactory;
import com.mt_ag.bayer.cmc.persistence.repository.DataSourcesRepository;
import com.mt_ag.bayer.cmc.constants.SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.mt_ag.bayer.cmc.persistence.entity.DataSource;

@Service
public class DataSourceServiceImpl implements DataSourceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceServiceImpl.class);

	private DataSourcesRepository repository;

	@Autowired
	WebClientFactory webClientFactory;

	@Autowired
	public DataSourceServiceImpl(DataSourcesRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void save(DataSource dataSource) {
		this.repository.save(dataSource);

	}

	@Override
	public List<DataSource> findAll() {
		List<DataSource> dataSources = new ArrayList<>();
		this.repository.findAll().forEach(dataSources::add);
		return dataSources;
	}

	@Override
	public DataSource find(Long id) {
		return this.repository.findById(id).get();
	}

	@Override
	public void delete(DataSource dataSource) {
		this.repository.delete(dataSource);
	}

	@Override
	public DataSource findByName(String name) {
		return this.repository.findByName_IgnoreCaseContaining(name);
	}

	@Override
	public boolean isUp(DataSource dataSource, boolean disableThrowExceptions) {
		WebClient webClient = webClientFactory.create();
		if (disableThrowExceptions) {
			webClient = disableExceptionThrowingInWebClient(webClient);
		}
		try {
			webClient.getPage(dataSource.getUrl());
			closeWebClient(webClient);
		} catch (UnknownHostException e) {
			LOGGER.error("ERROR. The " + dataSource.getName() + " server is Down", e);
			closeWebClient(webClient);
			return false;
		} catch (FailingHttpStatusCodeException | MalformedURLException e) {
			closeWebClient(webClient);
			LOGGER.error("ERROR. Please check the DataSource URL", e);
			return false;
		} catch (IOException e) {
			LOGGER.error("ERROR. Unknown error in DataSource " + dataSource.getName(), e);
			closeWebClient(webClient);
			return false;
		}
		return true;
	}

	private void closeWebClient(WebClient webClient) {
		if (webClient != null) {
			webClient.close();
		}
	}

	private WebClient disableExceptionThrowingInWebClient(WebClient webClient) {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		return webClient;
	}

}
