package com.martinwj.dao.activate;

import com.martinwj.entity.Activate;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: IActivateDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface IActivateDAO {
    /**
     * 根据用户id和验证类型，判断认证是否已存在
     * @param userId 用户id
     * @param type 验证类型
     * @return
     */
    Activate selectByUserIdAndType(@Param("userId") String userId, @Param("type") String type);

    /**
     * 插入
     * @param activate
     * @return
     */
    int insert(Activate activate);

    /**
     * 新增
     * @param activate
     * @return
     */
    int update(Activate activate);

    /**
     * 删除验证记录
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 获取验证记录
     * @param email
     * @param code
     * @param type
     * @return
     */
    Activate selectByEmailAndCodeAndType(
            @Param("email") String email,
            @Param("code") String code,
            @Param("type") String type
    );
}
