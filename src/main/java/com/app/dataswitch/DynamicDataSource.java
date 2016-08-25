package com.app.dataswitch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

	@Override
	protected Object determineCurrentLookupKey() {

		String status = DataSourceSwitcher.getDataSource();
		if (status != null) {
			logger.info("切换到：" + DataSourceSwitcher.getDataSource());
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("切换到：只读库");
			}
		}
		return DataSourceSwitcher.getDataSource();
	}

}