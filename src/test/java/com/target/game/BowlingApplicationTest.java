package com.target.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.target.game.beans.Lane;
import com.target.game.beans.Player;
import com.target.game.constant.BowlingConstants;
import com.target.game.exception.BowlingException;
import com.target.game.impl.ConfigImpl;
import com.target.game.impl.GameImpl;
import com.target.game.interfaces.Config;
import com.target.game.interfaces.Game;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GameImpl.class, ConfigImpl.class })
public class BowlingApplicationTest {

	@Autowired
	private Game game;

	private int totalPlayers;

	private List<String> playerNames;

	private Player[] players;

	private List<Lane> lanes;

	private int totalLanes;

	@Autowired
	private Config con;

	@Before
	public void setUp() {
		totalPlayers = 1;
		playerNames = new ArrayList<String>();
		playerNames.add("radhe");
		totalLanes = 1;
	}

	@Test
	public void gutterGame() {
		lanes = game.startGame(totalPlayers, playerNames, totalLanes);
		players = lanes.get(0).getPlayers();
		for (int i = 0; i < BowlingConstants.MAX_PINS; i++) {
			game.bowl(players[0], 0, 0, i);
		}
		game.getScoreBoard(lanes.get(0));
		assertEquals(players[0].getScoreCard().getTotalScore(), 0);
	}

	@Test
	public void allStrikeGame() {
		lanes = game.startGame(totalPlayers, playerNames, totalLanes);
		players = lanes.get(0).getPlayers();
		for (int i = 0; i < BowlingConstants.MAX_PINS; i++) {
			game.bowl(players[0], 10, 10, i);
		}
		game.bowlExtra(players[0], 10, 10);

		game.getScoreBoard(lanes.get(0));
		assertEquals(players[0].getScoreCard().getTotalScore(), 220);
	}

	@Test
	public void allSpareGame() {
		lanes = game.startGame(totalPlayers, playerNames, totalLanes);
		players = lanes.get(0).getPlayers();
		for (int i = 0; i < BowlingConstants.MAX_PINS; i++) {
			game.bowl(players[0], 6, 4, i);
		}
		game.bowlExtra(players[0], 5, 10);

		game.getScoreBoard(lanes.get(0));
		assertEquals(players[0].getScoreCard().getTotalScore(), 155);
	}

	@Test
	public void multiPlayerGame() {
		totalPlayers = 3;
		playerNames.add("player 1");
		playerNames.add("player 2");
		lanes = game.startGame(totalPlayers, playerNames, totalLanes);
		players = lanes.get(0).getPlayers();
		for (int i = 0; i < BowlingConstants.MAX_PINS; i++) {
			for (int j = 0; j < BowlingConstants.MAX_PLAYERS; j++) {
				game.bowl(players[j], 2, 5, i);
			}
		}

		game.getScoreBoard(lanes.get(0));
		assertEquals(players[0].getScoreCard().getTotalScore(), 70);
	}

	@Test(expected = BowlingException.class)
	public void playesMoreThanLanesGame() {
		totalPlayers = con.getTotalPlayers(BowlingConstants.TOTAL_PLAYERS);
		playerNames = con.getPlayerNames(BowlingConstants.PLAYER_NAMES);
		lanes = game.startGame(totalPlayers, playerNames, totalLanes);
	}

}
