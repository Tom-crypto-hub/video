package com.martinwj.dao.media;

import com.martinwj.entity.Media;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: IMediaDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
public interface IMediaDAO {
    /**
     * 查询媒体列表
     * @param param
     * @return
     */
    List<Media> list(Map<String, Object> param);

    /**
     * 根据接口自定义查询数据
     * @param selectSql
     * @return
     */
    List<Map<String, Object>> selectSqlByApi(@Param("selectSql") String selectSql);

    /**
     * 根据主键，查询该视频名称
     * @param mediaId
     * @return
     */
    String selectBiaotiById(String mediaId);

    /**
     * 根据主键查询媒体信息
     * @param mediaId 主键
     * @return
     */
    Media selectById(String mediaId);

    /**
     * 批量删除
     * @param mediaIdArr 主键数组
     */
    int batchDelete(@Param("mediaIdArr") String[] mediaIdArr);

    /**
     * 批量更新媒体
     * @param param
     */
    int batchUpdate(Map<String, Object> param);

    /**
     * 查询媒体信息，返回map
     * @param selectSql
     * @return
     */
    Map<String, Object> selectSqlById(@Param("selectSql") String selectSql);

    /**
     * 查询标题对应的媒体的数量
     * @param biaoti 标题
     * @param mediaId 主键
     * @return
     */
    int countByBiaoti(@Param("biaoti") String biaoti, @Param("mediaId") String mediaId);

    /**
     * 插入一条媒体信息
     * @param alterSql
     * @return
     */
    int insert(@Param("alterSql") String alterSql);

    /**
     * 更新一条媒体信息
     * @param alterSql
     * @return
     */
    int update(@Param("alterSql") String alterSql);
}
