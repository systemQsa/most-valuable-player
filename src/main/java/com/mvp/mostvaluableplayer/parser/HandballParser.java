package com.mvp.mostvaluableplayer.parser;

import com.mvp.mostvaluableplayer.dto.FileInputData;

import java.util.List;

public class HandballParser implements FileParser {

	@Override
	public FileInputData parse(List<String> fileInput, String gameName) {
		FileInputData handballPlayer = new FileInputData();
		handballPlayer.setGameName(gameName);
		for (int i = 0; i < fileInput.size(); i++) {
			handballPlayer.setPlayerName(fileInput.get(0));
			handballPlayer.setNickName(fileInput.get(1));
			handballPlayer.setNumber(Integer.parseInt(fileInput.get(2)));
			handballPlayer.setTeamName(fileInput.get(3));
			handballPlayer.setGoalsMade(Integer.parseInt(fileInput.get(4)));
			handballPlayer.setGoalsReceived(Integer.parseInt(fileInput.get(5)));
		}
		return handballPlayer;
	}
}
