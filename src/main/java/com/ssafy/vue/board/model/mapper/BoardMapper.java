package com.ssafy.vue.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.vue.board.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

	void writeArticle(BoardDto boardDto) throws SQLException;
	void writeComment(CommentDto commentDto) throws SQLException;
	void modifyComment(CommentDto commentDto) throws SQLException;
	void deleteComment(CommentDto commentDto) throws SQLException;
	void increaseCommentCount(int num) throws SQLException;
	void decreaseCommentCount(int num) throws SQLException;


	void registerFile(BoardDto boardDto) throws Exception;

	List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;

	List<CommentDto> listComment(BoardDto commentDto) throws SQLException;

	void setPublic(Map<String, Integer> map) throws Exception;

	int getTotalArticleCount(Map<String, Object> param) throws SQLException;

	BoardDto getArticle(int articleNo) throws SQLException;

	void updateHit(int articleNo) throws SQLException;

	void modifyArticle(BoardDto boardDto) throws SQLException;

	void deleteFile(int articleNo) throws Exception;

	void deleteArticle(int articleNo) throws SQLException;

	void setDayPlan(TripPlanDto dto);

	void deleteDayPlan(int articleNo);

	List<TripPlanDto> getDayPlans(int articleNo);


//	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
}
