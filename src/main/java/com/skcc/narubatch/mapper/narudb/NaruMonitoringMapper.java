package com.skcc.narubatch.mapper.narudb;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NaruMonitoringMapper {
	
	public int insertEigwMonOnlineError(Map<String, Object> reqMap);

}
