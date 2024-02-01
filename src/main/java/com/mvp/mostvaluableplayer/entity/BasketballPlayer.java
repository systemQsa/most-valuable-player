package com.mvp.mostvaluableplayer.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketballPlayer extends BasicPlayerData {
	private Integer scoredPoints;
	private Integer rebounds;
	private Integer assists;
}
