package com.mvp.mostvaluableplayer.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player {
	private String gameName;
	private String playerName;
	private String nickName;
	private Integer number;
	private Integer ratingPoints;
}
