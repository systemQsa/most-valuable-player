package com.mvp.mostvaluableplayer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class Team {
	private String game;
	private String teamName;
	private Integer ratingPoints;
	private Set<Player> players;
}

