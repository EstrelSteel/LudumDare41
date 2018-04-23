package com.estrelsteel.ld41.actors;

import java.util.ArrayList;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class DebrisInAir extends Actor {

	private static ArrayList<Animation> animations;
	
	public DebrisInAir(String name, Rectangle loc) {
		super(name, loc);

//		getAnimations().add(new Animation("grass", 0));
//		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(0 * 16, 0, 16, 16)));
//		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(1 * 16, 0, 16, 16)));
//		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(2 * 16, 0, 16, 16)));
//		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(3 * 16, 0, 16, 16)));
//		getAnimations().get(0).setMaxWaitTime(3);
//		getAnimations().add(new Animation("dirt", 1));
//		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(4 * 16, 0, 16, 16)));
//		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(5 * 16, 0, 16, 16)));
//		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(6 * 16, 0, 16, 16)));
//		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(7 * 16, 0, 16, 16)));
//		getAnimations().get(1).setMaxWaitTime(3);
		
		this.setHideOffScreen(false);
	}
	
	public static void loadAnimations() {
		animations = new ArrayList<Animation>();
		animations.add(new Animation("grass", 0));
		animations.get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(0 * 16, 0, 16, 16)));
		animations.get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(1 * 16, 0, 16, 16)));
		animations.get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(2 * 16, 0, 16, 16)));
		animations.get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(3 * 16, 0, 16, 16)));
		animations.get(0).setMaxWaitTime(3);
		animations.add(new Animation("dirt", 1));
		animations.get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(4 * 16, 0, 16, 16)));
		animations.get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(5 * 16, 0, 16, 16)));
		animations.get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(6 * 16, 0, 16, 16)));
		animations.get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/debris.png", QuickRectangle.location(7 * 16, 0, 16, 16)));
		animations.get(1).setMaxWaitTime(3);
	}
	
	public ArrayList<Animation> getAnimations() {
		return animations;
	}

}
