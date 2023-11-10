package com.ssafy.vue.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // 스프링 실행시 설정파일
@EnableSwagger2 // Swagger2를 사용
@SuppressWarnings("unchecked") // warning 제거
public class SwaggerConfiguration {

//	Swagger-UI 2.x 확인
//	http://localhost[:port]/{your-app-root}/swagger-ui.html
//	http://localhost/vue/swagger-ui.html
	
//	Swagger-UI 3.x 확인
//	http://localhost[:port]/{your-app-root}/swagger-ui/index.html

	private String version = "V1";
	private String title = "SSAFY Vuejs API " + version;

	private ApiInfo apiInfo() {
		String descript = "<img src=\"http://localhost/vue/static/assets/img/ssafy_logo.png\"><br>";
		descript += "<h2>싸피 Vuejs 개발을 위한 API 문서입니다.</h2>";
		descript += "회원정보, 게시판, 아파트 정보를 테스트 할 수 있습니다.";
		return new ApiInfoBuilder().title(title).description(descript)
//				.termsOfServiceUrl("https://edu.ssafy.com")
				.contact(new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com")).license("SSAFY License")
				.licenseUrl("ssafy@ssafy.com").version("1.0").build();
	}

	// swagger ui 설정.
	@Bean
	public UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder().displayRequestDuration(true).validatorUrl("").build();
	}
	
	@Bean
	public Docket allApi() {
		return getDocket("all", Predicates.or(PathSelectors.regex("/*.*")));
	}

	@Bean
	public Docket userApi() {
		return getDocket("users", Predicates.or(PathSelectors.regex("/user.*")));
//		return getDocket("회원", PathSelectors.ant("/user/*"));
	}

	@Bean
	public Docket searchApi() {
		return getDocket("boards", Predicates.or(PathSelectors.regex("/board.*")));
	}

	@Bean
	public Docket commonApi() {
		return getDocket("apts", Predicates.or(PathSelectors.regex("/map.*")));

	}

	public Docket getDocket(String groupName, Predicate<String> predicate) {
		return new Docket(DocumentationType.SWAGGER_2).groupName(groupName).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.vue")).paths(predicate)
				.apis(RequestHandlerSelectors.any()).build()
				.globalResponseMessage(RequestMethod.GET, getCustomizedResponseMessages())
				.globalResponseMessage(RequestMethod.POST, getCustomizedResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, getCustomizedResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, getCustomizedResponseMessages())
				.globalResponseMessage(RequestMethod.PATCH, getCustomizedResponseMessages());
	}

	private List<ResponseMessage> getCustomizedResponseMessages() {
		List<ResponseMessage> responseMessages = new ArrayList<>();
		responseMessages.add(new ResponseMessageBuilder().code(200).message("성공").build());
//		responseMessages.add(new ResponseMessageBuilder().code(204).message("데이터 미존재").build());
		responseMessages.add(new ResponseMessageBuilder().code(400).message("입력값 오류").build());
		responseMessages.add(new ResponseMessageBuilder().code(401).message("로그인필요!!!").build());
		responseMessages.add(new ResponseMessageBuilder().code(403).message("권한없음").build());
//		responseMessages.add(new ResponseMessageBuilder().code(412).message("처리중 오류").build());
		responseMessages.add(new ResponseMessageBuilder().code(500).message("서버에러").build());
		return responseMessages;
	}

}
