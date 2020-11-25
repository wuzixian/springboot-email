package com.wuzx.springboot;
import java.util.HashMap;
import java.util.Map;


public class ApiCode {

	public static final Map<Integer, String> zhMsgMap = new HashMap<Integer, String>(10);

	/** 请求成功 **/
	public static final Integer SUCCESS = 0;
	/** 失败 **/
	public static final Integer FAIL = -1;

	
	/**
	 *  Description: 获取状态码对应提示信息<br>
	 *  @author wenwujun  DateTime 2018年10月25日 下午12:38:15
	 *  @param responseCode
	 *  @return
	 */
	public static String getZhMsg(Integer responseCode) {
		return zhMsgMap.get(responseCode);
	}

	
	static {
		zhMsgMap.put(SUCCESS, "成功");
		zhMsgMap.put(FAIL, "系统繁忙，请稍后再试");
	}
	
}
