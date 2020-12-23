package com.martinwj.service.impl;

import com.martinwj.dao.slide.ISlideProfileDAO;
import com.martinwj.service.SlideProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: SlideProfileServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Service
public class SlideProfileServiceImpl implements SlideProfileService {

    @Autowired
    private ISlideProfileDAO iSlideProfileDAO;

    /**
     * 接口专用
     * 根据幻灯片主键，查询所有幻灯片数据
     * @param slideId 幻灯片表的主键
     * @return
     */
    public List<Map<String, Object>> mapListByApiId(String slideId) {
        return iSlideProfileDAO.mapListByApiId(slideId);
    }

}
