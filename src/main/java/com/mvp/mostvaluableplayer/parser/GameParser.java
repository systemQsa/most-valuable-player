package com.mvp.mostvaluableplayer.parser;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GameParser {

	BASKETBALL(new BasketballParser()),
	HANDBALL(new HandballParser());

	private final FileParser parser;

	GameParser(FileParser parser) {
		this.parser = parser;
	}

	public static FileParser getParseCommand(String gameName) {
		return Arrays.stream(GameParser.values())
			.filter(game -> game.name().equals(gameName))
			.findFirst()
			.map(GameParser::getParser)
			.orElse(null);
	}
}
