package com.martinwj.controller.api;

import com.martinwj.entity.Result;
import com.martinwj.entity.Video;
import com.martinwj.exception.SysException;
import com.martinwj.service.MediaService;
import com.martinwj.service.SlideProfileService;
import com.martinwj.service.VideoService;
import it.sauronsoftware.jave.VideoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ApiAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-22
 */
@Controller
@RequestMapping("api")
public class ApiAction {

    @Autowired
    private SlideProfileService slideProfileService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private VideoService videoService;

    /**
     * 幻灯片数据
     * @param apiId 接口表主键
     * @return
     */
    @RequestMapping("slide.json")
    @ResponseBody
    public Result slide(
            HttpServletRequest request,
            @RequestParam(value="apiId") String apiId) {

        List<Map<String, Object>> list = slideProfileService.mapListByApiId(apiId);

        return Result.success().add("list", list);
    }

    /**
     * 获取该视频的信息
     * @param videoId 视频主键
     * @return
     */
    @RequestMapping("get_video_info.json")
    @ResponseBody
    public Result getVideoInfo(
            HttpServletRequest request,
            @RequestParam(value="videoId") String videoId) {

        Video video = videoService.selectById(videoId);

        return Result.success().add("videoInfo", video);
    }

    /**
     * 获取视频地址
     * @param mediaId 媒体信息的主键
     * @return
     */
    @RequestMapping("get_video_play.json")
    @ResponseBody
    public Result getVideoPlay(
            @RequestParam(value="videoId") String videoId) {

        String videoPlay = videoService.selectVideoPlayById(videoId);

        return Result.success().add("videoPlay", videoPlay);
    }

    /**
     * 获取该视频的播放列表
     * @param mediaId 媒体信息的主键
     * @return
     */
    @RequestMapping("get_video_list.json")
    @ResponseBody
    public Result getVideoList(
            @RequestParam(value="mediaId") String mediaId) {

        List<Video> list = videoService.listByMediaId(mediaId);

        return Result.success().add("list", list);
    }

    /**
     * 根据主键，获取媒体信息
     * @param mediaId 媒体信息的主键
     * @return
     * @throws SysException
     */
    @RequestMapping("get_media_info.json")
    @ResponseBody
    public Result getMediaInfo(
            @RequestParam(value="mediaId") String mediaId) throws SysException {

        Map<String, Object> info = mediaService.selectByMediaId(mediaId);

        return Result.success().add("info", info);
    }

    /**
     * 根据接口自定义查询数据
     * @return
     * @throws SysException
     */
    @RequestMapping("data.json")
    @ResponseBody
    public Result data(
            @RequestParam(value="apiId") String apiId) throws SysException {

        List<Map<String, Object>> list = mediaService.getDataByApiId(apiId);

        return Result.success().add("list", list);
    }

    /**
     * 根据接口自定义查询排行榜数据
     * @return
     * @throws SysException
     */
    @RequestMapping("rank.json")
    @ResponseBody
    public Result rank(
            @RequestParam(value="apiId") String apiId) throws SysException {

        List<Map<String, Object>> list = mediaService.getRankDataByApiId(apiId);

        return Result.success().add("list", list);
    }
}
