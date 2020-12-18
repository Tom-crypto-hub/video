package com.martinwj.service.impl;

import com.martinwj.dao.nav.INavDAO;
import com.martinwj.entity.Nav;
import com.martinwj.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 可用导航列表
     * @return
     */
    public List<Nav> listIsUse() {
        return iNavDAO.listIsUse();
    }
}
