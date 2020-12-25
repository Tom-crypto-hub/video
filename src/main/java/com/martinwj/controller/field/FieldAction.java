package com.martinwj.controller.field;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.martinwj.constant.FixedOption.Option;
import com.martinwj.entity.Field;
import com.martinwj.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: FieldAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-25
 */
@Controller
@RequestMapping("field_info")
public class FieldAction {

    @Autowired
    private FieldService fieldService;


    /**
     * 查询所有用户自定义字段
     */
    @RequestMapping("list.action")
    public String list(ModelMap map,
                       @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                       @RequestParam(value="pageSize", defaultValue="10") int pageSize) {

        // pageHelper分页插
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<Field> list = fieldService.list();
        PageInfo<Field> pageInfo = new PageInfo<Field>(list);
        map.put("pageInfo", pageInfo);

        // 字段类型选项
        List<Map<String, String>> optList = new ArrayList<Map<String, String>>();
        Map<String, String> optMap = null;
        for (Option opt : Option.values()) {
            optMap = new HashMap<String, String>();
            optMap.put("value", opt.getValue());
            optMap.put("name", opt.getName());
            optList.add(optMap);
        }
        map.put("optList", optList);

        return "admin/field_info/list";
    }

}
