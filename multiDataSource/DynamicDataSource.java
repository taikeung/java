package com.fid.common.multiDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		//返回当前数据源  
        return DynamicDataSourceHolder.get();
	}

}
