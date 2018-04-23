package com.estrelsteel.ld41.hud;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class HudItem extends Actor {

	public HudItem(Rectangle loc) {
		super("scoreboard", loc);
		
		getAnimations().add(new Animation("scoreboard", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(0, 0, 123, 22)));
		getAnimations().add(new Animation("info", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(0, 22, 123, 22)));
		getAnimations().add(new Animation("title", 2));
		getAnimations().get(2).getFrames().add(new Image(Engine2.devPath + "/res/img/title.png"));
		getAnimations().add(new Animation("blue_win", 3));
		getAnimations().get(3).getFrames().add(new Image(Engine2.devPath + "/res/img/blue_win.png"));
		getAnimations().add(new Animation("orange_win", 4));
		getAnimations().get(4).getFrames().add(new Image(Engine2.devPath + "/res/img/orange_win.png"));
		getAnimations().add(new Animation("draw", 5));
		getAnimations().get(5).getFrames().add(new Image(Engine2.devPath + "/res/img/draw.png"));
		getAnimations().add(new Animation("tutorial", 6));
		getAnimations().get(6).getFrames().add(new Image(Engine2.devPath + "/res/img/tutorial.png"));
	}

}
