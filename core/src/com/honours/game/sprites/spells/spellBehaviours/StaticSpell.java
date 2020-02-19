package com.honours.game.sprites.spells.spellBehaviours;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.honours.game.HonoursGame;
import com.honours.game.sprites.Player;
import com.honours.game.tools.BodyHelper;

public class StaticSpell extends SpellGraphicBehaviour {

	private float activityTime;
	private float currentLifeTime = 0;
	private boolean isSensor;
	private boolean isOpaque;
	
	public StaticSpell(TextureRegion region, float activityTime, boolean isSensor, boolean isOpaque) {
		super(region);
		this.activityTime = activityTime;
		this.isSensor = isSensor;
		this.isOpaque = isOpaque;
	}
	
	public StaticSpell(TextureRegion region, float scaleX, float scaleY, float activityTime, boolean isSensor, boolean isOpaque) {
		super(region, scaleX, scaleY);
		this.activityTime = activityTime;
		this.isSensor = isSensor;
		this.isOpaque = isOpaque;
	}
	
	public StaticSpell(StaticSpell staticSpell) {
		super(staticSpell);
		this.activityTime = staticSpell.getActivityTime();
		this.isSensor = staticSpell.isSensor();
		this.isOpaque = staticSpell.isOpaque();
	}
	
	@Override
	public void castSpell(Player player, World world, Vector2 destination) {
		Vector2 bodyPosition = player.getBodyPosition();
		if (!spell.playerIsInRange(bodyPosition, destination)) {
			Vector2 vectorDir = new Vector2(destination.x - bodyPosition.x, destination.y - bodyPosition.y);
			vectorDir.nor();
			vectorDir.x = vectorDir.x * spell.getRange() + bodyPosition.x;
			vectorDir.y = vectorDir.y * spell.getRange() + bodyPosition.y;
			createSpell(player, world, vectorDir);
		} else {
			createSpell(player, world, destination);
		}
	}
	
	@Override
	protected void createSpell(Player player, World world, Vector2 destination) {
		this.world = world;
		createBody(world, destination);
		Vector2 bodyPosition = player.getBodyPosition();
		Vector2 vec = new Vector2(-(bodyPosition.y - destination.y), bodyPosition.x - destination.x);
		float angle = vec.angle();
		setRotation(angle);
		body.setTransform(body.getPosition(), (float) Math.toRadians(angle));
		setOrigin(widthSprite/2, heightSprite/2);
		setBounds(destination.x - widthSprite/2, destination.y-heightSprite/2, widthSprite, heightSprite);
	}
	
	@Override
	protected void createBody(World world, Vector2 position) {
		this.body = BodyHelper.createBody(world, position, BodyType.StaticBody);
		Filter filter;
		if (isOpaque)
			filter = BodyHelper.createFilter(HonoursGame.SPELL_BIT, (short)0,(short)(HonoursGame.PLAYER_BIT | HonoursGame.SPELL_BIT | HonoursGame.LIGHT_BIT));
		else
			filter = BodyHelper.createFilter(HonoursGame.SPELL_BIT, (short)0,(short)(HonoursGame.PLAYER_BIT | HonoursGame.SPELL_BIT));
		BodyHelper.createFixture(body, spell, BodyHelper.createPolygonShapeAsBox(widthSprite, heightSprite), filter, isSensor);
	}

	@Override
	public void update(float deltaTime) {
		currentLifeTime += deltaTime;
		if(currentLifeTime >= activityTime) {
			destroySpell();
			currentLifeTime = 0;
		}
	}
	
	public float getActivityTime() {
		return activityTime;
	}

	@Override
	public boolean isDestroyedWhenTouch(Player player, int teamId) {
		return false;
	}
	
	public boolean isSensor() {
		return isSensor;
	}

	@Override
	public StaticSpell clone() {
		return new StaticSpell(this);
	}
	
	public boolean isOpaque() {
		return isOpaque;
	}
	
	@Override
	public String toString() {
		return "This spell won't move during " + activityTime + "s unless it gets destroyed by another effect.";
	}
}
