package com.mvp.mostvaluableplayer.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandballPlayer extends BasicPlayerData {
	private Integer goalsMade;
	private Integer goalsReceived;
}
