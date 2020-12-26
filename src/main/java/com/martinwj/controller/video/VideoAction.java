package com.martinwj.controller.video;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.martinwj.constant.ErrorMsg;
import com.martinwj.entity.Player;
import com.martinwj.entity.Result;
import com.martinwj.entity.Video;
import com.martinwj.exception.SysException;
import com.martinwj.service.MediaService;
import com.martinwj.service.PlayerService;
import com.martinwj.service.VideoService;
import it.sauronsoftware.jave.VideoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName: VideoAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
@Controller
@RequestMapping("video_info")
public class VideoAction {

    @Autowired
    private VideoService videoService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private PlayerService playerService;


    /**
     * 根据媒体信息主键，查询该视频下的所有播放集数
     * @param map
     * @param mediaId 媒体信息主键
     * @return
     */
    @RequestMapping("list.action")
    public String list(ModelMap map,
                       @RequestParam(value="mediaId") String mediaId,
                       @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                       @RequestParam(value="pageSize", defaultValue="10") int pageSize) {

        // pageHelper分页插件
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<Video> list = videoService.listByMediaIdDesc(mediaId);
        PageInfo<Video> page = new PageInfo<Video>(list);
        map.put("pageInfo", page);

        // 根据媒体信息主键，查询该媒体标题
        String biaoti = mediaService.selectBiaotiById(mediaId);
        map.put("biaoti", biaoti);

        map.put("mediaId", mediaId);

        return "admin/video_info/list";
    }

    /**
     * 视频播放地址编辑
     */
    @RequestMapping("edit.action")
    public String edit(ModelMap map,
                       @RequestParam(required=false, value="videoId") String videoId,
                       @RequestParam(value="mediaId") String mediaId) {


        if (!StringUtils.isEmpty(videoId)) {
            Video video = videoService.selectById(videoId);
            map.put("videoInfo", video);
        }

        // 根据媒体信息主键，查询该视频名称
        String biaoti = mediaService.selectBiaotiById(mediaId);
        map.put("biaoti", biaoti);

        // 查询播放器列表
        List<Player> playerList = playerService.list();
        map.put("playerList", playerList);

        map.put("videoId", videoId);
        map.put("mediaId", mediaId);

        return "admin/video_info/edit";
    }

    /**
     * 播放视频
     */
    @RequestMapping("play_video.action")
    public String playVideo(ModelMap map,
                            @RequestParam(required=false, value="videoId") String videoId) {

        String videoPlay = videoService.selectVideoPlayById(videoId);
        map.put("videoPlay", videoPlay);

        return "admin/video_info/video";
    }

    /**
     * 视频播放地址保存
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(Video video) {

        videoService.save(video);
        return Result.success();
    }

    /**
     * 更新统一封面
     * @param mediaId 媒体信息主键
     * @param imgUrl 图片地址
     * @return
     * @throws SysException
     */
    @RequestMapping("update_image.json")
    @ResponseBody
    public Result updateImage(
            @RequestParam(value="mediaId") String mediaId,
            @RequestParam(value="imgUrl") String imgUrl) throws SysException {

        if (StringUtils.isEmpty(mediaId) || StringUtils.isEmpty(imgUrl)) {
            throw new SysException(ErrorMsg.ERROR_600002);
        }
        videoService.updateImage(mediaId, imgUrl);

        return Result.success();
    }

    /**
     * 更新统一权限值
     * @param mediaId 媒体信息主键
     * @param power 权限值
     * @return
     * @throws SysException
     */
    @RequestMapping("update_power.json")
    @ResponseBody
    public Result updatePower(
            @RequestParam(value="mediaId") String mediaId,
            @RequestParam(value="power") String power) throws SysException {

        if (StringUtils.isEmpty(power) || StringUtils.isEmpty(power)) {
            throw new SysException(ErrorMsg.ERROR_600006);
        }
        videoService.updatePower(mediaId, power);

        return Result.success();
    }

    /**
     * 批量更新排序
     * @param videoIdArr 主键数组
     * @param sortArr 排序数组
     */
    @RequestMapping("update_sort.json")
    @ResponseBody
    public Result updateSort(
            @RequestParam(value="videoIdArr") String[] videoIdArr,
            @RequestParam(value="sortArr") String[] sortArr) {

        videoService.updateSort(videoIdArr, sortArr);

        return Result.success();
    }

    /**
     * 批量更新视频状态
     * @param videoIdArr 主键数组
     * @param status 状态
     * @return
     */
    @RequestMapping("batch_update_status.json")
    @ResponseBody
    public Result batchUpdateStatus(
            @RequestParam(value="videoIdArr") String[] videoIdArr,
            @RequestParam(value="status") String status) {

        videoService.batchUpdateStatus(videoIdArr, status);

        return Result.success();
    }

    /**
     * 批量删除视频
     * @param videoIdArr 主键数组
     * @return
     * @throws SysException
     */
    @RequestMapping("batch_delete.json")
    @ResponseBody
    public Result batchDelete(
            @RequestParam(value="videoIdArr") String[] videoIdArr) throws SysException {

        videoService.batchDelete(videoIdArr);

        return Result.success();
    }

}
