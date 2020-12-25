package com.martinwj.controller.video;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.martinwj.entity.Player;
import com.martinwj.entity.Video;
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
        } else if (!StringUtils.isEmpty(mediaId)) {
            Video video = videoService.selectByMediaId(mediaId);
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



}
