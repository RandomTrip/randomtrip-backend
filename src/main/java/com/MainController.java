package com;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.model.AttractionInfoDto;
import com.ssafy.enjoytrip.model.service.AttractionService;
import com.ssafy.enjoytrip.model.service.AttractionServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(tags = "Attraction", description = "Attraction 관련 API")
public class MainController {
	
	private AttractionService as = AttractionServiceImpl.getAttractionService();

	@GetMapping
	@ApiOperation(value = "모든 명소 리스트 반환", notes = "모든 명소의 리스트를 반환합니다.")
	public List<String> getAll() {
		AttractionInfoDto dto = new AttractionInfoDto();
		dto.setSidoCode(0);
		dto.setContentTypeId(0);
		
		List<String> result = as.attractionList(dto);
		
		return result;
	}
	
	@GetMapping("/sido")
	@ApiOperation(value = "특정 시도의 명소 리스트 반환", notes = "특정 시도의 명소 리스트를 반환합니다.")
	public List<String> getbySido(@RequestParam("sido") int sido) {
		AttractionInfoDto dto = new AttractionInfoDto();
		dto.setSidoCode(sido);
		dto.setContentTypeId(0);
		return as.attractionList(dto);
	}
	
	@GetMapping("/content")
	@ApiOperation(value = "특정 종류의 명소 리스트 반환", notes = "특정 종류의 명소 리스트를 반환합니다.")
	public List<String> getbyContent(@RequestParam("content") int content) {
		AttractionInfoDto dto = new AttractionInfoDto();
		dto.setSidoCode(0);
		dto.setContentTypeId(content);
		return as.attractionList(dto);
	}
	
	@GetMapping("/sido/content")
	@ApiOperation(value = "특정 시도와 종류의 명소 리스트 반환", notes = "특정 시도와 종류의 명소 리스트를 반환합니다.")
	public List<String> getbyContent(@RequestParam("sido") int sido, @RequestParam("content") int content) {
		AttractionInfoDto dto = new AttractionInfoDto();
		dto.setSidoCode(sido);
		dto.setContentTypeId(content);
		return as.attractionList(dto);
	}
	
}
