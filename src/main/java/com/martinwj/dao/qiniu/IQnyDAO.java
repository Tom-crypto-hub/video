package com.martinwj.dao.qiniu;

import com.martinwj.entity.Qny;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: Qny
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-21
 */
@Repository
public interface IQnyDAO {
    /**
     * 查询指定类型的配置
     * @param type 类型
     * @return
     */
    Qny selectByType(String type);

    /**
     * 根据类型更新
     * @param qny
     * @return
     */
    int update(Qny qny);

    /**
     * 获取所有的七牛云配置信息
     * @return
     */
    List<Qny> selectAll();
}
