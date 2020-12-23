package com.martinwj.dao.player;

import com.martinwj.entity.Player;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: IPlayerDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Repository
public interface IPlayerDAO {
    
    /**
     * 查询播放器列表
     */
    List<Player> list();


}
