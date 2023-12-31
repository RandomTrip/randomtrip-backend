package com.ssafy.vue.member.model.service;

import com.ssafy.vue.member.model.MemberDto;
import com.ssafy.vue.member.model.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;

	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		return memberMapper.login(memberDto);
	}

	@Override
	public int signUp(MemberDto memberDto) throws Exception {

		MemberDto dto = memberMapper.userInfo(memberDto.getUserId());

		System.out.println("이미 존재하는 유저인가요? : " + dto);
		if(dto == null) {
			memberMapper.signUp(memberDto);
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public int updateUser(MemberDto memberDto) throws Exception {

		MemberDto dto = memberMapper.userInfo(memberDto.getUserId());

		System.out.println("이미 존재하는 유저인가요? : " + dto);
		if(dto == null) {
			return 0;
		}else {
			memberMapper.updateUser(memberDto);
			return 1;
		}
	}

	@Override
	public MemberDto userInfo(String userId) throws Exception {
		return memberMapper.userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberMapper.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}

}
