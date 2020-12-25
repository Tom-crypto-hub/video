package com.martinwj.controller.fieldProfile;

import com.martinwj.entity.FieldProfile;
import com.martinwj.entity.Result;
import com.martinwj.service.FieldProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FieldProfileAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-25
 */
@Controller
@RequestMapping("field_profile_info")
public class FieldProfileAction {
    @Autowired
    private FieldProfileService fieldProfileService;

    /**
     * 字段详情编辑
     * @param fieldId 字段主键
     * @param fieldName 字段名称
     */
    @RequestMapping("list.action")
    public String list(ModelMap map,
                       @RequestParam(value="fieldId") String fieldId,
                       @RequestParam(value="fieldName") String fieldName) {

        // 字段对应的详情内容
        List<FieldProfile> list = fieldProfileService.listByFieldId(fieldId);
        map.put("list", list);

        map.put("fieldId", fieldId);
        map.put("fieldName", fieldName);

        return "admin/field_profile_info/list";
    }

    /**
     * 保存字段
     * @param fieldId 字段主键
     * @param idArr 字段主键数组
     * @param sortArr 字段排序数组
     * @param nameArr 字段名称数组
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(
            @RequestParam(value="fieldId") String fieldId,
            @RequestParam(value="idArr") String[] idArr,
            @RequestParam(value="sortArr") String[] sortArr,
            @RequestParam(value="nameArr") String[] nameArr) {

        List<FieldProfile> fieldProfileList = new ArrayList<FieldProfile>();

        // 判断是否已有既存数据
        if (idArr.length==0) {
            // 全是新增
            // 遍历sortArr数组
            for (int i=0; i<sortArr.length; i++) {
                FieldProfile fieldProfile = new FieldProfile();
                fieldProfile.setSort(sortArr[i]);
                fieldProfile.setName(nameArr[i]);
                fieldProfile.setFieldId(fieldId);

                fieldProfileList.add(fieldProfile);
            }
        } else {
            // 遍历idArr数组
            for (int i=0; i<idArr.length; i++) {
                FieldProfile fieldProfile = new FieldProfile();
                fieldProfile.setId(idArr[i]);
                fieldProfile.setSort(sortArr[i]);
                fieldProfile.setName(nameArr[i]);
                fieldProfile.setFieldId(fieldId);

                fieldProfileList.add(fieldProfile);
            }
        }

        fieldProfileService.save(fieldId, fieldProfileList);

        return Result.success();
    }

    /**
     * 删除
     * @param fieldId 字段主键
     * @param idArr 字段主键数组
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public Result delete(
            @RequestParam(value="fieldId") String fieldId,
            @RequestParam(value="idArr") String[] idArr) {

        fieldProfileService.delete(fieldId, idArr);

        return Result.success();
    }

}
