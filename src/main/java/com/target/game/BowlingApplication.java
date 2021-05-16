package com.target.game;

import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.target.game.beans.Lane;
import com.target.game.beans.Player;
import com.target.game.constant.BowlingConstants;
import com.target.game.impl.GameImpl;
import com.target.game.interfaces.Config;
import com.target.game.interfaces.Game;

public class BowlingApplication {

	public static void main(String[] args) {

		Config con = (Config) getBean("configImpl");
		Game game = (GameImpl) getBean("gameImpl");

		createAndInsertConfig(con);

		int totalLanes = con.getTotalLanes(BowlingConstants.TOTAL_LANES);
		int totalPlayers = con.getTotalPlayers(BowlingConstants.TOTAL_PLAYERS);
		List<String> playerNames = con.getPlayerNames(BowlingConstants.PLAYER_NAMES);

		List<Lane> lanes = game.startGame(totalPlayers, playerNames, totalLanes);

		for (int i = 0; i < lanes.size(); i++) {
			Lane lane = lanes.get(i);
			bowlForEveryPlayer(game, lane.getPlayers(), lane.getPlayerCount(), lane);
			game.getScoreBoard(lane);
		}
	}

	private static void bowlForEveryPlayer(Game game, Player[] players, int playerCount, Lane lane) {
		for (int i = 0; i < BowlingConstants.MAX_PINS; i++) {
			for (int j = 0; j < playerCount; j++) {
				int throw1 = pinDownGenerator(BowlingConstants.MAX_PINS);
				int throw2 = pinDownGenerator(BowlingConstants.MAX_PINS - throw1);
				game.bowl(players[j], throw1, throw2, i);
				if (i == BowlingConstants.MAX_PINS - 1 && (throw1 == 10 || throw1 + throw2 == 10)) { // final set
					int throw3 = pinDownGenerator(BowlingConstants.MAX_PINS);
					game.bowlExtra(players[j], throw3, i + 1);
				}
//				game.getScoreCardByPlayer(players[j]);
			}
//			game.getScoreBoard(lane);
		}
	}

	private static int pinDownGenerator(int max) {
		return new Random().nextInt(max + 1);
	}

	public static Object getBean(String beanName) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		return context.getBean(beanName);
	}

	private static void createAndInsertConfig(Config con) {
//		con.createTable();
//		con.insertConfig(BowlingConstants.TOTAL_LANES, "6");
//		con.insertConfig(BowlingConstants.TOTAL_PLAYERS, "5");
//		con.insertConfig(BowlingConstants.PLAYER_NAMES, "ramesh,rahul,pooja,lily,kamal");
		con.getAllConfigs();
	}
}
