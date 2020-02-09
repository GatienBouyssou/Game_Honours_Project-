package com.honours.game.sprites.spells;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.honours.game.sprites.spells.spellBehaviours.LinearSpell;
import com.honours.game.sprites.spells.spellBehaviours.StaticSpell;
import com.honours.game.sprites.spells.spellEffects.Burn;
import com.honours.game.sprites.spells.spellEffects.Invisibility;
import com.honours.game.sprites.spells.spellEffects.ManaChanger;
import com.honours.game.sprites.spells.spellEffects.Stun;

public class SpellCreator {
	private List<Spell> listOfSpellCreated;
	
	public SpellCreator(TextureAtlas textureAtlas) {
		listOfSpellCreated = new ArrayList<Spell>();
		createSpells(textureAtlas);
	}
	
	private void createSpells(TextureAtlas textureAtlas) {
		TextureRegion region = new TextureRegion(textureAtlas.findRegion("autoAttack"));
		Spell spell = new Spell(Spell.MEDIUM_RANGE, Spell.SHORT_COULDOWN);
		spell.setSpellBehaviour(new LinearSpell(region, 0.25f, 0.25f, LinearSpell.TURTLE_SPEED, false));
		spell.setEffect(new ManaChanger(50));
		listOfSpellCreated.add(spell);
		
		region = new TextureRegion(textureAtlas.findRegion("Bush"));
		spell = new Spell(Spell.MEDIUM_RANGE, Spell.LONG_COULDOWN);
		spell.setSpellBehaviour(new StaticSpell(region, 2, 2, Spell.MEDIUM_COULDOWN, true));
		spell.setEffect(new Invisibility());
		listOfSpellCreated.add(spell);
	}

	public List<Spell> getListOfSpellCreated() {
		return listOfSpellCreated;
	}

	public void setListOfSpellCreated(List<Spell> listOfSpellCreated) {
		this.listOfSpellCreated = listOfSpellCreated;
	}
	
	public static List<Spell> duplicateListOfSpell(List<Spell> spells) {
		List<Spell> newSpells = new ArrayList<>();
		for (Spell spell : spells) {
			Spell newSpell = new Spell(spell.getRange(), spell.getCouldown());
			newSpell.setSpellBehaviour(spell.getSpellBehaviour().clone());
			newSpell.setEffect(spell.getEffect());
			newSpells.add(newSpell);
		}
		return newSpells;	
	}
	
}
