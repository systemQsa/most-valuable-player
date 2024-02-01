package com.mvp.mostvaluableplayer.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileInputData {
	private String gameName;
	private String playerName;
	private String nickName;
	private Integer number;
	private String teamName;
	private int scoredPoints;
	private int rebounds;
	private int assists;
	private int goalsMade;
	private int goalsReceived;
}
