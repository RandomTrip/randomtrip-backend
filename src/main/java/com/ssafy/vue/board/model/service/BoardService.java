package com.ssafy.vue.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.model.AttractionInfoDto;
import com.ssafy.vue.board.model.*;

public interface BoardService {

	void writeArticle(BoardDto boardDto) throws Exception;
	void writeComment(CommentDto commentDto) throws Exception;
	public void modifyComment(CommentDto commentDto) throws Exception;
	void deleteComment(CommentDto commentDto) throws Exception;
	void increaseCommentCount(int num) throws Exception;
	void decreaseCommentCount(int num) throws Exception;

	BoardListDto listArticle(Map<String, String> map) throws Exception;
	List<CommentDto> listComment(BoardDto dto) throws Exception;
//	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	
	void modifyArticle(BoardDto boardDto) throws Exception;
//	
	void deleteArticle(int articleNo) throws Exception;

	public void setPublic(Map<String, Integer> map) throws Exception;

    Object getAiRecommendation(List<AttractionInfoDto> list);

	void setDayPlans(ListTripPlanDto list);

	List<TripPlanDto> getDayPlans(int articleNo);
}
