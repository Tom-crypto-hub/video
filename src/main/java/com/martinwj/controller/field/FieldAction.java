package com.martinwj.controller.field;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.martinwj.constant.FixedOption.Option;
import com.martinwj.entity.Field;
import com.martinwj.entity.Result;
import com.martinwj.exception.SysException;
import com.martinwj.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 保存字段
     * @param idArr 字段主键数组
     * @param sortArr 字段排序数组
     * @param nameArr 字段名称数组
     * @param varNameArr 变量名称数组
     * @param inputTypeArr 填写类型数组
     * @throws SysException
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(
            @RequestParam(value="idArr") String[] idArr,
            @RequestParam(value="sortArr") String[] sortArr,
            @RequestParam(value="nameArr") String[] nameArr,
            @RequestParam(value="varNameArr") String[] varNameArr,
            @RequestParam(value="inputTypeArr") String[] inputTypeArr) throws SysException {

        List<Field> fieldList = new ArrayList<Field>();

        // 判断是否已有既存数据
        if (idArr.length==0) {
            // 全是新增
            // 遍历sortArr数组
            for (int i=0; i<sortArr.length; i++) {
                Field field = new Field();
                field.setSort(sortArr[i]);
                field.setName(nameArr[i]);
                field.setVarName(varNameArr[i]);
                field.setInputType(inputTypeArr[i]);
                // 判断该字段是否可编辑
                if ("radio".equals(inputTypeArr[i]) || "checkbox".equals(inputTypeArr[i]) || "select".equals(inputTypeArr[i])) {
                    field.setIsAllowEdit("1");
                }

                fieldList.add(field);
            }
        } else {
            // 遍历idArr数组
            for (int i=0; i<idArr.length; i++) {
                Field field = new Field();
                field.setId(idArr[i]);
                field.setSort(sortArr[i]);
                field.setName(nameArr[i]);
                field.setVarName(varNameArr[i]);
                field.setInputType(inputTypeArr[i]);
                // 判断该字段是否可编辑
                if ("radio".equals(inputTypeArr[i]) || "checkbox".equals(inputTypeArr[i]) || "select".equals(inputTypeArr[i])) {
                    field.setIsAllowEdit("1");
                }
                System.out.println(field);

                fieldList.add(field);
            }
        }

        fieldService.save(fieldList);

        return Result.success();
    }

}
