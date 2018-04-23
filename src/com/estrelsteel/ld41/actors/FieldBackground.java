package com.estrelsteel.ld41.actors;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class FieldBackground extends Actor {

	public FieldBackground(Rectangle loc) {
		super("fieldbackground", loc);
		getAnimations().add(new Animation("field", 0));
		getAnimations().get(0).getFrames().add(new Image(Engine2.devPath + "/res/img/field_pixel.png"));
		getAnimations().add(new Animation("stands_ew", 1));
		getAnimations().get(1).getFrames().add(new Image(Engine2.devPath + "/res/img/stands_ew.png"));
		getAnimations().add(new Animation("stands_n", 2));
		getAnimations().get(2).getFrames().add(new Image(Engine2.devPath + "/res/img/stands_n.png"));
		setSortable(false);
	}

}
