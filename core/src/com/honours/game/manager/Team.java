package com.honours.game.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.honours.game.sprites.Player;
import com.honours.game.sprites.spells.Spell;
import com.honours.game.tools.PlayerSightManager;

import box2dLight.RayHandler;

public class Team {
	private List<Integer> listOfPlayersAlive;
	private Map<Integer, Player> mapOfPlayers;
	
	public static List<String> nameRegions = Arrays.asList("", "Heal", "Hurt");
	
	private int teamId;
	private RayHandler rayHandler;
	private World world;
	
	
	public Team(int teamId, World world, Viewport viewport) {
		this.teamId = teamId;
		this.world = world;
		listOfPlayersAlive = new ArrayList<Integer>();
		mapOfPlayers = new HashMap<>();
		
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(.5f);
		rayHandler.setCombinedMatrix(viewport.getCamera().combined,0,0, viewport.getWorldWidth(),viewport.getWorldHeight());
		
	}
	
	public boolean hasLost() {
		if (listOfPlayersAlive.size() == 0) {
			return true;
		}
		return false;
	}
	
	public void updatePlayers(float deltaTime) {		
		for (int i = 0; i < listOfPlayersAlive.size(); i++) {
			mapOfPlayers.get(listOfPlayersAlive.get(i)).update(deltaTime);
		}
	}
	
		
	public void addNewPlayer(Vector2 spawnPoint, TextureAtlas atlas, String TypeOfPlayer, Array<Spell> listOfSpells) {
		List<TextureRegion> regions = new ArrayList<TextureRegion>();
		for (String nameRegion : nameRegions) {
			regions.add(new TextureRegion(atlas.findRegion("player"+TypeOfPlayer+nameRegion)));
		}
		Player player = new Player(world, spawnPoint, regions, listOfSpells, rayHandler, teamId, listOfPlayersAlive.size());
		listOfPlayersAlive.add(player.getId());
		mapOfPlayers.put(player.getId(), player);
	}
	
	public void updatePointOfView() {
		rayHandler.update();		
	}
	
	public void draw(Batch batch) {
		for (int playerId : listOfPlayersAlive) {
			mapOfPlayers.get(playerId).draw(batch);
		}
	}

	public void renderLight() {
		rayHandler.render();
	}
	
	public void drawPlayerAndSpellsIfInLight(SpriteBatch batch, Team team) {
		for (int playerId : listOfPlayersAlive) {
			mapOfPlayers.get(playerId).drawPlayerAndSpellsIfInLight(batch, team);
		}
	}
	
	public boolean detectsBody(Vector2 bodyPosition) {
		for (Integer playerId : listOfPlayersAlive) {
			if (PlayerSightManager.isBodySeen(world, mapOfPlayers.get(playerId).getBodyPosition(), bodyPosition)) 
				return true;
		}
		return false;
	}
	
	public List<Integer> getListOfPlayersAlive() {
		return listOfPlayersAlive;
	}
	
	public Player getPlayer(int index) {
		return mapOfPlayers.get(listOfPlayersAlive.get(index));
	}
	
	public int getId() {
		return teamId;
	}

	public RayHandler getPointOfView() {
		return rayHandler;
	}
	
	public void playerCastSpell(int playerId, int spellIndex, Vector2 destination) {
		mapOfPlayers.get(playerId).castSpell(spellIndex, destination);
	}
	
	public void playerMoveTo(int playerId, float x, float y) {
		mapOfPlayers.get(playerId).moveTo(new Vector2(x, y));
	}
	
	public int nbrOfPlayers() {
		return listOfPlayersAlive.size();
	}

	public void dispose() {
		rayHandler.dispose();

	}

	public void removePlayer(Integer playerId) {
		listOfPlayersAlive.remove(playerId);
	}
}
