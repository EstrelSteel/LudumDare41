package com.estrelsteel.ld41.field;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.image.Renderable;
import com.estrelsteel.engine2.shape.rectangle.AbstractedRectangle;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.world.World;
import com.estrelsteel.ld41.actors.Ball;
import com.estrelsteel.ld41.actors.DebrisBackground;
import com.estrelsteel.ld41.actors.FieldBackground;
import com.estrelsteel.ld41.actors.Shadow;

public class Field extends World {

	private FieldBackground background;
	private FieldBackground stands_ew;
	private FieldBackground stands_n;
	private Ball ball;
	private Shadow ball_shadow;
	private ArrayList<Wall> walls;
	private ArrayList<Renderable> mines;
	private ArrayList<Renderable> old_mines;
	private ArrayList<Renderable> debris;
	private DebrisBackground debrisBackground;
	
	public Field() {
		super(new PixelGrid());

		background = new FieldBackground(QuickRectangle.location(80, 60, 800, 480));
//		background = new FieldBackground(QuickRectangle.location(0, 0, 960, 640));-15, -20, 990, 640
		stands_ew = new FieldBackground(QuickRectangle.location(-15, -20, 990, 640));
		stands_ew.setRunningAnimationNumber(1);
		stands_n = new FieldBackground(QuickRectangle.location(-15, 0, 990, 60));
		stands_n.setRunningAnimationNumber(2);
		debrisBackground = new DebrisBackground(QuickRectangle.location(0, 0, 960, 640));
		ball = new Ball("ball", QuickRectangle.location(960/2 - 32, 300 - 32, 64, 64));
		ball_shadow = new Shadow("ball_shadow", ball.getLocation());
		
		walls = new ArrayList<Wall>();
		
		//GOAL RIGHT
		walls.add(new Wall(0, QuickRectangle.location(background.getLocation().getX() + background.getLocation().getWidth(), 
				background.getLocation().getY() - 256, 256, 150 + 256))); // RIGHT
		walls.add(new Wall(0, QuickRectangle.location(background.getLocation().getX() + background.getLocation().getWidth(), 
				background.getLocation().getY() - 256 + 150 + 256 + 180, 256, 150 + 256))); // RIGHT
		walls.add(new Wall(4, QuickRectangle.location(background.getLocation().getX() + background.getLocation().getWidth() + 16, 
				background.getLocation().getY() + 150 - 32, 256 - 16, 32)));
		walls.add(new Wall(4, QuickRectangle.location(background.getLocation().getX() + background.getLocation().getWidth() + 16, 
				background.getLocation().getY() + 150 + 180, 256 - 16, 32)));
		walls.add(new Wall(6, QuickRectangle.location(background.getLocation().getX() + background.getLocation().getWidth() + 64, 
				background.getLocation().getY() + 150 - 64, 256, 180 + 128)));

		//GOAL LEFT
		walls.add(new Wall(2, QuickRectangle.location(background.getLocation().getX() - 256, 
				background.getLocation().getY() - 256, 256, 150 + 256))); // LEFT
		walls.add(new Wall(2, QuickRectangle.location(background.getLocation().getX() - 256, 
				background.getLocation().getY() - 256 + 150 + 256 + 180, 256, 150 + 256))); // LEFT
		walls.add(new Wall(4, QuickRectangle.location(background.getLocation().getX() - 256, 
				background.getLocation().getY() + 150 - 32, 256 - 16, 32)));
		walls.add(new Wall(4, QuickRectangle.location(background.getLocation().getX() - 256, 
				background.getLocation().getY() + 150 + 180, 256 - 16, 32)));
		walls.add(new Wall(5, QuickRectangle.location(background.getLocation().getX() - 256 - 64, 
				background.getLocation().getY() + 150 - 64, 256, 180 + 128)));
		
		walls.add(new Wall(1, QuickRectangle.location(background.getLocation().getX() - 256, 
				background.getLocation().getY() - 256, background.getLocation().getWidth() + 256 * 2, 256))); // UP
		walls.add(new Wall(3, QuickRectangle.location(background.getLocation().getX() - 256,
				background.getLocation().getY() + background.getLocation().getHeight(), background.getLocation().getWidth() + 256 * 2, 256))); // DOWN
		
		mines = new ArrayList<Renderable>();
		old_mines = new ArrayList<Renderable>();
		debris = new ArrayList<Renderable>();
		updateObjects();
	}
	
	public Ball spawnBall(int favour) {
		ball = new Ball("ball", QuickRectangle.location(960/2 - 32, 300 - 32, 64, 64));
		ball_shadow = new Shadow("ball_shadow", ball.getLocation());
		ball.setVelocityX(favour);
		return ball;
	}
	
	public void updateObjects() {
		setObjects(new ArrayList<Renderable>());
		getObjects().add(background);
		getObjects().add(stands_ew);
		getObjects().add(stands_n);
//		getObjects().addAll(old_mines);
		getObjects().add(debrisBackground);
		getObjects().addAll(mines);
		getObjects().addAll(debris);
//		sortObjects();
	}
	
	public Graphics2D renderWorld(Graphics2D ctx) {
//		ctx.drawImage(background.getImage(), (int) arena.getX(), (int) arena.getY(), (int) arena.getWidth(), (int) arena.getHeight(), null);
//		ctx.drawLine(960/2, 0, 960/2, 640);
		super.renderWorld(ctx);
		ball_shadow.setLocation(QuickRectangle.location(ball.getLocation().getX(), ball.getLocation().getY() + (3 * ball.getLocation().getHeight() / 4), 
				ball.getLocation().getWidth(), ball.getLocation().getHeight() / 4));
		ball_shadow.render(ctx, this);
		ball.render(ctx, this);
//		for(int i = 0; i < walls.size(); i++) {
//			ctx.fillRect((int) walls.get(i).getLocation().getX(), (int) walls.get(i).getLocation().getY(), (int) walls.get(i).getLocation().getWidth(), (int) walls.get(i).getLocation().getHeight());
//		}
		return ctx; 
	}
	
	public DebrisBackground getDebrisBackground() {
		return debrisBackground;
	}

	public Ball getBall() {
		return ball;
	}
	
	public AbstractedRectangle getArenaSize() {
		return background.getLocation();
	}
	
	public ArrayList<Wall> getWalls() {
		return walls;
	}
	
	public ArrayList<Renderable> getMines() {
		return mines;
	}
	
	public ArrayList<Renderable> getOldMines() {
		return old_mines;
	}
	
	public ArrayList<Renderable> getDebris() {
		return debris;
	}
}
