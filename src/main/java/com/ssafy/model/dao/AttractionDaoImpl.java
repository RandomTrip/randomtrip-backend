package com.ssafy.model.dao;

import com.ssafy.model.AttractionInfoDto;
import com.ssafy.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttractionDaoImpl implements AttractionDao {

	// TODO : Singleton
	
	private static AttractionDao attraction = new AttractionDaoImpl();
	
	private AttractionDaoImpl() {}
	
	public static AttractionDao getAttractionDao() {
		return attraction;
	}
	
	
	@Override
	public List<String> attractionList(AttractionInfoDto attractionInfoDto) {
		
		List<String> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getInstance().getConnection();
			
			StringBuilder sql = new StringBuilder("select * \n");
			sql.append("from attraction_info\n");
			sql.append("where ");
			
			if(attractionInfoDto.getSidoCode() == 0) {
				sql.append("sido_code like '%' and ");
			}
			else {
				sql.append("sido_code = ? and ");
			}
			
			if(attractionInfoDto.getContentTypeId() == 0) {
				sql.append("content_type_id like '%'\n");
			}
			else {
				sql.append("content_type_id = ?\n");

			}
			sql.append("order by content_id desc limit 0, 20");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			if(attractionInfoDto.getSidoCode() != 0) {
				pstmt.setInt(1, attractionInfoDto.getSidoCode());
				
				if(attractionInfoDto.getContentTypeId() != 0) {
					pstmt.setInt(2, attractionInfoDto.getContentTypeId());
				}
			}
			else {
				if(attractionInfoDto.getContentTypeId() != 0) {
					pstmt.setInt(1, attractionInfoDto.getContentTypeId());
				}
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int contentId = rs.getInt(1);
				int contentTypeId = rs.getInt(2);
				String title = rs.getString(3);
				String addr1 = rs.getString(4);
				String addr2 = rs.getString(5);
				String zipcode = rs.getString(6);
				String tel = rs.getString(7);
				String firstImage = rs.getString(8);
				String firstImage2 = rs.getString(9);
				int readcount = rs.getInt(10);
				int sidoCode = rs.getInt(11);
				int gugunCode = rs.getInt(12);
				double latitude = rs.getInt(13);
				double longitude = rs.getInt(14);
				String mlevel = rs.getString(15);
				
				list.add(new AttractionInfoDto(contentId, contentTypeId, title, addr1, addr2, zipcode,
						tel, firstImage,firstImage2, readcount, sidoCode, gugunCode, latitude, longitude, mlevel).toString());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}
		
		return list;
	}

	@Override
	public List<String> searchByTitle(String title_, int sidoCode_) {
		
		List<String> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getInstance().getConnection();
			
			StringBuilder sql = new StringBuilder("select * \n");
			sql.append("from attraction_info\n");
			sql.append("where title like ?");
			
			if(sidoCode_ == 0) {
				sql.append(" and sido_code like '%'\n");
			}
			else {
				sql.append(" and sido_code = ?\n");

			}
			sql.append("order by content_id desc limit 0, 20");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, "%" + title_ + "%");
			if(sidoCode_ != 0) {
				pstmt.setInt(2, sidoCode_);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int contentId = rs.getInt(1);
				int contentTypeId = rs.getInt(2);
				String title = rs.getString(3);
				String addr1 = rs.getString(4);
				String addr2 = rs.getString(5);
				String zipcode = rs.getString(6);
				String tel = rs.getString(7);
				String firstImage = rs.getString(8);
				String firstImage2 = rs.getString(9);
				int readcount = rs.getInt(10);
				int sidoCode = rs.getInt(11);
				int gugunCode = rs.getInt(12);
				double latitude = rs.getInt(13);
				double longitude = rs.getInt(14);
				String mlevel = rs.getString(15);
				
				list.add(new AttractionInfoDto(contentId, contentTypeId, title, addr1, addr2, zipcode,
						tel, firstImage,firstImage2, readcount, sidoCode, gugunCode, latitude, longitude, mlevel).toString());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}
		
		return list;
	}
}
