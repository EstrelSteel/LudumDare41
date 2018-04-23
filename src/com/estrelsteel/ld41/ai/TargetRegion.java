package com.estrelsteel.ld41.ai;

import com.estrelsteel.engine2.shape.rectangle.AbstractedRectangle;

public class TargetRegion {

	private int id;
	private AbstractedRectangle location;
	private AIPriority priority;
	
	public TargetRegion(int id, AbstractedRectangle location, AIPriority priority) {
		this.id = id;
		this.location = location;
		this.priority = priority;
	}
	
	public int getID() {
		return id;
	}
	
	public AbstractedRectangle getLocation() {
		return location;
	}
	
	public AIPriority getPriority() {
		return priority;
	}
	
	public void setLocation(AbstractedRectangle location) {
		this.location = location;
	}
	
	public void setPriority(AIPriority priority) {
		this.priority = priority;
	}
	
}
