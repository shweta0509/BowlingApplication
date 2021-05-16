package com.target.game.beans;

public class Chance {

	private int throw1;
	private int throw2;
	private boolean strike;
	private boolean spare;
	private int score;

	public Chance() {
		this.throw1 = 0;
		this.throw2 = 0;
		this.strike = false;
		this.spare = false;
		this.score = 0;
	}

	public Chance(int throw1, int throw2, boolean strike, boolean spare, int score) {
		this.throw1 = throw1;
		this.throw2 = throw2;
		this.strike = strike;
		this.spare = spare;
		this.score = score;
	}

	public int getThrow1() {
		return throw1;
	}

	public int getThrow2() {
		return throw2;
	}

	public boolean isStrike() {
		return strike;
	}

	public boolean isSpare() {
		return spare;
	}

	public int getScore() {
		return score;
	}

}
