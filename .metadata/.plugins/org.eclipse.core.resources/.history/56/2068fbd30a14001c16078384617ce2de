package com.orderManagement.orderManagement.feigns;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.orderManagement.orderManagement.dto.OmProductModel;
import com.orderManagement.orderManagement.dto.OmReduceFromStockModel;

@FeignClient(name = "PIM", path = "/product")
public interface ProductFeignClient {

	@GetMapping(path = "/getByIdList/{list}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OmProductModel>> getByIdList(@PathVariable(value = "list") List<Long> productIdList);
	
	@PostMapping(path="/reduceFromStock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity reduceFromStock(@RequestBody @Valid OmReduceFromStockModel reduceFromStock) ;
}
