package com.ssafy.vue.board.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.model.AttractionInfoDto;
import com.ssafy.model.service.AttractionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.board.model.BoardDto;
import com.ssafy.vue.board.model.BoardListDto;
import com.ssafy.vue.board.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//http://localhost/vue/swagger-ui.html
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/board")
@Api("게시판 컨트롤러  API V1")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
//	private static final String SUCCESS = "success";
//	private static final String FAIL = "fail";

	private BoardService boardService;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}



	/*

post
http://localhost/vue/board
{
    "userId": "admin",
    "userName": "사용자 이름",
    "subject": "글 제목",
    "content": "글 내용",
    "attractionList": "125411,125418,125431,125448,125465,125478",
    "isPublic": 0,
    "category": 1
}

*/
	@ApiOperation(value = "나의 여행 계획작성", notes = "나의 여행 계획. attractionList 는 content_id  띄어쓰기 없이 , 로 구분 :(ex =>'125411,125418,125431,125448,125465,125478') isPublic 는 공개 여부 (0: 비공개, 1: 공개) 공개 여부는 나중에 수정 가능")
	@PostMapping
	public ResponseEntity<?> writeArticle(
			@RequestBody @ApiParam(value = "게시글 정보.", required = true) BoardDto boardDto) {
		logger.info("writeArticle boardDto - {}", boardDto);
		try {
			System.out.println(boardDto);
			boardService.writeArticle(boardDto);

			//			return ResponseEntity.ok().build();
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// http://localhost/vue/board?pgno=2&&spp20
	// http://localhost/vue/board?pgno=1&&spp20
	@ApiOperation(value = "여행정보 목록 (나의 여행 계획, 공유된 여행 계획)", notes = "http://localhost/vue/board?pgno=1&&spp=20&&key=user_id&&word=admin: user_id가 'admin'인 유저의 '나의 여행 계획' 20개씩 페이징해서 1페이지 데이터를 리턴.  pgno=1&&spp20: 20개씩 1페이지. 모든 유저의 여행 계획 공유 리스트 (is_public 이 1 인 게시물만을 읽어옴.)", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping
	public ResponseEntity<?> listArticle(
			@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
		logger.info("listArticle map - {}", map);
		try {
			BoardListDto boardListDto = boardService.listArticle(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// @ApiOperation(value = "공유 가능 설정", notes = "http://localhost/vue/board/public?articleno=3&ispublic=1  : 3번 게시물을 공유 가능(1)으로 수정", response = BoardDto.class)
	// @PutMapping("public")
	public BoardDto setPublic(
			@RequestParam @ApiParam(value = "공유가능 유무를 설정하기 위한 매개변수 (articleno: 3, ispublic: 0, 1)", required = true) Map<String, String> map)
			throws Exception {

		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("articleno", Integer.parseInt(map.get("articleno"))); // articleNo 값 설정
		paramMap.put("ispublic", Integer.parseInt(map.get("ispublic"))); // isPublic 값 설정



		boardService.setPublic(paramMap);

		return null;
	}



	@ApiOperation(value = "여행 계획 상세보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다. attraction list의 정보도 여기서 전부 받아옵니다.", response = BoardDto.class)
	@GetMapping("/{articleno}")
	public Map<String, Object> getArticle(
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		logger.info("getArticle - 호출 : " + articleno);
		System.out.println(articleno);

		BoardDto dto = boardService.getArticle(articleno);
		List<AttractionInfoDto> list = new MainController().getbyContentIdList(dto.getAttractionList());

		Map<String, Object> map = new HashMap<>();
		map.put("board", dto);
		map.put("attractionInfo", list);

		return map;
	}

	// @ApiOperation(value = "수정 할 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	// @PutMapping("/{articleno}")
	public ResponseEntity<BoardDto> getModifyArticle(
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		logger.info("getModifyArticle - 호출 : " + articleno);
		return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
	}



	/*
put
http://localhost/vue/board
{
	"articleNo": 32,
    "subject": "수정된 글 제목",
    "content": "수정된 글 내용",
    "attractionList": "125465,125478",
    "isPublic": 1,
    "category": 1
}
	*/

	@ApiOperation(value = "여행계획 수정", notes = "수정할 게시글 정보(articleNo, subject, content, attractionList, isPublic, category) " +
			"예시)" +
			"" +
			"put\n" +
			"http://localhost/vue/board\n" +
			"{\n" +
			"\t\"articleNo\": 32,\n" +
			"    \"subject\": \"수정된 글 제목\",\n" +
			"    \"content\": \"수정된 글 내용\",\n" +
			"    \"attractionList\": \"125465,125478\",\n" +
			"    \"isPublic\": 1,\n" +
			"    \"category\": 1\n" +
			"}" +
			"" +
			"를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception {
		logger.info("modifyArticle - 호출 {}", boardDto);

		boardService.modifyArticle(boardDto);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "여행계획 글삭제", notes = "글번호(article_no)에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleno") @ApiParam(value = "삭제할 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("deleteArticle - 호출");
		boardService.deleteArticle(articleno);
		return ResponseEntity.ok().build();

	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}