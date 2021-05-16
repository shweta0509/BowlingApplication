package com.target.game.beans;

public class Player {

	private String name;
	private ScoreCard scoreCard;

	public Player(String name) {
		this.name = name;
		this.scoreCard = new ScoreCard();
	}

	public String getName() {
		return name;
	}

	public ScoreCard getScoreCard() {
		return scoreCard;
	}

}