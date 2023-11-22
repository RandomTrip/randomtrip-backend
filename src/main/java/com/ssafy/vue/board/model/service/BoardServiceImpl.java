package com.ssafy.vue.board.model.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ssafy.model.AttractionInfoDto;
import com.ssafy.model.service.AttractionAiService;
import com.ssafy.vue.board.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.vue.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper boardMapper;
	@Autowired
	private AttractionAiService attractionAiService;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}

	@Override
	@Transactional
	public void writeArticle(BoardDto boardDto) throws Exception {
		boardDto.setAttractionList(boardDto.getListAttraction().stream().collect(Collectors.joining(",")));
		// 서버 저장용 String 데이터 설정
		System.out.println(boardDto.getAttractionList());
		boardMapper.writeArticle(boardDto);
	}

	@Override
	@Transactional
	public void writeComment(CommentDto commentDto) throws Exception {
		boardMapper.writeComment(commentDto);
	}

	@Override
	@Transactional
	public void modifyComment(CommentDto commentDto) throws Exception {
		boardMapper.modifyComment(commentDto);
	}
	@Override
	@Transactional
	public void deleteComment(CommentDto commentDto) throws Exception {
		boardMapper.deleteComment(commentDto);
	}

	@Override
	@Transactional
	public void increaseCommentCount(int num) throws Exception {
		boardMapper.increaseCommentCount(num);
	}

	@Override
	@Transactional
	public void decreaseCommentCount(int num) throws Exception {
		boardMapper.decreaseCommentCount(num);
	}
	@Override
	public BoardListDto listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		param.put("key", key == null ? "" : key);

		String isPublic = "and is_public = 1";
		if ("user_id".equals(key)) {
			param.put("key", key == null ? "" : "b.user_id");
			isPublic = "";
		}
		param.put("isPublic", isPublic);

		List<BoardDto> list = boardMapper.listArticle(param);

		for(int i = 0; i < list.size(); i++) {
			BoardDto dto = list.get(i);
			dto.setListAttraction(Arrays.asList(dto.getAttractionList().split(",")));
			list.set(i, dto);
		}

//		if ("user_id".equals(key))
//			param.put("key", key == null ? "" : "user_id");
		int totalArticleCount = boardMapper.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		boardListDto.setCurrentPage(currentPage);
		boardListDto.setTotalPageCount(totalPageCount);

		return boardListDto;
	}

	@Override
	public List<CommentDto> listComment(BoardDto dto) throws Exception {
		return boardMapper.listComment(dto);
	}

//	@Override
//	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
//		PageNavigation pageNavigation = new PageNavigation();
//
//		int naviSize = SizeConstant.NAVIGATION_SIZE;
//		int sizePerPage = SizeConstant.LIST_SIZE;
//		int currentPage = Integer.parseInt(map.get("pgno"));
//
//		pageNavigation.setCurrentPage(currentPage);
//		pageNavigation.setNaviSize(naviSize);
//		Map<String, Object> param = new HashMap<String, Object>();
//		String key = map.get("key");
//		if ("userid".equals(key))
//			key = "user_id";
//		param.put("key", key == null ? "" : key);
//		param.put("word", map.get("word") == null ? "" : map.get("word"));
//		int totalCount = boardMapper.getTotalArticleCount(param);
//		pageNavigation.setTotalCount(totalCount);
//		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
//		pageNavigation.setTotalPageCount(totalPageCount);
//		boolean startRange = currentPage <= naviSize;
//		pageNavigation.setStartRange(startRange);
//		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
//		pageNavigation.setEndRange(endRange);
//		pageNavigation.makeNavigator();
//
//		return pageNavigation;
//	}

	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		return boardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) throws Exception {
		// TODO : BoardDaoImpl의 modifyArticle 호출

		boardDto.setAttractionList(boardDto.getListAttraction().stream().collect(Collectors.joining(",")));
		boardMapper.modifyArticle(boardDto);
	}

//	@Override
//	@Transactional
//	public void deleteArticle(int articleNo, String path) throws Exception {
//		// TODO : BoardDaoImpl의 deleteArticle 호출
//		List<FileInfoDto> fileList = boardMapper.fileInfoList(articleNo);
//		boardMapper.deleteFile(articleNo);
//		boardMapper.deleteArticle(articleNo);
//		for(FileInfoDto fileInfoDto : fileList) {
//			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
//			file.delete();
//		}
//	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		// TODO : BoardDaoImpl의 deleteArticle 호출
		boardMapper.deleteArticle(articleNo);
	}

	@Override
	public void setPublic(Map<String, Integer> map) throws Exception{
		boardMapper.setPublic(map);
	}

	@Override
	public Object getAiRecommendation(List<AttractionInfoDto> list) {
		return attractionAiService.getAiRecommendation(list);
	}

	@Override
	public void setDayPlans(ListTripPlanDto list) {
		List<TripPlanDto> tripList = list.getPlans();
		boardMapper.deleteDayPlan(tripList.get(0).getArticleNo());
		for(TripPlanDto dto : tripList) {
			boardMapper.setDayPlan(dto);
		}
	}

	@Override
	public List<TripPlanDto> getDayPlans(int articleNo) {

		return boardMapper.getDayPlans(articleNo);
	}


}
