package com.target.game.beans;

public class ScoreCard {

	private Chance[] chances;
	private int totalStrike;
	private int totalSpare;
	private int totalScore;

	public ScoreCard() {
		this.chances = new Chance[11];
		for (int i = 0; i < this.chances.length; i++) {
			this.chances[i] = new Chance();
		}
	}

	public Chance[] getChances() {
		return chances;
	}

	public int getTotalStrike() {
		return totalStrike;
	}

	public void setTotalStrike(int totalStrike) {
		this.totalStrike = totalStrike;
	}

	public int getTotalSpare() {
		return totalSpare;
	}

	public void setTotalSpare(int totalSpare) {
		this.totalSpare = totalSpare;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

}
