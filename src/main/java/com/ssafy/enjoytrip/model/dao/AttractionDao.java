package com.ssafy.enjoytrip.model.dao;

import java.util.List;

import com.ssafy.enjoytrip.model.AttractionInfoDto;

public interface AttractionDao {

	List<String> attractionList(AttractionInfoDto attractionInfoDto);
	List<String> searchByTitle(String title, int sidoCode);
}
