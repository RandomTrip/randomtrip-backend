package com.ssafy.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.model.AttractionInfoDto;
import com.ssafy.model.dao.AttractionDaoImpl;
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
import java.util.*;
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



    public Object sendRequest(List<AttractionInfoDto> userAttractions, List<AttractionInfoDto> newAttractions) throws IOException {


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/chat/completions");

        // Bearer 토큰 추가

        httpPost.setHeader("Authorization", "Bearer " + gptToken);


//        String system = "너는 여행 관련 컨설턴트야";
//        String assistant = "너는고객이 짜놓은 여행 코스에서 추가적으로 코스를 추천해주거나, 근처 명소를 추천해줘. 여행 코스를 추천해줄 때는 json 형식으로 추천해줘. 유저가 방문하는 여행지 근처의 명소를 추천해주는데, 코스 제목, 추천 이유, 여행지 사진 url 이렇게 세 가지를 json 으로 만들어줘. 이미지는 웹상에 떠도는 비슷하게 생긴 이미지로 넣어줘. json 으로 파싱하기 쉽도록 다른 텍스트는 포함시키지 말고 모두 json 으로 만들어줘. 'recommendations' 안에 title, reason, photo 값을 갖는 객체 배열을 만들어주고, 전체적인 여행을 분석해서 해당 여행에 대한 총 평인 'evaluation' 도 넣어줘. 해당 evaluation 은 여행의 종류와, 여행하는 지역의 특징이나 이런걸 반영해서 JSON으로 작성해줘.";
//        String user = "고객이 짜놓은 여행 코스: 1. 대천항 2.심연동계곡(성주계곡) 3.숭의사 4.청산수목원 이 4개의 코스를 순차적으로 방문한다.";



        String strUserAttractions = "";
        for(int i = 0; i < userAttractions.size(); i++) {
            strUserAttractions += String.valueOf(i + 1) + ". " + userAttractions.get(i).getTitle();
        }
        String strNewAttractions = "";
        for(int i = 0; i < newAttractions.size(); i++) {
            strNewAttractions += String.valueOf(i + 1) + ". " + newAttractions.get(i).getTitle();
        }

        String system = "너는 여행 컨설턴트야";
        String assistant = "인풋: 고객이 짠 여행 코스와 우리가 추천해준 여행 코스가 주어짐. 해야 할 일: 너는 고객이 짠 여행코스와 우리가 추천해준 여행 코스를 보고, 각 추천해주는 여행지마다 우리가 여행 코스를 추천해주는 강력한 이유를 작성해줘. 아웃풋: JSON 형식. 우리가 짜준 여행 코스 순서대로 {1: '추천 이유', 2: '추천 이유', 3: '추천 이유', 4: '추천 이유', review: '전체적인 여행에 대한 견해'} 이 형식을 꼭 지켜서 내보내줘.";
        String user = "고객이 짜놓은 여행 코스: " + strNewAttractions + "우리가 짜줄 여행 코스 " + strNewAttractions;

        System.out.println("유저가 고른 여행지: " + strUserAttractions);
        System.out.println("우리가 추천해주는 여행지: " + strNewAttractions);


        // POST 요청을 위한 데이터 설정 (JSON 형태로 예시)
        StringEntity postData = new StringEntity("{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"stream\" : false,\n" +
                "  \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"system\",\n" +
                "            \"content\": \"" + system + "\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"role\": \"assistant\",\n" +
                "          \"content\": \"" + assistant + "\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\" : \"user\",\n" +
                "            \"content\" : \"" + user + "\"\n" +
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

        objectMapper = new ObjectMapper();

        try {
            objectMapper = new ObjectMapper();
            parsedObject = objectMapper.readValue(objString, Object.class);

            System.out.println("Parsed Object: " + parsedObject.toString());
            Map<String, Object> map = new HashMap<>();
            map.put("ai", parsedObject);
            map.put("attrData", newAttractions);

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }



    public static int findMode(int[] nums) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        int mode = 0;
        int maxFrequency = 0;

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mode = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return mode;
    }
    public Object getAiRecommendation(List<AttractionInfoDto> list) {

        // 여행지 중간 지점 찾기
        int count = list.size();
        double latitude = 0f;
        double longitude = 0f;
        int content_type_ids[] = new int[count];
        int i = 0;


        for(AttractionInfoDto dto : list) {
            latitude += dto.getLatitude();
            longitude += dto.getLongitude();

            content_type_ids[i] = dto.getContentTypeId();

            i += 1;
        }
        latitude /= count;
        longitude /= count;

        System.out.println(latitude + " , " + longitude);

        // 중간 지점에서 쿼리 날려서 근처 10개 가져오기

        int content_type_id = findMode(content_type_ids);
        List<AttractionInfoDto> listAttractions = AttractionDaoImpl.getAttractionDao().getNearbyAttractions(latitude, longitude, content_type_id, count + 4);

        // 중복 제거


        for(AttractionInfoDto dto : list) {
            listAttractions.removeIf(attraction -> attraction.getContentId() == dto.getContentId());
        }

        List<AttractionInfoDto> sendList = new ArrayList<>();
        for(int j = 0; j < 4; j++) {
            sendList.add(listAttractions.get(j));

        }


        // 지피티한테 넘기기
        try {
            return sendRequest(list, sendList);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
