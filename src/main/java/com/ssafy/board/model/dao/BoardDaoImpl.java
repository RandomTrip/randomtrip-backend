package com.ssafy.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.board.model.BoardDto;
import com.ssafy.util.DBUtil;

public class BoardDaoImpl implements BoardDao {
	
	private static BoardDao boardDao = new BoardDaoImpl();

	private BoardDaoImpl() {}

	public static BoardDao getBoardDao() {
		return boardDao;
	}

	@Override
	public void registerArticle(BoardDto boardDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
//		TODO : boardDto의 내용을 board table에 insert 하세요!!!
		try {
			
			conn = DBUtil.getInstance().getConnection();
			
			StringBuilder sql = new StringBuilder("insert into board (user_id, subject, content) \n");
			sql.append("values (?, ?, ?)");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, boardDto.getUserId());
			pstmt.setString(2, boardDto.getSubject());
			pstmt.setString(3, boardDto.getContent());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}
//		END
	}

	@Override
	public List<BoardDto> searchListAll() {
		List<BoardDto> list = new ArrayList<BoardDto>();
//		TODO : board table의 모든 글정보를 글번호순으로 정렬하여 list에 담고 return 하세요!!!
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getInstance().getConnection();
			
			StringBuilder sql = new StringBuilder("select article_no, user_id, subject, content, register_time \n");
			sql.append("from board\n");
			sql.append("order by article_no desc limit 0, 20");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setArticleNo(rs.getInt("article_no"));
				boardDto.setUserId(rs.getString("user_id"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setRegisterTime(rs.getString("register_time"));
				
				list.add(boardDto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}
//		END
		return list;
	}

	@Override
	public List<BoardDto> searchListBySubject(String subject) {
		List<BoardDto> list = new ArrayList<BoardDto>();
//		TODO : board table에서 제목에 subject를 포함하고 있는 글정보를 list에 담고 return 하세요!!!
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getInstance().getConnection();
			
			StringBuilder sql = new StringBuilder("select article_no, user_id, subject, content, register_time \n");
			sql.append("from board\n");
			sql.append("where subject like ?");
			sql.append("order by article_no desc limit 0, 20");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, "%" + subject + "%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardDto.setArticleNo(rs.getInt("article_no"));
				boardDto.setUserId(rs.getString("user_id"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				boardDto.setRegisterTime(rs.getString("register_time"));
				
				list.add(boardDto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}
//		END
		return list;
	}

	@Override
	public BoardDto viewArticle(int no) {
		BoardDto boardDto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		TODO : board table에서 글번호가 no인 글 한개를 return 하세요!!!
		try {
			conn = DBUtil.getInstance().getConnection();
			
			StringBuilder sql = new StringBuilder("select article_no, user_id, subject, content, register_time \n");
			sql.append("from board\n");
			sql.append("where article_no = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			boardDto = new BoardDto();
			boardDto.setArticleNo(rs.getInt("article_no"));
			boardDto.setUserId(rs.getString("user_id"));
			boardDto.setSubject(rs.getString("subject"));
			boardDto.setContent(rs.getString("content"));
			boardDto.setRegisterTime(rs.getString("register_time"));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}
//		END
		return boardDto;
	}

	@Override
	public void modifyArticle(BoardDto boardDto) {
//		TODO : boardDto의 내용을 이용하여 글번호에 해당하는 글제목과 내용을 수정하세요!!!
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtil.getInstance().getConnection();
			
			StringBuilder sql = new StringBuilder("update board\n");
			sql.append("set subject = ?, content = ?");
			sql.append("where article_no = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, boardDto.getSubject());
			pstmt.setString(2, boardDto.getContent());
			pstmt.setInt(3, boardDto.getArticleNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}
//		END
	}

	@Override
	public void deleteArticle(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
//		TODO : board table에서 글번호가 no인 글 정보를 삭제하세요!!!
		try {
			conn = DBUtil.getInstance().getConnection();
			
			StringBuilder sql = new StringBuilder("delete from board\n");
			sql.append("where article_no = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}
//		END
	}

}
