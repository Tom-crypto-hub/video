package com.martinwj.service;

import com.martinwj.entity.Qny;
import com.martinwj.exception.SysException;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName: TencentService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-21
 */
public interface QnyService {
    /**
     * 根据指定类型查询配置记录
     * @param type 类型
     * @return
     */
    public Qny selectByType(String type);

    /**
     * 保存配置
     * @param Qny
     */
    public void save(Qny Qny);

    /**
     * 上传本地图片到七牛云
     * @param file
     * @param Qny
     * @return
     * @throws IOException
     * @throws SysException
     */
    public String uploadImage(MultipartFile file, Qny Qny) throws IOException, SysException;

    /**
     * 远程图片上传到七牛云
     * @param url 远程图片地址
     * @param Qny 七牛云对象
     * @return
     * @throws SysException
     */
    public String uploadImageByYuancheng(String url, Qny Qny) throws SysException;

    /**
     * 图片裁剪后再次上传
     * @param Qny
     * @param auth
     * @param cfg
     * @param bucket
     * @param imgUrl
     * @return
     * @throws SysException
     */
    public String uploadCutImage(Qny Qny, Auth auth, Configuration cfg, String bucket, String imgUrl) throws SysException;

    /**
     * 删除七牛云空间的文件
     * @param auth
     * @param cfg
     * @param bucket 空间名称
     * @param fileName 文件名称
     */
    public void deleteFile(Auth auth, Configuration cfg, String bucket, String fileName);

    /**
     * 上传base64图片
     * @param file64
     * @param Qny
     * @return
     * @throws IOException
     */
    public String uploadAvatar(String file64, Qny Qny) throws IOException;

    /**
     * 上传视频文件
     * @param file 上传的文件
     * @param videoName 文件云端存储的名称
     * @param override 是否覆盖同名同位置文件
     * @param qny 七牛云的配置信息
     * @return
     * @throws IOException
     * @throws SysException
     */
    public String uploadVideo(MultipartFile file, String videoName, boolean override, Qny qny) throws IOException, SysException;
}
