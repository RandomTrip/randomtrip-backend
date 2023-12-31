package com.ssafy.vue.board.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ssafy.model.AttractionInfoDto;
import com.ssafy.model.service.AttractionAiService;
import com.ssafy.model.service.AttractionServiceImpl;
import com.ssafy.vue.board.BoardBigData;
import com.ssafy.vue.board.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


	@ApiOperation(value = "나의 여행 계획작성", notes = "나의 여행 계획. " +
			"\npostData 와 daysPlan 를 같이 보내주면 됩니다.\n" +
			" - post 예시" +
			"post\n" +
			"http://localhost/vue/board" +
			"{\n" +
			"  \"postData\": {\n" +
			"    \"userId\": \"admin\",\n" +
			"    \"userName\": \"사용자 이름\",\n" +
			"    \"subject\": \"글 제목\",\n" +
			"    \"content\": \"글 내용\",\n" +
			"    \"listAttraction\": [\n" +
			"      \"125411\",\n" +
			"      \"125418\",\n" +
			"      \"125431\"\n" +
			"    ],\n" +
			"    \"isPublic\": 1,\n" +
			"    \"category\": 1\n" +
			"  },\n" +
			"  \"daysPlan\": {\n" +
			"    \"plans\": [\n" +
			"      {\n" +
			"        \"articleNo\": null,\n" +
			"        \"dayNo\": 1,\n" +
			"        \"listAttraction\": [\n" +
			"          125411,\n" +
			"          125418,\n" +
			"          125431\n" +
			"        ]\n" +
			"      },\n" +
			"      {\n" +
			"        \"articleNo\": null,\n" +
			"        \"dayNo\": 2,\n" +
			"        \"listAttraction\": [\n" +
			"          125465,\n" +
			"          125478\n" +
			"        ]\n" +
			"      }\n" +
			"    ]\n" +
			"  }\n" +
			"}" +
			"" +
			"isPublic 는 공개 여부 (0: 비공개, 1: 공개) 공개 여부는 나중에 수정 가능")
	@PostMapping
	public ResponseEntity<?> writeArticle(
			@RequestBody @ApiParam(value = "게시글 정보.", required = true) BoardBigData data) {


		BoardDto boardDto = data.getPostData();
		ListTripPlanDto list = data.getDaysPlan();



		logger.info("writeArticle boardDto - {}", boardDto);
		try {
			System.out.println(boardDto);
			int no = boardService.writeArticle(boardDto);

			System.out.println(no);

			ListTripPlanDto dto = boardService.settingDayPlansData(list, no);

			setDayPlans(dto);

			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}






	@ApiOperation(value = "댓글 작성", notes = "" +
			"" +
			"예시)" +
			"" +
			"post" +
			"http://localhost/vue/board/comments" +
			"{\n" +
			"    \"userId\": \"ssafy\",\n" +
			"    \"articleNo\": \"32\",\n" +
			"    \"content\": \"추가된 댓글입니다 ㅋ !!!!!\"\n" +
			"}")
	@PostMapping("/comments")
	public ResponseEntity<?> writeComment(
			@RequestBody @ApiParam(value = "게시글 정보.", required = true) CommentDto commentDto) {
		try {
			System.out.println(commentDto);

			boardService.writeComment(commentDto);
			boardService.increaseCommentCount(commentDto.getArticleNo());

			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "댓글 삭제", notes = "" +
			"예시" +
			"post" +
			"http://localhost/vue/board/comments" +
			"{\n" +
			"    \"commentNo\": 1,\n" +
			"    \"articleNo\": 32\n" +
			"}" +
			"32번 게시물의 1번 댓글 삭제." +
			"")
	@DeleteMapping("/comments")
	public ResponseEntity<?> deleteComment(
			@RequestBody @ApiParam(value = "게시글 정보.", required = true) CommentDto commentDto) {
		try {
			System.out.println(commentDto);

			boardService.deleteComment(commentDto);
			boardService.decreaseCommentCount(commentDto.getArticleNo());

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



/*
	@ApiOperation(value = "여행정보 목록 (나의 여행 계획, 공유된 여행 계획)", notes = "" +
			"\n" +
			"http://localhost/vue/board/dayPlans: 일차별 여행지의 데이터를 저장한다.\n" +
			"수정할때도 그냥 이렇게 요청 보내면 됩니다." +
			"\n" +
			"\n" +
			"예시)\n " +
			"get \n" +
			"http://localhost/vue/board/daysPlan" +
			"{\n" +
			"    \"plans\": [\n" +
			"        {\n" +
			"            \"articleNo\": 4,\n" +
			"            \"dayNo\": 1,\n" +
			"            \"listAttraction\": [125411,125418,125431]\n" +
			"        },\n" +
			"        {\n" +
			"            \"articleNo\": 4,\n" +
			"            \"dayNo\": 2,\n" +
			"            \"listAttraction\": [125465,125478]\n" +
			"        }\n" +
			"    ]\n" +
			"}")
	@GetMapping("daysPlan")
*/

	public void setDayPlans(
			@RequestBody @ApiParam(value = "일차별 여행지 데이터") ListTripPlanDto list) {

		System.out.println(list);
		boardService.setDayPlans(list);

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



	@ApiOperation(value = "여행 계획 상세보기 + 댓글 + 여행지 리스트 + 일별 여행 계획", notes = "글번호에 해당하는 게시글과 댓글의 정보를 반환한다. 전체 여행지의 정보와, 일별 여행지 이동 계획을 받아옵니다..", response = BoardDto.class)
	@GetMapping("/{articleno}")
	public Map<String, Object> getArticle(
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		logger.info("getArticle - 호출 : " + articleno);
		System.out.println(articleno);

		BoardDto dto = boardService.getArticle(articleno);
		List<AttractionInfoDto> list = new MainController().getbyContentIdList(dto.getAttractionList());
		List<CommentDto> commentList = boardService.listComment(dto);
		List<TripPlanDto> tripPlanList = boardService.getDayPlans(articleno);

		Map<String, Object> map = new HashMap<>();
		map.put("board", dto);
		map.put("attractionInfo", list);
		map.put("comment", commentList);
		map.put("daysPlan", tripPlanList);


		return map;
	}


	@ApiOperation(value = "AI의 추천을 받습니다.", notes = "여행지 리스트를 받고, 동선에서 가까운 여행지중 여행 테마에 맞는 여행지들을 ai기반으로 추천 받는다." +
			"" +
			"예시)\n" +
			"post\n" +
			"http://localhost/vue/board/ai" +
			"{\n" +
			"    \"attractions\": [2848347,2848335,2848039]\n" +
			"}", response = BoardDto.class)
	@PostMapping("/ai")
	public Object getAIRecommendation(
			@ApiParam(value = "여행지의 리스트", required = true) @RequestBody Map<String, List<String>> requestBody)
			throws Exception {

		List<String> attractions = requestBody.get("attractions");
		String strList = attractions.stream().collect(Collectors.joining(","));

		List<AttractionInfoDto> list = new MainController().getbyContentIdList(strList);



		return boardService.getAiRecommendation(list);
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

	@ApiOperation(value = "여행계획 수정", notes = "수정할 게시글 정보(articleNo, subject, content, listAttraction, isPublic, category) " +
			"예시)" +
			"" +
			"put\n" +
			"http://localhost/vue/board\n" +
			"{\n" +
			"\t\"articleNo\": 32,\n" +
			"    \"subject\": \"수정된 글 제목\",\n" +
			"    \"content\": \"수정된 글 내용\",\n" +
			"    \"listAttraction\": [\"125411\", \"125418\", \"125431\"],\n" +
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


	@ApiOperation(value = "댓글 수정", notes = "댓글 수정" +
			"" +
			"예시)" +
			"" +
			"post" +
			"http://localhost/vue/board/comments" +
			"" +
			"{\n" +
			"    \"commentNo\": \"13\",\n" +
			"    \"content\": \"수정된 댓글입니다.\"\n" +
			"}")
	@PutMapping("/comments")
	public ResponseEntity<String> modifyComment (
			@RequestBody @ApiParam(value = "수정할 댓글정보.", required = true) CommentDto commentDto) throws Exception {
		logger.info("modifyComment - 호출 {}", commentDto);

		boardService.modifyComment(commentDto);
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