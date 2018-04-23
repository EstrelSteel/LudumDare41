package com.estrelsteel.ld41.actors;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Shadow extends Actor {

	public Shadow(String name, Rectangle loc) {
		super(name, loc);

		getAnimations().add(new Animation("shadow", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/shadows.png", QuickRectangle.location(0 * 16, 0, 16, 16)));
		
		this.setHideOffScreen(false);
	}

}
