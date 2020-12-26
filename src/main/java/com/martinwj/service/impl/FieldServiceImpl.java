package com.martinwj.service.impl;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.api.IApiDAO;
import com.martinwj.dao.field.IFieldDAO;
import com.martinwj.dao.fieldProfile.IFieldProfileDAO;
import com.martinwj.entity.Field;
import com.martinwj.entity.FieldProfile;
import com.martinwj.exception.SysException;
import com.martinwj.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: FieldServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private IFieldDAO iFieldDAO;
    @Autowired
    private IApiDAO iApiDAO;
    @Autowired
    private IFieldProfileDAO iFieldProfileDAO;

    /**
     * 查询字段列表
     */
    public List<Field> list() {
        return iFieldDAO.list();
    }

    /**
     * 查询分类下的字段列表
     *
     * @param typeId 分类信息主键
     * @return
     */
    public List<Field> listByTypeId(String typeId) {
        return iFieldDAO.listByTypeId(typeId);
    }


    /**
     * 保存字段
     *
     * @param fieldList
     * @throws SysException
     */
    public void save(List<Field> fieldList) throws SysException {
        // 保存字段信息
        for (Field field : fieldList) {
            if (StringUtils.isEmpty(field.getId())) {
                // 1.0 校验字段变量名是否重复或者是否已被媒体表内置字段占用
                String varName = field.getVarName();
                int count = iFieldDAO.countByVarName(varName, null);
                if (count > 0) {
                    // 重复
                    throw new SysException(ErrorMsg.ERROR_200001 + "：" + varName);
                }

                // 2.0 插入
                field.setType("user");
                iFieldDAO.insert(field);
            } else {
                // 1.0 校验字段变量名是否重复（排除自己）
                String varName = field.getVarName();
                int count = iFieldDAO.countByVarName(varName, field.getId());
                if (count > 0) {
                    // 重复
                    throw new SysException(ErrorMsg.ERROR_200001 + "：" + varName);
                }

                // 2.0 更新
                iFieldDAO.update(field);
            }
        }

        // 2.0 重新生成字段模板
        List<Field> list = iFieldDAO.list();
        for (Field field : list) {
            if ("text".equals(field.getInputType())) {
                // 文字输入框
                StringBuffer sb = new StringBuffer();
                sb.append("	<div class='unit'>");
                sb.append("		<div class='left'>");
                sb.append("			<p class='subtitle'>" + field.getName() + "</p>");
                sb.append("		</div>");
                sb.append("		<div class='right'>");
                sb.append("		<input type='text' class='text' name='" + field.getVarName() + "' value='' />");
                sb.append("		</div>");
                // 清浮动
                sb.append("		<span class='clearfix'></span>");
                sb.append("	</div>");

                // 3.0 更新字段模板
                field.setContent(sb.toString());
                iFieldDAO.update(field);
            } else if ("textarea".equals(field.getInputType())) {
                // 文本域
                StringBuffer sb = new StringBuffer();
                sb.append("	<div class='unit'>");
                sb.append("		<div class='left'>");
                sb.append("			<p class='subtitle'>" + field.getName() + "</p>");
                sb.append("		</div>");
                sb.append("		<div class='right'>");
                sb.append("			<textarea class='desc' name='" + field.getVarName() + "'></textarea>");
                sb.append("		</div>");
                // 清浮动
                sb.append("		<span class='clearfix'></span>");
                sb.append("	</div>");

                // 3.0 更新字段模板
                field.setContent(sb.toString());
                iFieldDAO.update(field);
            } else if ("number".equals(field.getInputType())) {
                // 数字
                StringBuffer sb = new StringBuffer();
                sb.append("	<div class='unit'>");
                sb.append("		<div class='left'>");
                sb.append("			<p class='subtitle'>" + field.getName() + "</p>");
                sb.append("		</div>");
                sb.append("		<div class='right'>");
                sb.append("		<input type='text' class='text' name='" + field.getVarName() + "' data-type=\"空|正整数\" error-msg=\"正整数格式错误\" value='' />");
                sb.append("		</div>");
                // 清浮动
                sb.append("		<span class='clearfix'></span>");
                sb.append("	</div>");

                // 3.0 更新字段模板
                field.setContent(sb.toString());
                iFieldDAO.update(field);
            } else if ("radio".equals(field.getInputType())) {
                // 字段对应的详情内容
                List<FieldProfile> fieldProfileList = iFieldProfileDAO.listByFieldId(field.getId());
                // 数字
                StringBuffer sb = new StringBuffer();
                sb.append("	<div class='unit'>");
                sb.append("		<div class='left'>");
                sb.append("			<p class='subtitle'>" + field.getName() + "</p>");
                sb.append("		</div>");
                sb.append("		<div class='right'>");
                sb.append("			<ul class='equal-8'>");
                for (FieldProfile fieldProfile : fieldProfileList) {
                    sb.append("			    <li><input type='radio' class='fill' name='" + field.getVarName() + "' value='" + fieldProfile.getId() + "'/>" + fieldProfile.getName() + "</li>");
                }
                sb.append("			</ul>");
                sb.append("		</div>");
                // 清浮动
                sb.append("		<span class='clearfix'></span>");
                sb.append("	</div>");

                // 3.0 更新字段模板
                field.setContent(sb.toString());
                iFieldDAO.update(field);
            } else if ("checkbox".equals(field.getInputType())) {
                // 字段对应的详情内容
                List<FieldProfile> fieldProfileList = iFieldProfileDAO.listByFieldId(field.getId());
                // 数字
                StringBuffer sb = new StringBuffer();
                sb.append("	<div class='unit'>");
                sb.append("		<div class='left'>");
                sb.append("			<p class='subtitle'>" + field.getName() + "</p>");
                sb.append("		</div>");
                sb.append("		<div class='right'>");
                sb.append("			<ul class='equal-8'>");
                for (FieldProfile fieldProfile : fieldProfileList) {
                    sb.append("			    <li><input type='checkbox' class='fill' name='" + field.getVarName() + "' value='" + fieldProfile.getId() + "' />" + fieldProfile.getName() + "</li>");
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

        // 3.0 向接口表中添加字段
        // 数据库名称
        String table_database = "video";
        String target_table_name = "api";
        for (int i = 0; i < list.size(); i++) {
            String varName = list.get(i).getVarName();

            StringBuffer sql = new StringBuffer();

            sql.append("CALL add_column( ");
            sql.append("'").append(table_database).append("'").append(" , ");
            sql.append("'").append(target_table_name).append("'").append(" , ");
            sql.append("'").append(varName).append("'").append(" ) ");

            iApiDAO.alter(sql.toString());
        }
    }

    /**
     * 获取分类筛选字段的信息2
     *
     * @param typeId     分类信息的主键
     * @param fieldName  字段名
     * @param fieldValue 字段选项名
     * @return
     */
    public List<Field> getListField(String typeId, String fieldName, String fieldValue) {
        // 1.0 查询该分类下的所有可筛选字段
        List<Field> fieldList = iFieldDAO.listCategoryField(typeId);
        if (fieldList != null && !fieldList.isEmpty()) {
            for (Field field : fieldList) {
                // 2.0 获取每个字段下的具体选项
                List<FieldProfile> fieldProfileList = iFieldProfileDAO.listByFieldId(field.getId());
                if (fieldProfileList == null || fieldProfileList.size() == 0) {
                    return null;
                }
                field.setFieldProfileList(fieldProfileList);

                // 3.0 判断参数是否合法
                if (StringUtils.isEmpty(fieldName) && StringUtils.isEmpty(fieldValue)) {

                } else {
                    if (StringUtils.isEmpty(fieldName)) {
                        return null;
                    }
                    if (StringUtils.isEmpty(fieldValue)) {
                        return null;
                    }

                    // 字段一致时，判断是否存在参数选项
                    if (field.getName().equals(fieldName)) {
                        boolean flag = false;
                        for (FieldProfile fieldProfileInfo : fieldProfileList) {
                            if (fieldProfileInfo.getName().equals(fieldValue)) {
                                flag = true;
                            }
                        }
                        if (!flag) {
                            return null;
                        }
                    }
                }
            }
        }

        return fieldList;
    }
}
