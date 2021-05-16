package com.target.game.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import com.target.game.beans.Chance;
import com.target.game.beans.Lane;
import com.target.game.beans.Player;
import com.target.game.beans.ScoreCard;
import com.target.game.constant.BowlingConstants;
import com.target.game.exception.BowlingException;
import com.target.game.interfaces.Game;

public class GameImpl implements Game {

	/**
	 * This method assigns players to lanes. Every lane can have maximum 3 players
	 * 
	 * @return Returns list of all the lanes which have been allocated for this game
	 */

	@Override
	public List<Lane> startGame(int totalPlayers, List<String> playerNames, int totalLanes) {
		List<Lane> lanes = new ArrayList<>();

		int playerCount = 0;
		int laneId = 1;
		while (totalPlayers > 0 && laneId <= totalLanes) {
			if (totalPlayers == 2) {
				playerCount = 2;
				totalPlayers -= 2;
			} else if (totalPlayers == 1) {
				playerCount = 1;
				totalPlayers -= 1;
			} else {
				playerCount = 3;
				totalPlayers -= BowlingConstants.MAX_PLAYERS;
			}

			Lane lane = new Lane(laneId, playerCount, generatePlayersList(playerCount, playerNames, laneId));
			lanes.add(lane);
			laneId++;
		}

		if (laneId > totalLanes && totalPlayers > 0) {
			throw new BowlingException("All lanes are full");
		}

		return lanes;
	}

	private Player[] generatePlayersList(int playerCount, List<String> playerNames, int laneId) {
		Player[] players = new Player[playerCount];
		int startIndex = (laneId - 1) * BowlingConstants.MAX_PLAYERS;
		for (int i = startIndex; i < playerCount + startIndex; i++) {
			players[i % playerCount] = new Player(playerNames.get(i));
		}
		return players;
	}

	/**
	 * This method displays the entire score board for all the players in that lane.
	 */
	@Override
	public void getScoreBoard(Lane lane) {
		System.out.println();
		System.out.println("Lane Number: " + lane.getId());

		TreeMap<Integer, List<String>> winners = new TreeMap<>(Collections.reverseOrder());

		for (Player player : lane.getPlayers()) {
			getScoreCardByPlayer(player);
			int totalScore = player.getScoreCard().getTotalScore();
			List<String> winner = new ArrayList<>();
			if (winners.containsKey(totalScore)) {
				winner = winners.get(totalScore);
			}
			winner.add(player.getName());
			winners.put(totalScore, winner);
		}
		System.out.println("Winner: " + winners.get(winners.firstKey()));
		System.out.println();
	}

	/**
	 * This method displays score board for a particular player
	 */
	@Override
	public void getScoreCardByPlayer(Player player) {
		System.out.printf("%12s: ", player.getName());
		Chance[] chances = player.getScoreCard().getChances();
		for (int i = 0; i < chances.length; i++) {
			if (i == chances.length - 1) {
				System.out.printf("|~ %02d|%02d ~|", chances[i].getThrow1(), chances[i].getThrow2());
			} else {
				System.out.printf("| %02d|%02d |", chances[i].getThrow1(), chances[i].getThrow2());
			}
		}
		System.out.printf(" => %03d  ", player.getScoreCard().getTotalScore());
		System.out.printf("Total Spare: %02d ", player.getScoreCard().getTotalSpare());
		System.out.printf("Total Strike: %02d%n", player.getScoreCard().getTotalStrike());
	}

	/**
	 * This method is used to throw bowls
	 */
	@Override
	public void bowl(Player player, int throw1, int throw2, int setCount) {
		if (throw1 == 10) {
			calculateSetScore(throw1, player, setCount);
		} else {
			calculateSetScore(throw1, throw2, player, setCount);
		}
	}

	/**
	 * This method is used to throw extra bowl in last set
	 */
	@Override
	public void bowlExtra(Player player, int throw1, int setCount) {
		calculateSetScore(throw1, player, setCount);
	}

	private void calculateSetScore(int throw1, Player player, int setCount) {
		calculateSetScore(throw1, -1, player, setCount);
	}

	private void calculateSetScore(int throw1, int throw2, Player player, int setCount) {
		boolean strike = false;
		boolean spare = false;
		int score = 0;

		ScoreCard scoreCard = player.getScoreCard();

		if (throw1 == 10 && throw2 == -1) { // strike
			score = throw1 + 10;
			strike = true;
			scoreCard.setTotalStrike(scoreCard.getTotalStrike() + 1);
		} else if (throw1 + throw2 == 10) { // spare
			score = throw1 + throw2 + 5;
			spare = true;
			scoreCard.setTotalSpare(scoreCard.getTotalSpare() + 1);
		} else {
			if (throw2 == -1) {
				throw2 = 0;
			}
			score = throw1 + throw2;
		}

		scoreCard.setTotalScore(scoreCard.getTotalScore() + score);

		Chance chance = new Chance(throw1, throw2, strike, spare, score);

		player.getScoreCard().getChances()[setCount] = chance;
	}

}
