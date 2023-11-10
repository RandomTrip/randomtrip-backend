package com.ssafy.model.service;

import com.ssafy.model.AttractionInfoDto;

import java.util.List;

public interface AttractionService {

	List<String> attractionList(AttractionInfoDto attractionInfoDto);

	List<String> searchByTitle(String title, int sidoCode);
	
}
