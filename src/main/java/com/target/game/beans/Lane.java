package com.target.game.beans;

public class Lane {

	private int id;
	private Player[] players;
	private int playerCount;

	public Lane(int id, int playerCount, Player[] players) {
		this.id = id;
		this.players = players;
		this.playerCount = playerCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

}
