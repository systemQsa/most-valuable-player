package com.mvp.mostvaluableplayer.command;

import com.mvp.mostvaluableplayer.dto.FileInputData;
import com.mvp.mostvaluableplayer.entity.Player;
import com.mvp.mostvaluableplayer.entity.Team;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Command {

	Team getTeamWinner(String gameName, Map<String, List<FileInputData>> teams, Map<String, Set<Player>> teamPlayers);

	Map<String, Player> getPlayers(String gameName, Map<String, List<FileInputData>>data);
}
