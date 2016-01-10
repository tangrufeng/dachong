package com.jcyt.lottery.entity;

/**
 * <p>Title: GameRoundInfo.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: jc-yt.com</p>
 * @author tang
 * @date 2015-12-20 下午4:22:34
 * @version 1.0
 *
 */
public class GameRoundInfo {

	private int id;
	
	private int result;
	
	private String createTime;
	
	private String gameType;
	
	private String videoPath;
	
	private String cardInfo;
	
	private int dealer;
	
	private int groupId;
	
	private String lastPlayTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}

	public int getDealer() {
		return dealer;
	}

	public void setDealer(int dealer) {
		this.dealer = dealer;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getLastPlayTime() {
		return lastPlayTime;
	}

	public void setLastPlayTime(String lastPlayTime) {
		this.lastPlayTime = lastPlayTime;
	}

	@Override
	public String toString() {
		return "GameRoundInfo [id=" + id + ", result=" + result
				+ ", createTime=" + createTime + ", gameType=" + gameType
				+ ", videoPath=" + videoPath + ", cardInfo=" + cardInfo
				+ ", dealer=" + dealer + ", groupId=" + groupId
				+ ", lastPlayTime=" + lastPlayTime + "]";
	}
	
	
	
	

}
