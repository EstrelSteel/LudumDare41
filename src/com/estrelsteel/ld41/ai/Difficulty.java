package com.estrelsteel.ld41.ai;

public enum Difficulty {
	TUTORIAL(0, 0), EASY(2, 3), MEDIUM(4, 6), HARD(6, 8);
	
	int minesPerRound;
	int maxMines;
	
	Difficulty(int minesPerRound, int maxMines) {
		this.minesPerRound = minesPerRound;
		this.maxMines = maxMines;
	}
	
	public int getMinesPerRound() {
		return minesPerRound;
	}
	
	public int getMaxMines() {
		return maxMines;
	}
}
