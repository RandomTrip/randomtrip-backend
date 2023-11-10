package com.ssafy.model.dao;

import com.ssafy.model.AttractionInfoDto;

import java.util.List;

public interface AttractionDao {

	List<String> attractionList(AttractionInfoDto attractionInfoDto);
	List<String> searchByTitle(String title, int sidoCode);
}
