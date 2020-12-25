package com.martinwj.controller.template;

import com.martinwj.entity.Template;
import com.martinwj.service.TemplateService;
import com.martinwj.util.ImgBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TemplateAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Controller
@RequestMapping("template")
public class TemplateAction {

    @Autowired
    private TemplateService templateService;

    /**
     * 查询模板风格列表
     * @throws IOException
     */
    @RequestMapping("list.action")
    public String list(ModelMap map, HttpServletRequest request) throws IOException {

        // 读取所有电脑端模板
        Map<String, String> templateMapPC = null;
        List<Map<String, String>> templateListPC = new ArrayList<Map<String, String>>();

        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/pages/portal/pc/template");
//		System.out.println("path---"+path);
        File file = new File(path);
        // 获得该文件夹内的所有文件夹名
        File[] arr = file.listFiles();
        if (arr!=null && arr.length>0) {
            for (int i=0; i<arr.length; i++) {
                templateMapPC = new HashMap<String, String>();
                // 获取文件夹名（即模板名称）
                String templateName = arr[i].getName();
//				System.out.println("模板名称：" + templateName);
                String imgBase64 = "";
                try {
                    // 该模板的预览图路径
                    String previewPath = path + "/" + arr[i].getName() + "/preview.jpg";
//					System.out.println("模板预览图路径：" + previewPath);
                    imgBase64 = ImgBase64.getImageBase64(previewPath);
//					System.out.println("imgBase64："+imgBase64);
                } catch (Exception e) {
                    imgBase64 = "";
                }

                templateMapPC.put("templateName", templateName);
                templateMapPC.put("previewPath", imgBase64);
                templateListPC.add(templateMapPC);
            }
        }
        map.put("templateListPC", templateListPC);
        // 获取电脑端已选模板的选项
        Template templatePC = templateService.selectByType("pc");
        map.put("templatePC", templatePC);

        return "admin/template_info/list";
    }

}
