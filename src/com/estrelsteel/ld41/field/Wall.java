package com.estrelsteel.ld41.field;

import java.awt.Graphics2D;

import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.world.FrozenWorld;

public class Wall implements Renderable {
	
	private Rectangle loc;
	private int id;
	
	public Wall(int id, Rectangle loc) {
		this.id = id;
		this.loc = loc;
	}
	
	public int getID() {
		return id;
	}

	@Override
	public Rectangle getLocation() {
		return loc;
	}

	@Override
	public Graphics2D render(Graphics2D ctx, FrozenWorld world) {
		return ctx;
	}

	@Override
	public Graphics2D simpleRender(Graphics2D ctx, FrozenWorld world) {
		return ctx;
	}

	@Override
	public boolean isSortable() {
		return false;
	}

	@Override
	public void setLocation(Rectangle loc) {
		this.loc = loc;
	}

	@Override
	public void setSortable(boolean sortable) {
		
	}
}
