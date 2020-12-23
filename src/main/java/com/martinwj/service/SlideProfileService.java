package com.martinwj.service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: SlideProfileService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
public interface SlideProfileService {

    /**
     * 接口专用
     * 根据幻灯片主键，查询所有幻灯片数据
     * @param slideId 幻灯片表的主键
     * @return
     */
    public List<Map<String, Object>> mapListByApiId(String slideId);
}
