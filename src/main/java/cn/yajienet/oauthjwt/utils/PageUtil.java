package cn.yajienet.oauthjwt.utils;

import cn.yajienet.oauthjwt.POJO.Page;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 3:59
 */
public class PageUtil {

    public static <T> Page getPages(List<T> list, PageInfo<T> pageInfo){
        return Page.builder()
                .rows(list == null?new ArrayList<>():list)
                .page(pageInfo.getPageNum())
                .pages(pageInfo.getPages())
                .total(pageInfo.getTotal())
                .pageSize(pageInfo.getPageSize())
                .build();
    }

}
