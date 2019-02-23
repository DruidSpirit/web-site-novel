package com.druidelf.novelbackstagemanagement.response;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("JavaDoc")
@Data
@ApiModel(value = "分页返回体")
@NoArgsConstructor
public class PageBody<T> implements Serializable {

    private static final long serialVersionUID = 2056823229578504942L;

    @ApiModelProperty( "分页总条数" )
    private long total;

    @ApiModelProperty( "响应结果" )
    private List<T> list;

    /**
     * 将list集合包装成分页数据
     * @param list
     * @param <T>
     * @return
     */
    public static <T>PageBody dealWithList( List<T> list ){
        PageInfo<T> page = new PageInfo(list);

        PageBody<T> pageBody = new PageBody<>();
        pageBody.setTotal( page.getTotal() );

        pageBody.setList( page.getList());
        return pageBody;
    }

    /**
     * 将list集合包装成分页数据,同时使用替代的list来作为其返回数据
     * @param list
     * @param replaceList
     * @param <T>
     * @return
     */
    public static <T>PageBody dealWithList( List<?> list, List<T> replaceList ){
        PageInfo page = new PageInfo(list);

        PageBody<T> pageBody = new PageBody<>();
        pageBody.setTotal( page.getTotal() );
        pageBody.setList( replaceList );
        return pageBody;
    }
}
