package com.ssafy.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.SystemColor.text;




@Service
public class AttractionAiService {

    @Value("${gpt.token}")
    String gptToken;

    public static Object removeOuterBrackets(String input) {
        StringBuilder result = new StringBuilder();
        int count = 0;

        for (char c : input.toCharArray()) {
            if (c == '{') {
                if (count > 0) {
                    result.append(c);
                }
                count++;
            } else if (c == '}') {
                count--;
                if (count > 0) {
                    result.append(c);
                }
            } else if (count > 0) {
                result.append(c);
            }
        }

        return result.toString();
    }

    public Object sendRequest() throws IOException {


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/chat/completions");

        // Bearer 토큰 추가

        httpPost.setHeader("Authorization", "Bearer " + gptToken);

        // POST 요청을 위한 데이터 설정 (JSON 형태로 예시)
        StringEntity postData = new StringEntity("{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"stream\" : false,\n" +
                "  \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"system\",\n" +
                "            \"content\": \"너는 여행 관련 컨설턴트야\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"role\": \"assistant\",\n" +
                "          \"content\": \"너는 고객이 짜놓은 여행 코스에서 추가적으로 코스를 추천해주거나, 근처 명소를 추천해줘. 여행 코스를 추천해줄 때는 json 형식으로 추천해줘. 유저가 방문하는 여행지 근처의 명소를 추천해주는데, 코스 제목, 추천 이유, 여행지 사진 url 이렇게 세 가지를 json 으로 만들어줘. 이미지는 웹상에 떠도는 비슷하게 생긴 이미지로 넣어줘. json 으로 파싱하기 쉽도록 다른 텍스트는 포함시키지 말고 모두 json 으로 만들어줘. 'recommendations' 안에 title, reason, photo 값을 갖는 객체 배열을 만들어주고, 전체적인 여행을 분석해서 해당 여행에 대한 총 평인 'evaluation' 도 넣어줘. 해당 evaluation 은 여행의 종류와, 여행하는 지역의 특징이나 이런걸 반영해서 JSON으로 작성해줘.\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\" : \"user\",\n" +
                "            \"content\" : \"고객이 짜놓은 여행 코스: 1. 대천항 2.심연동계곡(성주계곡) 3.숭의사 4.청산수목원 이 4개의 코스를 순차적으로 방문한다.\"\n" +
                "        }\n" +
                "    ]\n" +
                "}", StandardCharsets.UTF_8);
        httpPost.setEntity(postData);
        httpPost.setHeader("Accept-Charset", "UTF-8");
        httpPost.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpPost);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }











        ObjectMapper objectMapper = new ObjectMapper();


        Object parsedObject = objectMapper.readValue(result.toString(), Object.class);

        HashMap<String, Object> hmTmp = (HashMap<String, Object>) parsedObject;
        ArrayList<Object> listTmp = (ArrayList<Object>) hmTmp.get("choices");
        HashMap<String, Object> hmTmp3 = (HashMap<String, Object>) listTmp.get(0);
        HashMap<String, String> hmTmp4 = (HashMap<String, String>) hmTmp3.get("message");
        System.out.println("Response: " + hmTmp4.get("content"));




        String objString = "{" +  removeOuterBrackets(hmTmp4.get("content")) + "}";

        System.out.println("잘라낸 결과: " + objString);
        objectMapper = new ObjectMapper();

        try {
            objectMapper = new ObjectMapper();
            parsedObject = objectMapper.readValue(objString, Object.class);

            // 객체로 변환된 데이터 출력 (디버깅용)
            System.out.println("Parsed Object: " + parsedObject.toString());
            return parsedObject;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }
}
