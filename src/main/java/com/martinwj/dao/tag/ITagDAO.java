package com.martinwj.dao.tag;

import com.martinwj.entity.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.servlet.jsp.tagext.TagInfo;
import java.util.List;

/**
 * @ClassName: ITagDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
@Repository
public interface ITagDAO {
    /**
     * 根据主键数组，查询名称集合
     * @param idArr 主键数组
     * @return
     */
    List<String> listNameByIdArr(String[] idArr);

    /**
     * 根据标签中文名称，查询标签是否已存在
     * @param name 标签中文名称
     * @return
     */
    int countByName(String name);

    /**
     * 插入新标签
     * @param tag
     */
    int insert(Tag tag);

    /**
     * 根据标签名称数组，查询对应的id集合，并按从小到大排序
     * @param nameArr 标签名称数组
     * @return
     */
    List<String> listIdByNameArr(@Param("nameArr") String[] nameArr);

}
