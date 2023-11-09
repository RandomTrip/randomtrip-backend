package com.ssafy.board.model.service;

import java.util.List;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.dao.BoardDaoImpl;

// Business Logic
public class BoardServiceImpl implements BoardService {
	
//	TODO : BoardServiceImpl의 객체를 Singleton으로 새성하고 instance를 return하는 getBoardService()를 만드세요.
	
	private static BoardService boardService = new BoardServiceImpl();
	
	private BoardServiceImpl() {}
	
	public static BoardService getBoardService() {
		return boardService;
	}

//	END
	
	@Override
	public void registerArticle(BoardDto boardDto) {
		BoardDaoImpl.getBoardDao().registerArticle(boardDto);
	}

	@Override
	public List<BoardDto> searchListAll() { // int pg, String key, String word
		/*
		int end = pg * 20;
		int start = end - 20;
		*/
		return BoardDaoImpl.getBoardDao().searchListAll();
	}

	@Override
	public List<BoardDto> searchListBySubject(String subject) {
		return BoardDaoImpl.getBoardDao().searchListBySubject(subject);
	}

	@Override
	public BoardDto viewArticle(int no) {
		return BoardDaoImpl.getBoardDao().viewArticle(no);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) {
		BoardDaoImpl.getBoardDao().modifyArticle(boardDto);
	}

	@Override
	public void deleteArticle(int no) {
		BoardDaoImpl.getBoardDao().deleteArticle(no);
	}

}
