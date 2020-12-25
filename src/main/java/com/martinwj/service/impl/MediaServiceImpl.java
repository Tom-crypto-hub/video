package com.martinwj.service.impl;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.api.IApiDAO;
import com.martinwj.dao.media.IMediaDAO;
import com.martinwj.dao.video.IVideoDAO;
import com.martinwj.entity.Media;
import com.martinwj.entity.Video;
import com.martinwj.exception.SysException;
import com.martinwj.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: MediaServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private IMediaDAO iMediaDAO;
    @Autowired
    private IApiDAO iApiDAO;
    @Autowired
    private IVideoDAO iVideoDAO;

    /**
     * 查询媒体列表
     *
     * @param param
     * @return
     */
    public List<Media> list(Map<String, Object> param) {
        return iMediaDAO.list(param);
    }

    /**
     * 根据接口自定义查询数据
     *
     * @param apiId 接口表主键
     * @return
     * @throws SysException
     */
    public List<Map<String, Object>> getDataByApiId(String apiId) throws SysException {

        Map<String, Object> apiInfo = iApiDAO.selectById(apiId);
        if (apiInfo == null) {
            throw new SysException(ErrorMsg.ERROR_700002);
        }

        if (StringUtils.isEmpty(apiInfo.get("field"))) {
            throw new SysException(ErrorMsg.ERROR_700002);
        }

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
//        sql.append(" TOP " + apiInfo.get("num").toString());
        sql.append(" mediaId, ");
        sql.append(" haibao, ");
        sql.append(" dafengmian, ");
        sql.append(" fengmian, ");
        sql.append(" biaoti, ");
        sql.append(" kandian, ");
        sql.append(" zongjishu, ");
        sql.append(" updateTime AS uploadTime ");
        sql.append(" FROM ");
        sql.append(" media ");
        sql.append(" WHERE ");
        sql.append(" status = '1' ");
        sql.append(" AND hasVideo = '1' ");// 其下有视频
        // 查询该接口定义了哪些字段
        String[] fieldArr = apiInfo.get("field").toString().split(",");
        for (int i = 0; i < fieldArr.length; i++) {
            // 查询每一个字段
            String fieldName = fieldArr[i];
            // 得到该接口中该字段的值
            String apiFieldValue = apiInfo.get(fieldName).toString();
            // 生成查询条件
            String[] arr = apiFieldValue.split(",");
            if (arr.length == 1) {
                sql.append(" AND " + fieldName + " = '" + apiFieldValue + "' ");
            } else {
                String str = "";
                for (int j = 0; j < arr.length; j++) {
                    if (j == 0) {
                        str = "'" + arr[j] + "'";
                    } else {
                        str += ",'" + arr[j] + "'";
                    }
                }
                sql.append(" AND " + fieldName + " IN (" + str + ") ");
            }
        }

        // 标签条件
        if (!StringUtils.isEmpty(apiInfo.get("tag"))) {
            String apiTag = apiInfo.get("tag").toString();
            String[] tagArr = apiTag.split(",");
            sql.append(" AND ( ");
            for (int k = 0; k < tagArr.length; k++) {
                if (k == (tagArr.length - 1)) {
                    sql.append(" ',' + tag + ',' LIKE '%," + tagArr[k] + ",%' ");
                } else {
                    sql.append(" ',' + tag + ',' LIKE '%," + tagArr[k] + ",%' OR ");
                }
            }
            sql.append(" ) ");
        }
        sql.append(" ORDER BY updateTime DESC ");
        sql.append(" limit ").append(0).append(", ").append(apiInfo.get("num").toString()).append(" ");

        // 返回查询结果
        List<Map<String, Object>> list = iMediaDAO.selectSqlByApi(sql.toString());

        // 判断是否需要查询视频信息
        String selectVideo = apiInfo.get("selectVideo").toString();
        if ("0".equals(selectVideo)) {
            // 不查询
        } else {
            if (list != null && list.isEmpty() == false) {
                int len = list.size();
                for (int i = 0; i < len; i++) {
                    String mediaId = list.get(i).get("mediaId").toString();
                    Video video = null;
                    if ("1".equals(selectVideo)) {
                        // 查询第一集
                        video = iVideoDAO.selectByMediaIdFirst(mediaId);
                    } else if ("2".equals(selectVideo)) {
                        // 查询最新一集
                        video = iVideoDAO.selectByMediaIdLast(mediaId);
                    }
                    if (video != null) {
                        list.get(i).put("videoInfo", video);
                    }
                }
            }
        }

        return list;
    }

    /**
     * 根据主键，查询该媒体信息标题
     * @param mediaId 主键
     * @return
     */
    @Override
    public String selectBiaotiById(String mediaId) {
        return iMediaDAO.selectBiaotiById(mediaId);
    }

    /**
     * 根据主键查询媒体信息
     * @param mediaId 主键
     * @return
     */
    public Media selectById(String mediaId) {
        return iMediaDAO.selectById(mediaId);
    }


}
