package com.martinwj.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName: TagService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
public interface TagService {
    /**
     * 根据主键数组，查询名称集合
     * @param idArr 主键数组
     * @return
     */
    public List<String> listNameByIdArr(String[] idArr);

}
