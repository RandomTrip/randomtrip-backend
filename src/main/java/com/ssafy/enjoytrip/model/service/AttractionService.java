package com.ssafy.enjoytrip.model.service;

import java.util.List;

import com.ssafy.enjoytrip.model.AttractionInfoDto;

public interface AttractionService {

	List<String> attractionList(AttractionInfoDto attractionInfoDto);

	List<String> searchByTitle(String title, int sidoCode);
	
}
