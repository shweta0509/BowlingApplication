package com.target.game.interfaces;

import java.util.List;

public interface Config {

	public void createTable();

	public void insertConfig(String name, String value);

	public void updateConfig(String name, String value);

	public void getAllConfigs();

	public List<String> getPlayerNames(String playerNames);

	public int getTotalLanes(String name);

	public int getTotalPlayers(String totalPlayers);

}
