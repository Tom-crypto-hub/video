package com.martinwj.dao.fieldProfile;

import com.martinwj.entity.FieldProfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: IFieldProfileDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-25
 */
public interface IFieldProfileDAO {
    /**
     * 根据字段主键查询字段详情列表
     * @param fieldId 字段主键
     * @return
     */
    List<FieldProfile> listByFieldId(String fieldId);

    /**
     * 插入一条新数据
     * @param fieldProfile
     * @return
     */
    int insert(FieldProfile fieldProfile);

    /**
     * 更新一条新数据
     * @param fieldProfile
     * @return
     */
    int update(FieldProfile fieldProfile);

    /**
     * 批量删除内容
     * @param idArr 主键数组
     * @return
     */
    int delete(@Param("idArr") String[] idArr);

}
