package com.martinwj.service;

/**
 * @ClassName: TypeFieldService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
public interface TypeFieldService {

    /**
     * 检索指定字段是否必填
     * @param typeId 分类信息主键
     * @param varName 字段变量名
     * @return
     */
    public String selectIsRequired(String typeId, String varName) ;
}
