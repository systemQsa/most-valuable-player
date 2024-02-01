package com.mvp.mostvaluableplayer.command;

import com.mvp.mostvaluableplayer.dto.FileInputData;
import com.mvp.mostvaluableplayer.entity.Player;
import com.mvp.mostvaluableplayer.entity.Team;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HandBallCommandImpl implements Command {
	@Override
	public Team getTeamWinner(String gameName, Map<String, List<FileInputData>> teams, Map<String, Set<Player>> teamPlayers) {

		var scoreSumOfTeams = getScoreSumOfTeams(teams);

		return scoreSumOfTeams.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			.limit(1)
			.findFirst()
			.map(team -> new Team(gameName, team.getKey(), team.getValue(), teamPlayers.get(team.getKey())))
			.orElse(null);
	}

	@Override
	public Map<String, Player> getPlayers(String gameName, Map<String, List<FileInputData>> data) {
		return data.values().stream()
			.flatMap(Collection::stream)
			.map(player -> Player.builder()
				.gameName(gameName)
				.playerName(player.getPlayerName())
				.number(player.getNumber())
				.ratingPoints(2 * player.getGoalsMade() - player.getGoalsReceived())
				.nickName(player.getNickName())
				.build())
			.collect(Collectors.toMap(Player::getNickName, Function.identity()));
	}

	private Map<String, Integer> getScoreSumOfTeams(Map<String, List<FileInputData>> teams) {
		return teams.entrySet().stream()
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				entry -> entry.getValue().stream()
					.mapToInt(FileInputData::getGoalsMade).sum()
			));
	}
}
