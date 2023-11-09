package com.ssafy.member.model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ssafy.board.model.BoardDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.util.DBUtil;

public class MemberDaoImpl implements MemberDao {
	private static MemberDao memberDao = new MemberDaoImpl();
	
	private MemberDaoImpl() {}

	public static MemberDao getMemberDao() {
		return memberDao;
	}
	@Override
	public void registerMember(MemberDto memberDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn=DBUtil.getInstance().getConnection();
			StringBuilder sql = new StringBuilder("insert into members(userId,userPass,userName)\n");
			sql.append("values (?,?,?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, memberDto.getUserId());
			pstmt.setString(2, memberDto.getUserPass());
			pstmt.setString(3, memberDto.getUserName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt,conn);
		}
	}

	@Override
	public MemberDto login(String userId, String userPass) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDto memberDto = new MemberDto();
		
		try {
			conn=DBUtil.getInstance().getConnection();
			StringBuilder sql = new StringBuilder("select * \n");
			sql.append("from members \n");
			sql.append("where userId = ? and userPass=?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,userId);
			pstmt.setString(2,userPass);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				memberDto.setUserId(rs.getString("userId"));
				memberDto.setUserPass(rs.getString("userPass"));
				memberDto.setUserName(rs.getString("userName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.getInstance().close(rs,pstmt,conn);
		}
//		END
		return memberDto;
		
	}

	@Override
	public void modifyMember(MemberDto memberDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn=DBUtil.getInstance().getConnection();
			StringBuilder sql = new StringBuilder("update members set userPass=?,userName=?\n");
			sql.append("where userId=?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, memberDto.getUserPass());
			pstmt.setString(2, memberDto.getUserName());
			pstmt.setString(3, memberDto.getUserId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt,conn);
		}

	}

	@Override
	public void deleteMember(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn=DBUtil.getInstance().getConnection();
			StringBuilder sql = new StringBuilder("delete from members \n");
			sql.append("where userId=?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt,conn);
		}

	}

}
