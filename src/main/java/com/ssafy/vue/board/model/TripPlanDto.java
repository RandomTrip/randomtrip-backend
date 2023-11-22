package com.ssafy.vue.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel(value = "TripPlanDto : ", description = "n일차별로 데이터를 저장한다.")
public class TripPlanDto {

    @ApiModelProperty(value = "연결된 글(여행계획) 번호")
    private int articleNo; // pk, not null
    @ApiModelProperty(value = "n일차")
    private int dayNo; // int
    @ApiModelProperty(value = "여행지 목록 (서버 저장용)")
    private String attractionList; // varchar(45)
    @ApiModelProperty(value = "여행지 목록 (rest api 통신용)")
    private List<String> listAttraction;

    public int getArticleNo() {
        return articleNo;
    }

    public TripPlanDto() {

    }

    public void setArticleNo(int articleNo) {
        this.articleNo = articleNo;
    }

    public int getDayNo() {
        return dayNo;
    }

    public void setDayNo(int dayNo) {
        this.dayNo = dayNo;
    }

    public TripPlanDto(int articleNo, int dayNo, String attractionList, List<String> listAttraction) {
        this.articleNo = articleNo;
        this.dayNo = dayNo;
        this.attractionList = attractionList;
        this.listAttraction = listAttraction;

    }

    public String getAttractionList() {
        return attractionList;
    }

    public void setAttractionList(String attractionList) {
        this.attractionList = attractionList;
        this.listAttraction = Arrays.asList(attractionList.split(",")); // rest api 전송용 데이터 세팅
    }

    public List<String> getListAttraction() {
        return listAttraction;
    }

    public void setListAttraction(List<String> listAttraction) {

        this.listAttraction = listAttraction;
        this.attractionList = listAttraction.stream().collect(Collectors.joining(",")); // 서버 저장용 데이터 세팅

    }


    @Override
    public String toString() {
        return "TripPlanDto{" +
                "articleNo=" + articleNo +
                ", dayNo=" + dayNo +
                ", attractionList='" + attractionList + '\'' +
                ", listAttraction=" + listAttraction +
                '}';
    }
}
