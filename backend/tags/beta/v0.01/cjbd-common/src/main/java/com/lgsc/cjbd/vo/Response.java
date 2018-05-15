package com.lgsc.cjbd.vo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 响应对象
 *
 * @author
 * @since 1.0.0
 */
public class Response {

    private static final String OK = "ok";
    private static final String ERROR = "error";

    private Meta meta;
    private Object data;

    public Response success() {
        this.meta = new Meta(true, OK);
        return this;
    }

    public Response success(Object data) {
        this.meta = new Meta(true, OK);
        this.data = data;
        return this;
    }
    
    public Response success(String message, Object data) {
        this.meta = new Meta(true, message);
        this.data = data;
        return this;
    }

    public Response failure() {
        this.meta = new Meta(false, ERROR);
        return this;
    }

    public Response failure(String message) {
        this.meta = new Meta(false, message);
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public Object getData() {
        return data;
    }


    public class Meta {

        private boolean success;
        private String message;

        public Meta() {

        }

        public Meta(boolean success) {
            this.success = success;
        }

        public Meta(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }



    private static Response createInstance(){
        return new Response();
    }
    public static Response forSuccess(String message, Object data) {
        Response response = createInstance();
        return  response.success(message,data);
    }
    public static Response forSuccess() {
        Response response = createInstance();
        return response.success();
    }

    public static Response forSuccess(Object data) {
        Response response = createInstance();
        return  response.success(data);
    }
    public static Response forFail(String message) {
        Response response = createInstance();
        return response.failure(message);
    }
    public static Response forFail() {
        Response response = createInstance();
        return response.failure();
    }

    /**
     * @desc   返回结果状态
     * @date   2017/3/13 18:18
     * @author zzq
     *
    **/
    
    
    public static Response forResult(int status,String msg,Object data) {
        Response response = createInstance();
        if(status == HttpStatus.OK.value()){
            response = response.success(data);
        }else{
            response =  response.failure(msg);
        }
        return response;
    }
  
  /**
   * @desc
   * @date   2017/3/13 18:35
   * @author zzq
   *
  **/
  
  

    public static Response forResult(ResponseEntity<Response> responseEntity) {

        Response response = createInstance();
        if(responseEntity.getStatusCodeValue() == HttpStatus.OK.value()){
           return  response.success(responseEntity.getBody().getMeta().getMessage(),responseEntity.getBody().getData());
        }
        return response.failure(responseEntity.getBody().getMeta().getMessage());


    }

   public static Response forResult(Response response,JSONTransform jsonTransform) {

        if(response.getMeta().isSuccess()){
            response =   response.success( jsonTransform.tranform(response.getData()));
        }else{
            response =  response.failure(response.getMeta().getMessage());
        }

        return response;
    }

}
