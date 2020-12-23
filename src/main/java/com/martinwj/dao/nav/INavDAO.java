package com.martinwj.dao.nav;

import com.martinwj.entity.Nav;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: INavDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Repository
public interface INavDAO {
    /**
     * 查询导航列表
     */
    List<Nav> list();

    /**
     * 插入新的导航
     * @param nav
     * @return
     */
    int insert(Nav nav);

    /**
     * 更新导航
     * @param nav
     * @return
     */
    int update(Nav navI);

    /**
     * 批量删除导航
     * @param idArr 主键数组
     * @return
     */
    int delete(@Param("idArr") String[] idArr);

    /**
     * 根据频道主键删除导航
     * @param channelId
     */
    int deleteByChannelId(String channelId);

    /**
     * 可用导航列表
     * @return
     */
    List<Nav> listIsUse();

    /**
     * 频道编辑时，同步更新导航
     * @param nav
     */
    int updateByChannel(Nav nav);
}
