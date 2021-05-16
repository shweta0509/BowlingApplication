package com.target.game.interfaces;

import java.util.List;

import com.target.game.beans.Lane;
import com.target.game.beans.Player;

public interface Game {

	public List<Lane> startGame(int totalPlayers, List<String> playerNames, int totalLanes);

	public void getScoreBoard(Lane lane);

	public void getScoreCardByPlayer(Player player);

	public void bowl(Player player, int throw1, int throw2, int setCount);

	public void bowlExtra(Player player, int throw1, int setCount);

}
