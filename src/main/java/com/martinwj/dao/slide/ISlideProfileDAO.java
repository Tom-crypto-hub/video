package com.martinwj.dao.slide;

import com.martinwj.entity.Slide;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ISlideProfileDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Repository
public interface ISlideProfileDAO {

    /**
     * 接口专用
     * 根据幻灯片主键，查询所有幻灯片数据
     * @param slideId 幻灯片表的主键
     * @return
     */
    List<Map<String, Object>> mapListByApiId(String slideId);
}
