package com.honours.game.desktop;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.honours.elasticsearch.ElasticCaseDB;
import com.honours.game.HonoursGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        int width = Lwjgl3ApplicationConfiguration.getDisplayMode().width - 200;
        int height = Lwjgl3ApplicationConfiguration.getDisplayMode().height - 100;
        config.setResizable(false);
    	config.setWindowedMode(width, height);
    	config.setDecorated(true);
    	config.useVsync(true);		
    	config.setTitle("RavTech");
    	ElasticCaseDB.getCases();
		new Lwjgl3Application(new HonoursGame(), config);
	}
}
