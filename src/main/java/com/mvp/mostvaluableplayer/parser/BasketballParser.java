package com.mvp.mostvaluableplayer.parser;

import com.mvp.mostvaluableplayer.dto.FileInputData;

import java.util.List;

public class BasketballParser implements FileParser {

	@Override
	public FileInputData parse(List<String> fileInput, String gameName) {
		FileInputData basketballPlayer = new FileInputData();
		basketballPlayer.setGameName(gameName);
		for (int i = 0; i < fileInput.size(); i++) {
			basketballPlayer.setPlayerName(fileInput.get(0));
			basketballPlayer.setNickName(fileInput.get(1));
			basketballPlayer.setNumber(Integer.parseInt(fileInput.get(2)));
			basketballPlayer.setTeamName(fileInput.get(3));
			basketballPlayer.setScoredPoints(Integer.parseInt(fileInput.get(4)));
			basketballPlayer.setRebounds(Integer.parseInt(fileInput.get(5)));
			basketballPlayer.setAssists(Integer.parseInt(fileInput.get(6)));
		}
		return basketballPlayer;
	}
}
