package com.fiveyoukais.alen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fiveyoukais.alen.Alen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 400;
        config.height = 208;
		config.title = "Alen";
		config.pauseWhenBackground = true;
		new LwjglApplication(new Alen(), config);
	}
}

