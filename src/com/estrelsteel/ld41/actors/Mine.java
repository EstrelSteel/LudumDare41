package com.estrelsteel.ld41.actors;

import java.util.ArrayList;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.collide.Collision;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Mine extends Actor {

	private static final double radius = 16;
	private static ArrayList<Animation> animations;
	private int aiLoc;
	
	public Mine(String name, Rectangle loc) {
		super(name, loc);
		
		setCollision(new Collision(true, new RectangleCollideArea(QuickRectangle.location(loc.getX() - radius, loc.getY() - radius, loc.getWidth() + radius * 2, loc.getHeight() + radius * 2))));
		setHideOffScreen(false);
		aiLoc = -1;
	}
	
	public static void loadAnimations() {
		animations = new ArrayList<Animation>();
		animations.add(new Animation("mine_blue", 0));
		animations.get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(0 * 16, 0 * 16, 16, 16)));
		animations.get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(1 * 16, 0 * 16, 16, 16)));
		animations.get(0).setMaxWaitTime(60);
		animations.add(new Animation("mine_orange", 1));
		animations.get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(0 * 16, 1 * 16, 16, 16)));
		animations.get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(1 * 16, 1 * 16, 16, 16)));
		animations.get(1).setMaxWaitTime(60);
		animations.add(new Animation("mine_destroy_1", 2));
		animations.get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(2 * 16, 0 * 16, 16, 16)));
		animations.get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(3 * 16, 0 * 16, 16, 16)));
		animations.get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(2 * 16, 1 * 16, 16, 16)));
		animations.get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(3 * 16, 1 * 16, 16, 16)));
		animations.get(2).setMaxWaitTime(Integer.MAX_VALUE);
		animations.add(new Animation("mine_destroy_2", 3));
		animations.get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(0 * 16, 2 * 16, 16, 16)));
		animations.get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(1 * 16, 2 * 16, 16, 16)));
		animations.get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(0 * 16, 3 * 16, 16, 16)));
		animations.get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(1 * 16, 3 * 16, 16, 16)));
		animations.get(3).setMaxWaitTime(Integer.MAX_VALUE);
		animations.add(new Animation("mine_active", 4));
		animations.get(4).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/mines.png", QuickRectangle.location(2 * 16, 2 * 16, 16, 16)));
		animations.get(4).setMaxWaitTime(Integer.MAX_VALUE);
	}
	
	public ArrayList<Animation> getAnimations() {
		return animations;
	}
	
	public int getAILocation() {
		return aiLoc;
	}
	
	public void setAILocation(int aiLoc) {
		this.aiLoc = aiLoc;
	}

	
}
