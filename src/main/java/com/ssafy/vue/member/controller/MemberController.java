package com.ssafy.vue.member.controller;

import com.ssafy.vue.member.model.MemberDto;
import com.ssafy.vue.member.model.service.MemberService;
import com.ssafy.vue.util.JWTUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class MemberController {

	private MemberService memberService;
	private JWTUtil jwtUtil;

	public MemberController(MemberService memberService, JWTUtil jwtUtil) {
		super();
		this.memberService = memberService;
		this.jwtUtil = jwtUtil;
	}

	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		log.debug("login user : {}", memberDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if(loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);

//				발급받은 refresh token을 DB에 저장.
				memberService.saveRefreshToken(loginUser.getUserId(), refreshToken);

//				JSON으로 token 전달.
				resultMap.put("accessToken", accessToken);
				resultMap.put("refreshToken", refreshToken);

				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			}

		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}




	@ApiOperation(value = "회원가입", notes = "회원가입 처리.")
	@PostMapping
	public ResponseEntity<Map<String, Object>> signUp(
			@RequestBody @ApiParam(value = "user_id, user_name, user_password, email_id, email_domain", required = true) MemberDto memberDto) {
		log.debug("login user : {}", memberDto);

		try {
			int result = memberService.signUp(memberDto);

			if(result == 1) {
				System.out.println("회원가입 시도 !!");
				return login(memberDto);
			}else {

				HttpStatus status = HttpStatus.BAD_REQUEST;
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("result", "이미 존재하는 유저입니다.");
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
		}catch(Exception e) {
			e.printStackTrace();
			HttpStatus status = HttpStatus.BAD_REQUEST;
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("result", "서버 에러가 발생한듯");
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
		}
	}


	@ApiOperation(value = "유저 정보 수정", notes = "userId 유저의 유저 이름(userName), 유저 이메일(emailId), 이메일 도메인(emailDomain) 를 수정합니다." +
			"예시)" +
			"post" +
			"http://localhost/vue/user" +
			"{\n" +
			"    \"userId\": \"ssafy\",\n" +
			"    \"userName\": \"new name\",\n" +
			"    \"emailId\": \"new id\",\n" +
			"    \"emailDomain\": \"new domain.com\"\n" +
			"}")
	@PutMapping
	public ResponseEntity<Map<String, Object>> updateUser(
			@RequestBody @ApiParam(value = "userId, userName, emailId, emailDomain", required = true) MemberDto memberDto) {
		log.debug("login user : {}", memberDto);

		try {
			int result = memberService.updateUser(memberDto);

			if(result == 1) {
				HttpStatus status = HttpStatus.ACCEPTED;
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("result", "수정 성공");
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}else {

				HttpStatus status = HttpStatus.BAD_REQUEST;
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("result", "존재하지 않는 유저입니다.");
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
		}catch(Exception e) {
			e.printStackTrace();
			HttpStatus status = HttpStatus.BAD_REQUEST;
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("result", "서버 에러가 발생한듯");
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
		}
	}


	/*
get
http://localhost/vue/user/info/{user_id}
http://localhost/vue/user/info/ssafy

header
key		: Authorization
value	: eyJ0eXAiOiJKV..... (refresh token)

	*/
	@ApiOperation(value = "회원 정보 가져오기(회원인증)", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userId);
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(memberDto.getUserId());
				log.debug("token : {}", accessToken);
				log.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
