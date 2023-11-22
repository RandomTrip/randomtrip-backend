package com.ssafy.vue.board;

import com.ssafy.vue.board.model.BoardDto;
import com.ssafy.vue.board.model.ListTripPlanDto;

public class BoardBigData {
    private BoardDto postData;
    private ListTripPlanDto daysPlan;

    public BoardDto getPostData() {
        return postData;
    }

    public void setPostData(BoardDto postData) {
        this.postData = postData;
    }

    public ListTripPlanDto getDaysPlan() {
        return daysPlan;
    }

    public void setDaysPlan(ListTripPlanDto daysPlan) {
        this.daysPlan = daysPlan;
    }

    public BoardBigData() {
    }
}
