package com.ssafy.member.model.service;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.dao.MemberDao;
import com.ssafy.member.model.dao.MemberDaoImpl;

public class MemberServiceImpl implements MemberService {
	
	private static MemberServiceImpl memberService = new MemberServiceImpl();
	
	private MemberServiceImpl() {}

	public static MemberService getMemberService() {
		return memberService;
	}
	@Override
	public void registerMember(MemberDto memberDto) {
		MemberDaoImpl.getMemberDao().registerMember(memberDto);
	}

	@Override
	public MemberDto login(String userId, String userPass) {
		return MemberDaoImpl.getMemberDao().login(userId, userPass);
	}

	@Override
	public void modifyMember(MemberDto memberDto) {
		MemberDaoImpl.getMemberDao().modifyMember(memberDto);

	}

	@Override
	public void deleteMember(String userId) {
		MemberDaoImpl.getMemberDao().deleteMember(userId);

	}

}
