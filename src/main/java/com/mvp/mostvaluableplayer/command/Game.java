package com.mvp.mostvaluableplayer.command;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Game {
	BASKETBALL(new BasketballCommandImpl()),
	HANDBALL(new HandBallCommandImpl());

	private final Command command;

	Game(Command command) {
		this.command = command;
	}

	public static Command getGame(String gameName) {
		return Arrays.stream(Game.values())
			.filter(command -> command.name().equals(gameName))
			.map(Game::getCommand)
			.findFirst()
			.orElse(null);
	}
}
