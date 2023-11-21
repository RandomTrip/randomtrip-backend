package com.ssafy.vue.board.controller;

import com.ssafy.model.AttractionDetailDto;
import com.ssafy.model.AttractionInfoDto;
import com.ssafy.model.SidoDto;
import com.ssafy.model.service.AttractionAiService;
import com.ssafy.model.service.AttractionService;
import com.ssafy.model.service.AttractionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "Attraction", description = "Attraction 관련 API")
public class MainController {


	@Autowired
	AttractionAiService attractionAiService;
	private AttractionService as = AttractionServiceImpl.getAttractionService();
	// @GetMapping
	// @ApiOperation(value = "모든 명소 리스트 반환", notes = "모든 명소의 리스트를 반환합니다.")
	public List<AttractionInfoDto> getAll() {
		AttractionInfoDto dto = new AttractionInfoDto();
		dto.setSidoCode(0);
		dto.setContentTypeId(0);
		List<AttractionInfoDto> result = as.attractionList(dto);
		return result;
	}
	@GetMapping("/sido")
	@ApiOperation(value = "특정 시도의 명소 리스트 반환", notes = "특정 시도의 명소 리스트를 반환합니다.")
	public List<AttractionInfoDto> getbySido(@RequestParam("sido") int sido) {
		AttractionInfoDto dto = new AttractionInfoDto();
		dto.setSidoCode(sido);
		dto.setContentTypeId(0);
		return as.attractionList(dto);
	}

	@GetMapping("/search")
	@ApiOperation(value = "이름으로 검색", notes = "이름으로 명소를 검색하고 결과 리스트를 반환합니다.")
	public List<AttractionInfoDto> search(@RequestParam("title") String title) {
		return as.searchByTitle(title, 0);
	}


	@GetMapping("/send")
	public Object sendData() throws IOException {

		return attractionAiService.sendRequest();
	}

	@GetMapping("/sidolist")
	@ApiOperation(value = "전국 시도 리스트 반환", notes = "특정 시도의 리스트를 반환합니다.")
	public List<SidoDto> getSidoList() {
		return as.sidoList();
	}


	@GetMapping("/attractionlist")
	@ApiOperation(value = "여행지 리스트 정보 가져오기", notes = "content_id 리스트를 받아서 해당하는 여행지의 데이터를 반환합니다. ex) http://localhost/vue/attractionlist?list=125411,125418,125431,125448,125465,125478 : 여행지 리스트 전체 데이터 순서대로 반환. http://localhost/vue/attractionlist?list=125411 : 125411(한개) 번 여행지 데이터 반환")
	public List<AttractionInfoDto> getbyContentIdList(@RequestParam("list") String ids) {


		List<String> idList = Arrays.asList(ids.split(","));

		return as.getAttractionData(idList);
	}

	// @GetMapping("/content")
	// @ApiOperation(value = "특정 종류의 명소 리스트 반환", notes = "특정 종류의 명소 리스트를 반환합니다.")
	public List<AttractionInfoDto> getbyContent(@RequestParam("content") int content) {
		AttractionInfoDto dto = new AttractionInfoDto();
		dto.setSidoCode(0);
		dto.setContentTypeId(content);
		return as.attractionList(dto);
	}
	// @GetMapping("/sido/content")
	// @ApiOperation(value = "특정 시도와 종류의 명소 리스트 반환", notes = "특정 시도와 종류의 명소 리스트를 반환합니다.")
	public List<AttractionInfoDto> getbyContent(@RequestParam("sido") int sido, @RequestParam("content") int content) {
		AttractionInfoDto dto = new AttractionInfoDto();
		dto.setSidoCode(sido);
		dto.setContentTypeId(content);
		return as.attractionList(dto);
	}
}
