package com.wuzx.springboot;


import java.util.HashMap;
import java.util.Map;



/**
 * BaseCommonResult生成器
 */
public class Result {

    private Result(){

    }


    /**
     * 返回成功
     * @return
     */
    public static CommonResult success(){
        CommonResult result = new CommonResult();
        result.setCode(ApiCode.SUCCESS);
        result.setMsg(ApiCode.getZhMsg(ApiCode.SUCCESS));
        return result;
    }

    /**
     * 返回成功
     * @return
     */
    public static <T> CommonResult<T> success(T data){
        CommonResult<T> result = new CommonResult();
        result.setCode(ApiCode.SUCCESS);
        result.setMsg(ApiCode.getZhMsg(ApiCode.SUCCESS));
        result.setData(data);
        return result;
    }

    /**
     * 返回成功
     * @return
     */
    public static CommonResult<Map<String,Object>> success(String key, Object value){
        CommonResult<Map<String,Object>> result = new CommonResult();
        result.setCode(ApiCode.SUCCESS);
        result.setMsg(ApiCode.getZhMsg(ApiCode.SUCCESS));
        Map<String,Object> resMap = new HashMap();
        resMap.put(key,value);
        result.setData(resMap);
        return result;
    }

    /**
     * 返回成功
     * @return
     */
    public static <T> CommonResult<T> success(T data, String msg){
        CommonResult<T> result = new CommonResult();
        result.setCode(ApiCode.SUCCESS);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


    /**
     * 返回失败
     * @return
     */
    public static CommonResult fail(){
        CommonResult result = new CommonResult();
        result.setCode(ApiCode.FAIL);
        result.setMsg(ApiCode.getZhMsg(ApiCode.FAIL));
        return result;
    }
    
    /**
     * 
     *  Description: 根据指定错误码生成响应报文对象
     *  @author chenyuanxian 2018年11月20日 下午2:52:34
     *  @param code 错误码
     *  @return 返回失败
     */
    public static CommonResult fail(Integer code){
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMsg(ApiCode.getZhMsg(code));
        return result;
    }

    /**
     * 返回失败
     * @return
     */
    public static CommonResult fail(String msg){
        CommonResult result = new CommonResult();
        result.setCode(ApiCode.FAIL);
        result.setMsg(msg);
        return result;
    }

    /**
     * 返回失败
     * @return
     */
    public static CommonResult fail(Integer code, String msg){
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 返回失败
     * @return
     */
    public static <T> CommonResult<T> fail(Integer code, String msg, T data){
        CommonResult<T> result = new CommonResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

}
