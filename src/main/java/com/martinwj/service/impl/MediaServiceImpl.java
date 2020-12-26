package com.martinwj.service.impl;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.api.IApiDAO;
import com.martinwj.dao.field.IFieldDAO;
import com.martinwj.dao.fieldProfile.IFieldProfileDAO;
import com.martinwj.dao.media.IMediaDAO;
import com.martinwj.dao.tag.ITagDAO;
import com.martinwj.dao.video.IVideoDAO;
import com.martinwj.entity.*;
import com.martinwj.exception.SysException;
import com.martinwj.service.MediaService;
import com.martinwj.service.QnyService;
import com.martinwj.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.tagext.TagInfo;
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
    @Autowired
    private ITagDAO iTagDAO;
    @Autowired
    private IFieldDAO iFieldDAO;
    @Autowired
    private QnyService qnyService;
    @Autowired
    private IFieldProfileDAO iFieldProfileDAO;

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
     * 保存媒体信息
     * @param param
     * @throws SysException
     */
    public void save(Map<String, Object> param) throws SysException {
        // 生成标签
        if (StringUtils.isEmpty(param.get("tag"))) {
            // 清空该媒体的标签
            param.put("tag", "");
        } else {
            String tags = param.get("tag").toString();
            // 1.0 将得到的标签转为数组格式
            String[] tagArr = tags.split(",");
            for (int i=0; i<tagArr.length; i++) {
                // 根据标签中文名称，查询标签是否已存在
                int count = iTagDAO.countByName(tagArr[i]);
                if (count==0) {
                    // 不存在，插入新标签
                    Tag tag = new Tag();
                    tag.setName(tagArr[i]);

                    iTagDAO.insert(tag);
                }
            }

            // 2.0 重新根据用户输入的标签，查询标签的主键，按从小到大排序
            List<String> idList = iTagDAO.listIdByNameArr(tagArr);

            // 3.0 将集合转为 1,3这种字符串
            String str = "";
            for (int i=0; i<idList.size(); i++) {
                if (i==0) {
                    str = idList.get(i);
                } else {
                    str = str + "," + idList.get(i);
                }
            }

            // 4.0 保存新的标签
            param.put("tag", str);
        }

        // 分类
        String typeId = param.get("typeId").toString();
        // 标题
        String biaoti = param.get("biaoti").toString();
        // 判断是否允许标题重复
        String repeat = param.get("repeat").toString();

        // 查询字段列表
        List<Field> fieldList = iFieldDAO.listByTypeId(typeId);
        if (fieldList!=null && fieldList.isEmpty()==false) {
            // 判断是新增还是更新
            String mediaId = param.get("mediaId").toString();
            if (StringUtils.isEmpty(mediaId)) {
                // 新增
                // 1.0 判断是否允许标题重复
                if (!"1".equals(repeat)) {
                    // 校验标题是否重复
                    int count = iMediaDAO.countByBiaoti(biaoti, null);
                    if (count>0) {
                        // 重复
                        throw new SysException(ErrorMsg.ERROR_300001);
                    }
                }

                //2.0 生成插入数据SQL文
                StringBuffer sql = new StringBuffer();
                sql.append(" INSERT INTO ");
                sql.append(" media ");
                sql.append(" ( ");
                for (Field fieldInfo : fieldList) {
                    sql.append(fieldInfo.getVarName() + ",");
                }
                sql.append(" tag, ");
                sql.append(" typeId, ");
                sql.append(" haibao, ");
                sql.append(" dafengmian, ");
                sql.append(" fengmian, ");
                sql.append(" biaoti, ");
                sql.append(" bieming, ");
                sql.append(" jianjie, ");
                sql.append(" status, ");
                sql.append(" hasVideo, ");
                sql.append(" updateTime ");
                sql.append(" ) ");
                sql.append(" VALUES ");
                sql.append(" ( ");
                for (Field fieldInfo : fieldList) {
                    String value = "";
                    if (param.get(fieldInfo.getVarName())!=null) {
                        value = param.get(fieldInfo.getVarName()).toString();
                    }
                    sql.append(" '"+value+"', ");
                }
                sql.append(" '"+param.get("tag").toString()+"', ");
                sql.append(" '"+typeId+"', ");
                sql.append(" '"+param.get("haibao").toString()+"', ");
                sql.append(" '"+param.get("dafengmian").toString()+"', ");
                sql.append(" '"+param.get("fengmian").toString()+"', ");
                sql.append(" '"+biaoti+"', ");
                sql.append(" '"+param.get("bieming").toString()+"', ");
                sql.append(" '"+param.get("jianjie").toString()+"', ");
                sql.append(" '1', ");
                sql.append(" '0', ");
                sql.append(" now() ");
                sql.append(" ) ");

                iMediaDAO.insert(sql.toString());
            } else {
                // 更新
                // 1.0 判断是否允许标题重复
                if (!"1".equals(repeat)) {
                    int count = iMediaDAO.countByBiaoti(biaoti, mediaId);
                    if (count>0) {
                        // 重复
                        throw new SysException(ErrorMsg.ERROR_300001);
                    }
                }

                // 2.0 生成更新数据SQL文
                StringBuffer sql = new StringBuffer();
                sql.append(" UPDATE ");
                sql.append(" media ");
                sql.append(" SET ");
                for (Field fieldInfo : fieldList) {
                    String value = "";
                    if (param.get(fieldInfo.getVarName())!=null) {
                        value = param.get(fieldInfo.getVarName()).toString();
                    }
                    sql.append(fieldInfo.getVarName() + "=" + "'" + value + "',");
                }
                sql.append(" tag='"+param.get("tag").toString()+"', ");
                sql.append(" haibao='"+param.get("haibao").toString()+"', ");
                sql.append(" dafengmian='"+param.get("dafengmian").toString()+"', ");
                sql.append(" fengmian='"+param.get("fengmian").toString()+"', ");
                sql.append(" biaoti='"+biaoti+"', ");
                sql.append(" bieming='"+param.get("bieming").toString()+"', ");
                sql.append(" jianjie='"+param.get("jianjie").toString()+"' ");
                sql.append(" WHERE ");
                sql.append(" mediaId = '" + mediaId + "' ");

                iMediaDAO.update(sql.toString());
            }
        }
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

    /**
     * 批量更新媒体的状态
     * @param param
     */
    public void batchUpdateStatus(Map<String, Object> param) {
        // 批量更新媒体的状态
        iMediaDAO.batchUpdate(param);

        // 批量更新视频状态
        iVideoDAO.batchUpdate(param);
    }

    /**
     * 根据主键和分类id，获取媒体字段信息
     * @param mediaId 主键
     * @param typeId 分类id
     * @return
     */
    public Map<String, Object> selectByIdAndTypeId(String mediaId, String typeId) {
        // 1.0 根据分类id，查询该媒体使用了哪些字段
        List<Field> list = iFieldDAO.listByTypeId(typeId);
        if (list!=null && list.isEmpty()==false) {
            // 生成媒体查询sql（查哪些字段）
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT ");
            for (int i=0; i<list.size(); i++) {
                if (i==(list.size()-1)) {
                    sql.append(list.get(i).getVarName());
                } else {
                    sql.append(list.get(i).getVarName() + ",");
                }
            }
            sql.append(" FROM ");
            sql.append(" media ");
            sql.append(" WHERE ");
            sql.append(" mediaId = '" + mediaId + "' ");

            // 返回查询结果
            return iMediaDAO.selectSqlById(sql.toString());
        }

        return null;
    }

    /**
     * 批量移动到分类
     * @param param
     */
    public void batchUpdateType(Map<String, Object> param) {
        iMediaDAO.batchUpdate(param);
    }

    /**
     * 批量删除
     * @param mediaIdArr 主键数组
     */
    public void batchDelete(String[] mediaIdArr) {

        // 1.0 获取视频信息，删除七牛云对象信息
        Qny videoQny = qnyService.selectByType("video");
        Qny haibaoQny = qnyService.selectByType("haibao");
        Qny fengmianQny = qnyService.selectByType("fengmian");
        Qny contentQny = qnyService.selectByType("content");
        Qny dafengmianQny = qnyService.selectByType("dafengmian");
        Qny touxiangQny = qnyService.selectByType("touxiang");

        // 1.1 获取所有的视频信息的URL链接
        List<Video> videos = iVideoDAO.selectByMediaId(mediaIdArr);
        for(Video video : videos) {
            // 1.1.1 删除视频文件
            if(video.getUrl() != null) {
                int i = video.getUrl().lastIndexOf("/");
                QiNiuUtil.deleteFile(videoQny, video.getUrl().substring(i + 1));
            }
            // 1.1.2 删除视频的封面文件
            if(video.getImage() != null) {
                int i = video.getImage().lastIndexOf("/");
                QiNiuUtil.deleteFile(fengmianQny, video.getImage().substring(i + 1));
            }
        }

        for(String mediaId : mediaIdArr) {
            Media media = iMediaDAO.selectById(mediaId);
            // 1.1.3 删除海报信息
            if(media.getHaibao() != null) {
                int i = media.getHaibao().lastIndexOf("/");
                QiNiuUtil.deleteFile(haibaoQny, media.getHaibao().substring(i + 1));
            }
            // 1.1.4 删除封面信息
            if(media.getFengmian() != null) {
                int i = media.getFengmian().lastIndexOf("/");
                QiNiuUtil.deleteFile(fengmianQny, media.getFengmian().substring(i + 1));
            }
            // 1.1.5 删除大封面信息
            if(media.getDafengmian() != null) {
                int i = media.getDafengmian().lastIndexOf("/");
                QiNiuUtil.deleteFile(haibaoQny, media.getDafengmian().substring(i + 1));
            }
        }

        // 2.0 删除其下的视频信息
        iVideoDAO.batchDeleteByMediaId(mediaIdArr);

        // 2.0 删除媒体信息
        iMediaDAO.batchDelete(mediaIdArr);
    }

    /**
     * 根据主键，获取媒体信息
     * @param mediaId 媒体信息的主键
     * @return
     * @throws SysException
     */
    public Map<String, Object> selectByMediaId(String mediaId) throws SysException {
        Map<String, Object> map = iMediaDAO.selectByMediaId(mediaId);
        if (map==null) {
            throw new SysException(ErrorMsg.ERROR_300003);
        }

        // 遍历map，获取字段对应的文本
        for (String key : map.keySet()) {
            if ("mediaId".equals(key) || "tag".equals(key) || "haibao".equals(key) || "biaoti".equals(key) || "kandian".equals(key) || "jianjie".equals(key)) {

            } else {
                String fieldProfileId = map.get(key).toString();
                String[] arr = fieldProfileId.split(",");
                if (arr!=null && arr.length>0) {
                    // 判断该字段是否是复选框
                    Field field = iFieldDAO.selectByVarName(key);
                    if (field!=null) {
                        // 判断字段类型
                        if ("radio".equals(field.getInputType())) {
                            // 单选框
                            String value = iFieldProfileDAO.selectById(fieldProfileId);
                            if (!StringUtils.isEmpty(value)) {
                                map.put(key, value);
                            }
                        } else if ("checkbox".equals(field.getInputType())) {
                            // 复选框
                            List<String> list = iFieldProfileDAO.selectByIdArr(arr);
                            if (list!=null && list.isEmpty()==false) {
                                map.put(key, list);
                            }
                        }
                    }
                }
            }
        }

        return map;
    }

    /**
     * 根据接口自定义查询排行榜数据
     * @param apiId
     * @return
     * @throws SysException
     */
    public List<Map<String, Object>> getRankDataByApiId(String apiId) throws SysException {
        Map<String, Object> apiInfo = iApiDAO.selectById(apiId);
        if (apiInfo==null) {
            throw new SysException(ErrorMsg.ERROR_700002);
        }

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" mi.mediaId, ");
        sql.append(" mi.biaoti, ");
        sql.append(" mi.fengmian, ");
        sql.append(" mi.zongjishu, ");
        sql.append(" vi.viewCount ");
        sql.append(" FROM ");
        sql.append(" media mi, ");
        sql.append(" ( ");
        sql.append(" SELECT ");
        sql.append(" mediaId, ");
        if ("day".equals(apiInfo.get("rankType"))) {
            sql.append(" SUM(viewCountDay) AS viewCount ");
        } else if ("week".equals(apiInfo.get("rankType"))) {
            sql.append(" SUM(viewCountWeek) AS viewCount ");
        } else if ("month".equals(apiInfo.get("rankType"))) {
            sql.append(" SUM(viewCountMonth) AS viewCount ");
        } else if ("year".equals(apiInfo.get("rankType"))) {
            sql.append(" SUM(viewCountYear) AS viewCount ");
        } else {
            return null;
        }
        sql.append(" FROM ");
        sql.append(" video ");
        sql.append(" GROUP BY ");
        sql.append(" mediaId ");
        sql.append(" ) vi ");
        sql.append(" WHERE ");
        sql.append(" mi.status = '1' ");
        sql.append(" AND mi.hasVideo = '1' ");// 其下有视频
        sql.append(" AND mi.mediaId = vi.mediaId ");
        sql.append(" AND mi.typeId = '" + apiInfo.get("typeId") + "' ");
        sql.append(" ORDER BY ");
        sql.append(" vi.viewCount DESC ");
        sql.append(" limit ").append(apiInfo.get("num")).append("1");

        // 返回查询结果
        List<Map<String, Object>> list = iMediaDAO.selectSqlByApi(sql.toString());

        // 判断是否需要查询视频信息
        String selectVideo = apiInfo.get("selectVideo").toString();
        if ("0".equals(selectVideo)) {
            // 不查询
        } else {
            if (list!=null && list.isEmpty()==false) {
                int len = list.size();
                for (int i=0; i<len; i++) {
                    String mediaId = list.get(i).get("mediaId").toString();
                    Video video = null;
                    if ("1".equals(selectVideo)) {
                        // 查询第一集
                        video = iVideoDAO.selectByMediaIdFirst(mediaId);
                    } else if ("2".equals(selectVideo)) {
                        // 查询最新一集
                        video = iVideoDAO.selectByMediaIdLast(mediaId);
                    }
                    if (video!=null) {
                        list.get(i).put("videoInfo", video);
                    }
                }
            }
        }

        return list;
    }

}
