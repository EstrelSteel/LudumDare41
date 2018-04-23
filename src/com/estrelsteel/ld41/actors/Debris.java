package com.estrelsteel.ld41.actors;

import java.awt.Graphics2D;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.world.FrozenWorld;

public class Debris extends Ball {

	private double h;
	private double vh;
	private DebrisInAir debris;
	
	public Debris(String name, Rectangle loc) {
		super(name, loc);
		
		getAnimations().remove(0);
		getAnimations().add(new Animation("grass", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/shadows.png", QuickRectangle.location(0 * 16, 0, 16, 16)));
		
		h = 1;
		vh = 0;
		
		debris = new DebrisInAir("image", null);
		updateDebrisInAir();
		setSortable(false);
	}
	
	public Graphics2D render(Graphics2D ctx, FrozenWorld world) {
		super.render(ctx, world);
		debris.render(ctx, world);
		return ctx;
	}
	
	public Graphics2D simpleRender(Graphics2D ctx, FrozenWorld world) {
		super.simpleRender(ctx, world);
		debris.simpleRender(ctx, world);
		return ctx;
	}
	
	public DebrisInAir getDebrisInAir() {
		return debris;
	}
	
	public double getHeight() {
		return h;
	}
	
	public double getVelocityH() {
		return vh;
	}
	
	public DebrisInAir updateDebrisInAir() {
		debris.setLocation(QuickRectangle.location(getLocation().getX(), getLocation().getY() - h - getLocation().getWidth() + (getLocation().getHeight() / 2), getLocation().getWidth(), getLocation().getWidth()));
		return debris;
	}
	
	public Debris updateVelocityH(double fh) {
		if(h > 0) {
			vh = vh - fh;
		}
		if(h <= 0) {
			h = 0;
			vh = 0;
		}
		return this;
	}
	
	public Debris updateLocation(double fx, double fy, double fh) {
		updateVelocityH(fh);
		h = h + fh;
		return (Debris) super.updateLocation(fx, fy);
	}
	
	public Debris updateHeight(double fh) {
		updateVelocityH(fh);
		return setHeight(getHeight() + vh);
	}
	
//	public Debris updateLocation(double f, double fh) {
//		updateVelocityH(fh);
//		return (Debris) super.updateLocation(f);
//	}
//	
//	public Debris updateLocation(double fh) {
//		updateVelocityH(fh);
//		return (Debris) super.updateLocation(0);
//	}

	public Debris applyVelocity(double r, double v, double vh) {
		return (Debris) setVelocityH(getVelocityH() + vh).applyVelocity(r, v);
	}
	
	public Debris setHeight(double h) {
		this.h = h;
		return this;
	}
	
	public Debris setVelocityH(double vh) {
		this.vh = vh;
		return this;
	}
	
	public void setDebrisInAir(DebrisInAir debris) {
		this.debris = debris;
	}

}
