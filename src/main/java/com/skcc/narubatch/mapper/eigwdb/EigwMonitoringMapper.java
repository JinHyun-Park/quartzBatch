package com.skcc.narubatch.mapper.eigwdb;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EigwMonitoringMapper {
	public List<Map<String, Object>> selectEigwOnlineError();

}
