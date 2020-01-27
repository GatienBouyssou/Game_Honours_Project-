// 
// Decompiled by Procyon v0.5.36
// 

package com.honours.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.honours.game.screens.TitleScreen;
import com.honours.game.sprites.spells.Spell;
import com.honours.game.sprites.spells.SpellCreator;

public class HonoursGame extends Game 
{
    private SpriteBatch batch;
    public final static short WORLD_BIT = 1;
    public final static short PLAYER_BIT = 2;
    public final static short SPELL_BIT = 4;

    public static final int VIRTUAL_WIDTH = 16;
    public static final int VIRTUAL_HEIGHT = 16;
    public static final int FRAME_WIDTH = 32;
    private List<Spell> ListOfSpellsAvailable;
    
    
    public void create() {    	
        this.batch = new SpriteBatch();
        setScreen(new TitleScreen(this));
        SpellCreator spellCreator = new SpellCreator();
        ListOfSpellsAvailable = spellCreator.getListOfSpellCreated();
        
    }
    
    public void dispose() {
    	super.dispose();
        this.batch.dispose();
    }
    
    public void render() {
    	super.render();
    }
    
    public SpriteBatch getBatch() {
        return this.batch;
    }

	public List<Spell> getListOfSpellsAvailable() {
		return ListOfSpellsAvailable;
	}

	public void setListOfSpellsAvailable(List<Spell> listOfSpellsAvailable) {
		ListOfSpellsAvailable = listOfSpellsAvailable;
	}
       
    	
}
