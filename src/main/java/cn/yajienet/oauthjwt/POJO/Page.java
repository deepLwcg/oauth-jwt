package cn.yajienet.oauthjwt.POJO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 3:55
 */
@Data
@Builder
public class Page {
    private int page;			// 当前页数
    private long pageSize;      //页数大小
    private int pages;          // 总页数
    private long total;			// 总记录数
    private List<?> rows;		// 每行显示的内容
}
