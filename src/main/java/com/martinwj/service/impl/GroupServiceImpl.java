package com.martinwj.service.impl;/*
 * @Author oscar
 * @Date 2020/12/25 9:58
 * @Version 1.0
 */

import com.martinwj.dao.group.IGroupDAO;
import com.martinwj.entity.Group;
import com.martinwj.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    public IGroupDAO iGroupDAO;

    @Override
    public List<Group> list() {
        return iGroupDAO.list();
    }

    @Override
    public int insert(Group group) {
        group.setType("user");
        return iGroupDAO.insert(group);
    }

    @Override
    public int update(Group group) {
        return iGroupDAO.update(group);
    }

    @Override
    public int deleteById(String[] idArr) {
        return iGroupDAO.deleteById(idArr);
    }

    @Override
    public int save(Group group) {
        if(StringUtils.isEmpty(group.getId())){
            return insert(group);
        } else {
            return update(group);
        }
    }
}
