package com.mvp.mostvaluableplayer.service;

import com.mvp.mostvaluableplayer.command.Game;
import com.mvp.mostvaluableplayer.dto.FileInputData;
import com.mvp.mostvaluableplayer.entity.Player;
import com.mvp.mostvaluableplayer.entity.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MainService {
	private final ReadFileService fileReader = new ReadFileService();
	private final Map<String, Integer> playersScoresOfAllGames = new HashMap<>();

	public void playGames() {
		var filesInputData = fileReader.readFiles();

		var groupedGameTeams = groupGameTeams(filesInputData);

		var winnerTeams = getWinnerTeams(groupedGameTeams);

		calculatePlayersRatingPoints(groupedGameTeams, winnerTeams);

		var mostValuablePlayer = getMostValuablePlayer();

		mostValuablePlayer.forEach((key, value) -> log.info("Most Valuable Player = {}, {}", key, value));
	}

	private void calculatePlayersRatingPoints(Map<String, Map<String, List<FileInputData>>> groupedGameTeams,
											  Set<Team> winnerTeams) {
		groupedGameTeams.keySet().stream()
			.forEach(key -> {
				var gameCommand = Game.getGame(key);
				var players = gameCommand.getPlayers(key, groupedGameTeams.get(key));
				increasePlayerRatingPoints(winnerTeams, players);
				setPlayersScoresOfAllGames(players);
			});
	}

	private Map<String, Integer> getMostValuablePlayer() {
		var maxRatingPoints = Collections.max(playersScoresOfAllGames.values());

		return playersScoresOfAllGames.entrySet()
			.stream()
			.filter(entry -> Objects.equals(entry.getValue(), maxRatingPoints))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	private void setPlayersScoresOfAllGames(Map<String, Player> players) {
		players.values().forEach(player -> {
			playersScoresOfAllGames.computeIfPresent(player.getNickName(),
				(nickName, currentPoints) -> currentPoints + player.getRatingPoints()
			);
			if (playersScoresOfAllGames.get(player.getNickName()) == null) {
				playersScoresOfAllGames.putIfAbsent(player.getNickName(), player.getRatingPoints());
			}
		});
	}

	private void increasePlayerRatingPoints(Set<Team> winnerTeams, Map<String, Player> playerMap) {
		winnerTeams
			.forEach(team -> team.getPlayers()
				.forEach(player -> {
					Player gamePlayer = playerMap.get(player.getNickName());
					gamePlayer.setRatingPoints(gamePlayer.getRatingPoints() + 10);
					playerMap.put(player.getNickName(), gamePlayer);
				}));
	}

	private Set<Team> getWinnerTeams(Map<String, Map<String, List<FileInputData>>> groupedGameTeams) {
		return groupedGameTeams.keySet().stream()
			.map(key -> {
				var game = Game.getGame(key);
				var gameParticipants = groupedGameTeams.get(key);
				var teamPlayers = getTeamPlayers(gameParticipants);
				return game.getTeamWinner(key, gameParticipants, teamPlayers);
			}).collect(Collectors.toSet());
	}

	private Map<String, Map<String, List<FileInputData>>> groupGameTeams(Map<String, List<FileInputData>> result) {
		return result.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey,
				entry -> entry.getValue().stream()
					.collect(Collectors.groupingBy(FileInputData::getTeamName, Collectors.toList()))
			));
	}

	private Map<String, Set<Player>> getTeamPlayers(Map<String, List<FileInputData>> teams) {
		return teams.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey,
				entry -> entry.getValue().stream()
					.map(player -> Player.builder()
						.gameName(player.getGameName())
						.playerName(player.getPlayerName())
						.nickName(player.getNickName())
						.ratingPoints(player.getScoredPoints())
						.build())
					.collect(Collectors.toSet())));
	}
}
