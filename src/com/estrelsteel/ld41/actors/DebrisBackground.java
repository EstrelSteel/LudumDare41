package com.estrelsteel.ld41.actors;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class DebrisBackground extends Actor {

	public DebrisBackground(Rectangle loc) {
		super("debrisbackground", loc);
		getAnimations().add(new Animation("background", 0));
		getAnimations().get(0).getFrames().add(new Image(Engine2.devPath + "/res/img/blank.png"));
		setSortable(false);
	}

}
