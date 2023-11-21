package com.ssafy.model.dao;

import com.ssafy.model.AttractionInfoDto;
import com.ssafy.model.SidoDto;

import java.util.List;

public interface AttractionDao {

	List<AttractionInfoDto> attractionList(AttractionInfoDto attractionInfoDto);
	List<AttractionInfoDto> searchByTitle(String title, int sidoCode);
	public List<SidoDto> getSidoList();

	List<AttractionInfoDto> getAttractionData(List<String> l);

	List<AttractionInfoDto> getNearbyAttractions(double latitude, double longitude, int contentTypeId, int count);
}
