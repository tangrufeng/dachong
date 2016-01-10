package com.jcyt.lottery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jcyt.lottery.entity.GameRoundInfo;
import com.jcyt.lottery.sqlmapper.SqlMapperInterface;

/**
 * <p>Title: GameRoundInfoDAO.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: jc-yt.com</p>
 * @author tang
 * @date 2015-12-20 下午4:37:56
 * @version 1.0
 *
 */
public interface GameRoundInfoDAO extends SqlMapperInterface{

	public List<GameRoundInfo> getGameRoundInfo(@Param("dealer")int dealer);
}
