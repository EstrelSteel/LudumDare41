package com.estrelsteel.ld41.actors;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Ball extends Actor {
	
	private double vx;
	private double vy;

	public Ball(String name, Rectangle loc) {
		super(name, loc);
		
		getAnimations().add(new Animation("BALL_ROLL", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/ball.png", QuickRectangle.location(0 * 16, 0, 32, 32)));
//		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/ball.png", QuickRectangle.location(1 * 16, 0, 64, 64)));
//		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/ball.png", QuickRectangle.location(2 * 16, 0, 64, 64)));
		getAnimations().get(0).setMaxWaitTime(5);
		setHideOffScreen(false);
	}
	
	public double getVelocityX() {
		return vx;
	}
	
	public double getVelocityY() {
		return vy;
	}
	
	public Rectangle getFutureLocation() {
		return QuickRectangle.location(getLocation().getX() + vx, getLocation().getY() - vy, getLocation().getWidth(), getLocation().getHeight());
	}
	
	public Ball updateLocation(double fx, double fy) {
		setLocation(getFutureLocation());
		if(vx != 0) {
			double fvx = vx - fx;
			if((vx < 0 && fvx > 0) || (vx > 0 && fvx < 0)) {
				fvx = 0;
			}
			vx = fvx;
		}
		if(vy != 0) {
		double fvy = vy - fy;
			if((vy < 0 && fvy > 0) || (vy > 0 && fvy < 0)) {
				fvy = 0;
			}
			vy = fvy;
		}
		return this;
	}
	
	public Ball updateLocation(double f) {
		double r = Math.atan2(vy, vx);
		double fx = f * Math.cos(r);
		double fy = f * Math.sin(r);
		return updateLocation(fx, fy);
	}
	
	public Ball updateLocation() {
		return updateLocation(0);
	}

	public Ball applyVelocity(double r, double v) {
		vx = vx + v * Math.cos(r);
		vy = vy + v * Math.sin(r);
		 return this;
	}
	
	public Ball setVelocityX(double vx) {
		this.vx = vx;
		return this;
	}
	
	public Ball setVelocityY(double vy) {
		this.vy = vy;
		return this;
	}
	
	public Ball setVelocity(double r, double v) {
		vx = 0;
		vy = 0;
		return applyVelocity(r, v);
	}
}
