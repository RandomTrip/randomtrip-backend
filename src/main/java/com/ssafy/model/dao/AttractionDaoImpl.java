package com.ssafy.model.dao;

import com.ssafy.model.AttractionInfoDto;
import com.ssafy.model.SidoDto;
import com.ssafy.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AttractionDaoImpl implements AttractionDao {

	// TODO : Singleton
	
	private static AttractionDao attraction = new AttractionDaoImpl();
	
	private AttractionDaoImpl() {}
	
	public static AttractionDao getAttractionDao() {
		return attraction;
	}

    public List<SidoDto> getSidoList() {
		List<SidoDto> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getInstance().getConnection();
			StringBuilder sql = new StringBuilder("SELECT * FROM sido;");

			PreparedStatement pstat = conn.prepareStatement(sql.toString());

			rs = pstat.executeQuery();

			while(rs.next()) {
				list.add(new SidoDto(Integer.parseInt(rs.getString("sido_code")),rs.getString("sido_name")));
			}

			return list;

		}catch(Exception e) {

		}

		return null;
    }


    @Override
	public List<AttractionInfoDto> attractionList(AttractionInfoDto attractionInfoDto) {
		
		List<AttractionInfoDto> list = new ArrayList<>();
		
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
				double latitude = rs.getDouble(13);
				double longitude = rs.getDouble(14);
				String mlevel = rs.getString(15);
				
				list.add(new AttractionInfoDto(contentId, contentTypeId, title, addr1, addr2, zipcode,
						tel, firstImage,firstImage2, readcount, sidoCode, gugunCode, latitude, longitude, mlevel));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}
		
		return list;
	}

	@Override
	public List<AttractionInfoDto> searchByTitle(String title_, int sidoCode_) {

		List<AttractionInfoDto> list = new ArrayList<>();

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
				double latitude = rs.getDouble(13);
				double longitude = rs.getDouble(14);
				String mlevel = rs.getString(15);

				list.add(new AttractionInfoDto(contentId, contentTypeId, title, addr1, addr2, zipcode,
						tel, firstImage,firstImage2, readcount, sidoCode, gugunCode, latitude, longitude, mlevel));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}

		return list;
	}





	@Override
	public List<AttractionInfoDto> getAttractionData(List<String> l) {

		List<AttractionInfoDto> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getInstance().getConnection();

			StringBuilder sql = new StringBuilder("select * \n");
			sql.append("from attraction_info\n");
			sql.append("where content_id = ");
			sql.append(l.get(0));

			for(int i = 1; i < l.size(); i++){
				sql.append(" or content_id=");
				sql.append(l.get(i));
			}


			pstmt = conn.prepareStatement(sql.toString());



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
				double latitude = rs.getDouble(13);
				double longitude = rs.getDouble(14);
				String mlevel = rs.getString(15);

				list.add(new AttractionInfoDto(contentId, contentTypeId, title, addr1, addr2, zipcode,
						tel, firstImage,firstImage2, readcount, sidoCode, gugunCode, latitude, longitude, mlevel));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}



		Comparator<AttractionInfoDto> contentIdComparator = Comparator.comparing(AttractionInfoDto::getContentId);

		// list 를  l 기반으로 정렬
		List<AttractionInfoDto> attractionList = new ArrayList<>();

		for(String s : l){
			for(AttractionInfoDto a : list) {
				if(a.getContentId() == Integer.parseInt(s)){
					attractionList.add(a);
					break;
				}
			}
		}


		return attractionList;
	}

	@Override
	public List<AttractionInfoDto> getNearbyAttractions(double latitude_p, double longitude_p, int contentTypeId_p, int count) {




		List<AttractionInfoDto> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getInstance().getConnection();
//SELECT * FROM enjoytrip.attraction_info where content_type_id = 12 and sido_code = 32 order by (abs(latitude - 37.522513) + abs(longitude - 128.291911)) limit 4;
			StringBuilder sql = new StringBuilder("select * \n");
			sql.append("from attraction_info\n");
			sql.append("where content_type_id = " + contentTypeId_p);
			sql.append(" order by (abs(latitude - " + latitude_p + ") + abs(longitude - " + longitude_p + ")) limit " + String.valueOf(count));


			pstmt = conn.prepareStatement(sql.toString());



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
				double latitude = rs.getDouble(13);
				double longitude = rs.getDouble(14);
				String mlevel = rs.getString(15);

				list.add(new AttractionInfoDto(contentId, contentTypeId, title, addr1, addr2, zipcode,
						tel, firstImage,firstImage2, readcount, sidoCode, gugunCode, latitude, longitude, mlevel));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.getInstance().close(pstmt, conn);
		}


		System.out.println(list);
		return list;
	}
}
