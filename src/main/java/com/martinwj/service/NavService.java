package com.martinwj.service;

import com.martinwj.dao.nav.INavDAO;
import com.martinwj.entity.Nav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: NavService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface NavService {
    /**
     * 查询导航列表
     */
    public List<Nav> list();

    /**
     * 可用导航列表
     * @return
     */
    public List<Nav> listIsUse();

    /**
     * 保存导航
     * @param navList
     */
    public void save(List<Nav> navList);

    /**
     * 删除导航
     * @param idArr 导航主键数组
     */
    public void delete(String[] idArr);
}
