package com.martinwj.dao.typefield;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ITypeFieldDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-25
 */
@Repository
public interface ITypeFieldDAO {

    /**
     * 检索指定字段是否必填
     * @param typeId 分类信息主键
     * @param varName 字段变量名
     * @return
     */
    String selectIsRequired(@Param("typeId") String typeId, @Param("varName") String varName);

}
