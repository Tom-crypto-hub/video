package com.martinwj.dao.tag;

import org.springframework.stereotype.Repository;

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
}
