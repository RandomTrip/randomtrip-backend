package com.ssafy.model.service;

import com.ssafy.model.AttractionInfoDto;
import com.ssafy.model.SidoDto;

import java.util.List;

public interface AttractionService {

	List<String> attractionList(AttractionInfoDto attractionInfoDto);

	List<AttractionInfoDto> searchByTitle(String title, int sidoCode);

	List<SidoDto> sidoList();

}
