package com.martinwj.service.impl;

import com.martinwj.dao.field.IFieldDAO;
import com.martinwj.dao.fieldProfile.IFieldProfileDAO;
import com.martinwj.entity.Field;
import com.martinwj.entity.FieldProfile;
import com.martinwj.service.FieldProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: FieldProfileServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-25
 */
@Service
public class FieldProfileServiceImpl implements FieldProfileService {

    @Autowired
    private IFieldProfileDAO iFieldProfileDAO;
    @Autowired
    private IFieldDAO iFieldDAO;

    /**
     * 根据字段主键查询字段详情列表
     * @param fieldId 字段主键
     */
    public List<FieldProfile> listByFieldId(String fieldId) {
        return iFieldProfileDAO.listByFieldId(fieldId);
    }

    /**
     * 保存字段详细内容
     * @param fieldId 字段主键
     * @param fieldProfileList
     */
    public void save(String fieldId, List<FieldProfile> fieldProfileList) {
        // 1.0 保存字段内容
        for (FieldProfile fieldProfile : fieldProfileList) {
            if (StringUtils.isEmpty(fieldProfile.getId())) {
                // 插入
                iFieldProfileDAO.insert(fieldProfile);
            } else {
                // 更新
                iFieldProfileDAO.update(fieldProfile);
            }
        }

        // 2.0 重新生成字段模板
        fieldTemplate(fieldId);
    }

    /**
     * 删除字段内容
     * @param fieldId 字段主键
     * @param idArr 主键数组
     */
    public void delete(String fieldId, String[] idArr) {
        // 1.0 批量删除字段内容
        iFieldProfileDAO.delete(idArr);

        // 2.0 重新生成字段模板
        fieldTemplate(fieldId);
    }

    /**
     * 生成字段模板
     * @param fieldId 字段主键
     */
    public void fieldTemplate(String fieldId) {
        // 1.0 根据主键查询字段信息
        Field field = iFieldDAO.selectById(fieldId);

        // 2.0 生成模板代码
        // 2.1 查询字段详细内容
        List<FieldProfile> list = iFieldProfileDAO.listByFieldId(fieldId);
        // 编辑页面选项等分数量
        String equal = "8";
        // [\u4e00-\u9fa5]是中文的Unicode编码范围，用正则表达式的方法，若字符串中有字符满足中文的正则表达式，则判定为中文，将其替换为两个字符，故长度差就为中文的个数
        String regEx = "[\\u4e00-\\u9fa5]";
        for (FieldProfile fieldProfile : list) {
            String fieldName = fieldProfile.getName();
            String zhongwen = fieldName.replaceAll(regEx, "aa");
            // 当有4个以上的中文 或者 字符串长度大于4时，设置为6等分
            if ((zhongwen.length()-fieldName.length())>3 || fieldName.length()>4) {
                equal = "6";
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append("	<div class='unit'>");
        sb.append("		<div class='left'>");
        sb.append("			<p class='subtitle'>"+field.getName()+"</p>");
        sb.append("		</div>");
        sb.append("		<div class='right'>");
        sb.append("			<ul class='equal-"+equal+"'>");
        // 2.2 判断输入类型
        int len = list.size();
        if ("radio".equals(field.getInputType())) {
            // 单选框
            for (int i=0; i<len; i++) {
                if (i==0) {
                    sb.append("		<li><input type='radio' class='fill' name='"+field.getVarName()+"' value='"+list.get(i).getId()+"' checked />"+list.get(i).getName()+"</li>");
                } else {
                    sb.append("		<li><input type='radio' class='fill' name='"+field.getVarName()+"' value='"+list.get(i).getId()+"' />"+list.get(i).getName()+"</li>");
                }
            }
        } else if ("checkbox".equals(field.getInputType())) {
            // 复选框
            for (int i=0; i<len; i++) {
                sb.append("		<li><input type='checkbox' class='fill' name='"+field.getVarName()+"' value='"+list.get(i).getId()+"' />"+list.get(i).getName()+"</li>");
            }
        }
        sb.append("			</ul>");
        sb.append("		</div>");
        // 清浮动
        sb.append("		<span class='clearfix'></span>");
        sb.append("	</div>");

        // 3.0 更新字段模板
        field.setContent(sb.toString());
        iFieldDAO.update(field);
    }


}
