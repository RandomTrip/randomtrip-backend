package com.ssafy.model.service;

import com.ssafy.model.AttractionInfoDto;
import com.ssafy.model.dao.AttractionDaoImpl;

import java.util.List;

public class AttractionServiceImpl implements AttractionService{

	// TODO : Singleton
	private static AttractionService attractionService = new AttractionServiceImpl();
	
	private AttractionServiceImpl() {}
	
	public static AttractionService getAttractionService() {
		return attractionService;
	}
	
	@Override
	public List<String> attractionList(AttractionInfoDto attractionInfoDto) {
		return  AttractionDaoImpl.getAttractionDao().attractionList(attractionInfoDto);
	}

	@Override
	public List<String> searchByTitle(String title, int sidoCode) {
		return AttractionDaoImpl.getAttractionDao().searchByTitle(title, sidoCode);
	}
}
