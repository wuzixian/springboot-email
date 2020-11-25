package com.wuzx.springboot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;


@Data
@Slf4j
public class CommonResult<T> implements Serializable {
    private static final long serialVersionUID = 6558724517240247655L;
	
    @ApiModelProperty(value="返回状态码 默认成功:0")
    private Integer code = 0;
    //错误码描述
	@ApiModelProperty(value="返回消息 默认成功:成功")
    private String msg = "成功";
	
	@ApiModelProperty(value="返回数据")
    private T data;

    public CommonResult(){
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"{"+subClassToString()+"}";
    }

    /**
     * 通过内省机制获取子类的属性
     * @return 返回子类 toString()
     */
    public String subClassToString(){
        StringBuilder sb = new StringBuilder();
        try{
//			System.out.println(this instanceof MemberDismissRequest);
            Class<? extends CommonResult> clz = this.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(clz);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors(); // 拿到子类所有属性的数组
            for (PropertyDescriptor pd : pds) { // 迭代拼接
                String name = pd.getName(); //属性名
                if(!"class".equals(name)){
                    Object value = pd.getReadMethod().invoke(this); // 属性值
                    sb.append(name+"=").append("'").append(value).append("',");
                }
            }
        }catch (Exception e){
            log.error("subClassToString出错:{}",e);
        }
        sb.replace(sb.length()-1,sb.length(),"");// 去掉尾部逗号
        return sb.toString();
    }

    public CommonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
