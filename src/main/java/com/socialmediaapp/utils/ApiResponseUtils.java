package com.socialmediaapp.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmediaapp.apiresponse.ApiDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class ApiResponseUtils {

    public static  <T> ResponseEntity<ApiDataResponse<T>> response(HttpStatus status, T data, String message ){
        return ApiResponseUtils.getResponse(status,data,message);
    }

    public static  <T> ResponseEntity<ApiDataResponse<T>> response(HttpStatus status, T data){
        String message=null;
        if(status.equals(HttpStatus.OK)){
            message="Success";
        }
        return ApiResponseUtils.getResponse(status,data,message);
    }

    private static  <T> ResponseEntity<ApiDataResponse<T>> getResponse(HttpStatus status,@Nullable T data, @Nullable String message ){
        ApiDataResponse<T> ar = new ApiDataResponse<>(HttpStatus.OK);
        ar.setData(data);
        ar.setMessage(message);
        return new ResponseEntity<>(ar,status);
    }

    public static  <T> ResponseEntity<ApiDataResponse<T>> response(HttpStatus status, String message ){
        return ApiResponseUtils.getResponse(status,null,message);
    }

    public static  ResponseEntity<ApiDataResponse> errorResponse(HttpStatus status,@Nullable String errMsg,@Nullable String debugMsg,@Nullable String customErrCd ){
        return ApiResponseUtils.getErrResponse(status,errMsg,debugMsg,customErrCd);
    }

    private static ResponseEntity<ApiDataResponse> getErrResponse(HttpStatus status,@Nullable String errMsg,@Nullable String debugMsg,@Nullable String customErrCd  ){
        ApiDataResponse ar = new ApiDataResponse<>(status);
        ar.setMessage(errMsg);
        ar.setErrorCode(customErrCd);
        return new ResponseEntity<>(ar,status);
    }

    public static ResponseEntity<ApiDataResponse> errorResponse(HttpStatus status,@Nullable String errMsg,@Nullable String debugMsg ){
        return ApiResponseUtils.getErrResponse(status,errMsg,debugMsg,null);
    }

    public static ResponseEntity<ApiDataResponse> errorResponse(HttpStatus status,@Nullable String errMsg){
        return ApiResponseUtils.getErrResponse(status,errMsg,null,null);
    }

    public static void writeErrorResponse(String errMsg, HttpServletResponse response, HttpStatus httpStatus) {
        try {
            ApiDataResponse ar = new ApiDataResponse<>(httpStatus);
            ar.setMessage(errMsg);
            response.setStatus(httpStatus.value());
            response.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            PrintWriter out = response.getWriter();
            out.write(mapper.writeValueAsString(ar));
        } catch (Exception e) {
            log.error("Unknown error", e);
        }
    }

}
