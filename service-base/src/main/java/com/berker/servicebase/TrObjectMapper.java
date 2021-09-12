package com.berker.servicebase;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrObjectMapper {
	
	public final static ObjectMapper mapper;
	
	static {
		mapper=new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		mapper.setVisibility(PropertyAccessor.FIELD,Visibility.ANY);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
	//	mapper.disable(feature)
	}
	
	

}
