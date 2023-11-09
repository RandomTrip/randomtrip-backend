package com;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.dao.MemberDao;
import com.ssafy.member.model.dao.MemberDaoImpl;

@RestController
public class UserController {
	
	MemberDao dao = MemberDaoImpl.getMemberDao();
	
	@PostMapping("/user/login")
	public String login(@RequestParam("id")String id, @RequestParam("pw")String pw) {
		
		MemberDto dto = dao.login(id, pw);
		if(dto.getUserId() == null)
			return "로그인 실패 ㅠㅠ";
		else
			return "로그인 성공 !!";
	}
	
	@PostMapping("/user")
	public void login(MemberDto member) {
		dao.registerMember(member);
	}
	
	@PostMapping("/user/modify")
	public void modify(MemberDto member) {
		dao.modifyMember(member);
	}
	
	@PostMapping("/user/delete")
	public void delete(String id) {
		dao.deleteMember(id);
	}
}
