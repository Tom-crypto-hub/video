package com.martinwj.service;

import com.martinwj.entity.Media;
import com.martinwj.exception.SysException;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: MediaService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
public interface MediaService {

    /**
     * 查询媒体列表
     * @param param
     * @return
     */
    public List<Media> list(Map<String, Object> param);

    /**
     * 根据接口自定义查询数据
     * @param apiId 接口表主键
     * @return
     * @throws SysException
     */
    public List<Map<String, Object>> getDataByApiId(String apiId) throws SysException;

    /**
     * 根据主键，查询该媒体信息标题
     * @param mediaId 主键
     * @return
     */
    String selectBiaotiById(String mediaId);

    /**
     * 根据主键查询媒体信息
     * @param mediaId 主键
     * @return
     */
    public Media selectById(String mediaId);

    /**
     * 保存媒体信息
     * @param param
     * @throws SysException
     */
    void save(Map<String, Object> param) throws SysException;

    /**
     * 批量更新媒体的状态
     * @param param
     */
    public void batchUpdateStatus(Map<String, Object> param);

    /**
     * 根据主键和分类id，获取媒体字段信息
     * @param mediaId 主键
     * @param typeId 分类id
     * @return
     */
    Map<String, Object> selectByIdAndTypeId(String mediaId, String typeId);

    /**
     * 批量移动到分类
     * @param param
     */
    public void batchUpdateType(Map<String, Object> param);

    /**
     * 批量删除
     * @param mediaIdArr 主键数组
     */
    public void batchDelete(String[] mediaIdArr);

    /**
     * 根据主键，获取媒体信息
     * @param mediaId 媒体信息的主键
     * @return
     * @throws SysException
     */
    public Map<String, Object> selectByMediaId(String mediaId) throws SysException;

    /**
     * 根据接口自定义查询排行榜数据
     * @param apiId
     * @return
     * @throws SysException
     */
    List<Map<String, Object>> getRankDataByApiId(String apiId) throws SysException;
}
