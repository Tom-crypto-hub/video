package com.martinwj.dao.api;

import com.martinwj.entity.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: IApiDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-22
 */
@Repository
public interface IApiDAO {
    /**
     * 查询指定类型的接口列表
     * @param type 接口类型
     * @return
     */
    List<Api> listByType(String type);

    /**
     * 根据主键，获取接口设置条件
     * @param id 主键
     * @return
     */
    Map<String, Object> selectById(String id);

    /**
     * 向接口表中添加字段
     * @param alterSql
     */
    int alter(@Param("alterSql") String alterSql);
}
