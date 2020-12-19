package com.martinwj.dao.email;

import com.martinwj.entity.Email;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: IEmailDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface IEmailDAO {

    List<Email> list();

    int insert(Email email);

    int update(Email email);

    int delete(@Param("idArr") String[] idArr);

    /**
     * 根据主键查询记录信息
     * @param id 主键
     * @return
     */
    Email selectById(String id);
}
