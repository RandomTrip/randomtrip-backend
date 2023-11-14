package com.ssafy.model.dao;

import com.ssafy.model.AttractionInfoDto;
import com.ssafy.model.SidoDto;

import java.util.List;

public interface AttractionDao {

	List<String> attractionList(AttractionInfoDto attractionInfoDto);
	List<AttractionInfoDto> searchByTitle(String title, int sidoCode);
	public List<SidoDto> getSidoList();
}
