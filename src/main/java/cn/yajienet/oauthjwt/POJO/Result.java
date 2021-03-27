package cn.yajienet.oauthjwt.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 4:05
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private int code;
    private String error;
    private String message;
    private Object data;
    private String path;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date timestamp;

    public Result() {
    }

    public Result(int code, String error, String message, Object data, String path, Date timestamp) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.data = data;
        this.path = path;
        this.timestamp = timestamp;
    }

    public Result error(){
        return new Result(400,"unknown error","发生未知错误",null,null,new Date());
    }
    public Result success(){
        return new Result(200,null,"访问成功",null,null,new Date());
    }

}
