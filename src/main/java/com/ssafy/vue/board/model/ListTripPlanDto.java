package com.ssafy.vue.board.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "ListTripPlanDto : ", description = "n일차의 여행 데이터 리스트를 저장한다.")
public class ListTripPlanDto {


    @ApiModelProperty(value = "n일차 여행지 데이터 리스트")
    private ArrayList<TripPlanDto> plans;

    public ListTripPlanDto() {
    }

    @Override
    public String toString() {
        return "ListTripPlanDto{" +
                "plans=" + plans +
                '}';
    }

    public ListTripPlanDto(ArrayList<TripPlanDto> plans) {
        this.plans = plans;
    }

    public ArrayList<TripPlanDto> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<TripPlanDto> plans) {
        this.plans = plans;
    }
}
