package com.martinwj.service.impl;

import com.martinwj.dao.nav.INavDAO;
import com.martinwj.entity.Nav;
import com.martinwj.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: NavServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Service
public class NavServiceImpl implements NavService {

    @Autowired
    private INavDAO iNavDAO;

    /**
     * 查询导航列表
     */
    public List<Nav> list() {
        return iNavDAO.list();
    }

    /**
     * 可用导航列表
     * @return
     */
    public List<Nav> listIsUse() {
        return iNavDAO.listIsUse();
    }

    /**
     * 保存导航
     * @param navList
     */
    public void save(List<Nav> navList) {
        for (Nav nav : navList) {
            if (StringUtils.isEmpty(nav.getId())) {
                // 插入
                nav.setType("user");
                iNavDAO.insert(nav);
            } else {
                // 更新
                iNavDAO.update(nav);
            }
        }
    }

    /**
     * 删除导航
     * @param idArr 导航主键数组
     */
    public void delete(String[] idArr) {
        iNavDAO.delete(idArr);
    }
}
