package com.jcyt.lottery.service;

public enum TableStatus {
	BETTING,	//投注中 
	DEALING,	//发牌中
	DEALING_WAIT,	//等待发牌中
	SHUFFLE,	//洗牌中
	STANDBY,	//
	WAITING,	//等待中
	CLOSED,
	DISABLE,
	LOGOUT,
}
