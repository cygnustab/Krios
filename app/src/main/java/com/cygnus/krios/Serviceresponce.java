package com.cygnus.krios;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitesh on 6/22/2016.
 */
public class Serviceresponce {

    @Expose
    private String Success;


    @Expose
    private List<JsonResult> Response;
//    @Expose
//    private ArrayList<CategoryId> category_id;
//
//    public ArrayList<CategoryId> getCategory_id() {
//        return category_id;
//    }
//
//    public void setCategory_id(ArrayList<CategoryId> category_id) {
//        this.category_id = category_id;
//    }

    private ArrayList<UserData> category_id;

    public ArrayList<UserData> getCategory_id() {
        return category_id;
    }

    public void setCategory_id(ArrayList<UserData> category_id) {
        this.category_id = category_id;
    }

    public List<JsonResult> getResponse() {
        return Response;
    }

    public void setResponse(List<JsonResult> response) {
        Response = response;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }


}
