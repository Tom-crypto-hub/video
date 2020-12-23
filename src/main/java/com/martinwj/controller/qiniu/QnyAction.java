package com.martinwj.controller.qiniu;

import com.martinwj.entity.Qny;
import com.martinwj.entity.Result;
import com.martinwj.exception.SysException;
import com.martinwj.service.QnyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName: QnyAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Controller
@RequestMapping("qny")
public class QnyAction {

    @Autowired
    private QnyService qnyService;

    /**
     * 配置指定类型
     * @param map
     * @param type
     * @return
     */
    @RequestMapping("edit.action")
    public String edit(ModelMap map,
                       @RequestParam(value="type") String type) {

        Qny qny = qnyService.selectByType(type);
        map.put("qiniuInfo", qny);

        map.put("type", type);

        return "admin/qiniu_info/edit";
    }

    /**
     * 保存配置
     * @param qny
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(Qny qny) {

        qnyService.save(qny);
        return Result.success();
    }

    /**
     * 上传本地图片到七牛云
     * @param file
     * @param type 类型
     * @return
     * @throws IOException
     * @throws SysException
     */
    @RequestMapping("upload_image.json")
    @ResponseBody
    public Result uploadImage(
            MultipartFile file,
            @RequestParam(value="type") String type) throws IOException, SysException {

        Qny qny = qnyService.selectByType(type);
        String imgUrl = qnyService.uploadImage(file, qny);

        return Result.success().add("imgUrl", imgUrl);
    }

    /**
     * 远程图片上传到七牛云
     * @param imgUrl 远程图片地址
     * @param type 类型
     * @return
     * @throws IOException
     * @throws SysException
     */
    @RequestMapping("upload_image_by_yuancheng.json")
    @ResponseBody
    public Result uploadImageByYuancheng(
            @RequestParam(value="imgUrl") String imgUrl,
            @RequestParam(value="type") String type) throws IOException, SysException {

        Qny qny = qnyService.selectByType(type);
        imgUrl = qnyService.uploadImageByYuancheng(imgUrl , qny);

        return Result.success().add("imgUrl", imgUrl);
    }


}
