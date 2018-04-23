package com.estrelsteel.ld41;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.actor.Text;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.image.Image;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.Point2;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.collide.CollideArea;
import com.estrelsteel.engine2.shape.collide.PerspectiveRectangleArea;
import com.estrelsteel.engine2.shape.collide.RectangleCollideArea;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.window.WindowSettings;
import com.estrelsteel.engine2.world.FrozenWorld;
import com.estrelsteel.engine2.world.Menu;
import com.estrelsteel.engine2.world.World;
import com.estrelsteel.ld41.actors.Debris;
import com.estrelsteel.ld41.actors.DebrisInAir;
import com.estrelsteel.ld41.actors.Mine;
import com.estrelsteel.ld41.ai.AI;
import com.estrelsteel.ld41.ai.Difficulty;
import com.estrelsteel.ld41.field.Field;
import com.estrelsteel.ld41.field.Wall;
import com.estrelsteel.ld41.hud.HudItem;

public class LD41 implements StartListener, StopListener, RenderListener, TickListener {

	private Launcher l;
	private Field f;
	private InputHandler in;
	
	private DecimalFormat format = new DecimalFormat("00");
	
	private int shakeCameraTime;
	private double shakeCameraPower;
	private double shakeBoundX;
	private double shakeBoundY;
	
	private double friction;
	
	private double kickDistance;
	private double kickPower;
	
	private AI ai;
	private State state;
	private Mode mode;
	private long roundTime;
	private int turn;
	private int maxTurn;
	
	private int scoreBlue;
	private int scoreOrange;
	private int goal;
	private Menu hud;
	private Menu tutorial;
	private Menu title;
	private Text textScoreBlue;
	private Text textScoreOrange;
	private Text textMinesBlue;
	private Text textMinesOrange;
	private Text textTurn;
	private Text textNextTurn;
	private Text textBlueTurn;
	private Text textOrangeTurn;
	private boolean textLoaded;
	
	private int mines;
	private int maxMines;
	private int minesP2;
	private int maxMinesP2;
	private int totalMines;
	private double minePower;
	private int maxDebrisPerMine;
	private int minDebrisPerMine;
	private Graphics2D debrisCtx;
	private World debrisWorld;
	private BufferedImage debrisImage;
	
//	private Debris temp;
	
	public static void main(String[] args) {
		new LD41();
	}
	
	
	// 90:120 = 3:4 = 480:640 = 720:960
	@SuppressWarnings("static-access")
	public LD41() {
		l = new Launcher();
		Rectangle size;
		if(System.getProperty("os.name").startsWith("Windows")) {
			size = QuickRectangle.location(0, 0, 950, 630);
		}
		else {
			size = QuickRectangle.location(0, 0, 960, 640);
		}
		in = new InputHandler();
		
		l.getEngine().setWindowSettings(new WindowSettings(size, "Bomber Ball - EstrelSteel", "v1.0a", 0));
		
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		l.getEngine().addKeyListener(in);
		l.getEngine().addMouseListener(in);
		l.getEngine().addMouseMotionListener(in);
		
		l.getEngine().development = true;
//		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/LD41/LD41";
		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();
	}
	
	@Override
	public void start() {
		Mine.loadAnimations();
		DebrisInAir.loadAnimations();

		title = new Menu("Title", true);
		hud = new Menu("Hud", false);
		tutorial = new Menu("Tutorial", false);
		textLoaded = false;
		configureGame(Mode.MENU, Difficulty.TUTORIAL);
		
//		textScoreBlue = new Text("00", QuickRectangle.location(480 - 48 * 2, 48, 0, 0), new Font("Menlo", Font.BOLD, 48), Color.BLUE);
//		textScoreOrange = new Text("00", QuickRectangle.location(480 + 36, 48, 0, 0), new Font("Menlo", Font.BOLD, 48), Color.ORANGE);
//		textMinesBlue = new Text("Mines: 0/" + maxMines, QuickRectangle.location(120, 580, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.DARK_GRAY);
//		textMinesOrange = new Text("Mines: 0/" + maxMinesP2, QuickRectangle.location(700, 580, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.DARK_GRAY);
//		textTurn = new Text("Turn: 1/" + maxTurn, QuickRectangle.location(120, 620, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.DARK_GRAY);
//		textNextTurn = new Text("Press [SPACE] to continue.", QuickRectangle.location(300, 620, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.BLACK);
//		textBlueTurn = new Text("Blue's turn.", QuickRectangle.location(400, 580, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.BLUE);
//		textOrangeTurn = new Text("Orange's turn.", QuickRectangle.location(380, 580, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.ORANGE);
		
		HudItem temp;
		temp = new HudItem(QuickRectangle.location(480 - 140, 0, 280, 60));
		hud.getObjects().add(temp);
		temp = new HudItem(QuickRectangle.location(0, 540, 960, 100));
		temp.setRunningAnimationNumber(1);
		hud.getObjects().add(temp);
		hud.getObjects().add(textScoreBlue);
		hud.getObjects().add(textScoreOrange);
		hud.getObjects().add(textMinesBlue);
		hud.getObjects().add(textMinesOrange);
		hud.getObjects().add(textTurn);
		hud.getObjects().add(textNextTurn);
		hud.getObjects().add(textBlueTurn);
		hud.getObjects().add(textOrangeTurn);
		temp = new HudItem(QuickRectangle.location(0, 0, 960, 640));
		temp.setRunningAnimationNumber(6);
		tutorial.getObjects().add(temp);
//		temp = (Debris) new Debris("test", QuickRectangle.location(320, 160, 32, 8)).setVelocityH(15).setVelocity(5, 10);
		
		temp = new HudItem(QuickRectangle.location(0, 0, 960, 640));
		temp.setRunningAnimationNumber(2);
		title.getObjects().add(temp);
		
//		loadMode(Mode.FREEPLAY);
	}
	
	private void loadGameText() {
		textScoreBlue = new Text("00", QuickRectangle.location(480 - 48 * 2, 48, 0, 0), new Font("Menlo", Font.BOLD, 48), Color.BLUE);
		textScoreOrange = new Text("00", QuickRectangle.location(480 + 36, 48, 0, 0), new Font("Menlo", Font.BOLD, 48), Color.ORANGE);
		textMinesBlue = new Text("Mines: 0/" + maxMines, QuickRectangle.location(120, 580, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.DARK_GRAY);
		textMinesOrange = new Text("Mines: 0/" + maxMinesP2, QuickRectangle.location(700, 580, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.DARK_GRAY);
		textTurn = new Text("Turn: 1/" + maxTurn, QuickRectangle.location(120, 620, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.DARK_GRAY);
		textNextTurn = new Text("Press [SPACE] to continue.", QuickRectangle.location(300, 620, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.BLACK);
		textBlueTurn = new Text("Blue's turn.", QuickRectangle.location(400, 580, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.BLUE);
		textOrangeTurn = new Text("Orange's turn.", QuickRectangle.location(380, 580, 0, 0), new Font("Menlo", Font.BOLD, 24), Color.ORANGE);
	}
	
	private void configureGame(Mode mode, Difficulty d) {
		System.out.println("configuring to " + mode.name());
		f = new Field();
		ai = new AI(d);
//		state = State.ACTION;
//		mode = Mode.AI;
		turn = 0;
		maxTurn = 25;
		
		friction = 0.1;
		kickDistance = 64;
		kickPower = 0.25;
		
		mines = 0;
		maxMines = 3;
		minesP2 = 0;
		maxMinesP2 = 3;
		minePower = 30;
		maxDebrisPerMine = 14;
		minDebrisPerMine = 4;
		debrisWorld = new World(new PixelGrid());
		debrisImage = new BufferedImage(960, 640, BufferedImage.TYPE_INT_ARGB);
		
		shakeCameraTime = 0;
		shakeCameraPower = 4;
		shakeBoundX = 48;
		shakeBoundY = 48;
		
		scoreBlue = 0;
		scoreOrange = 0;
		
		if(!textLoaded) {
			System.out.println("loading text");
			loadGameText();
			textLoaded = true;
		}
		loadMode(mode);
	}
	
	private void loadMode(Mode mode) {
		this.mode = mode;
		textScoreBlue.setText("00");
		textScoreOrange.setText("00");
		if(mode == Mode.FREEPLAY) {
			textNextTurn.setText("");
			textBlueTurn.setText("");
			textOrangeTurn.setText("");
			textTurn.setText("Press [ESCAPE] to exit.");
			textMinesBlue.setText("Mines: " + mines + "/" + maxMines);
			textMinesOrange.setText("");
			maxMines = 10;
			state = State.ACTION;
		}
		if(mode == Mode.AI) {
			System.out.println("Loading AI");
			textBlueTurn.setText("");
			textOrangeTurn.setText("");
			textMinesOrange.setText("");
			state = State.PLAYER_PLACEMENT;
			if(ai.getDifficulty() != Difficulty.HARD) {
				f.getMines().addAll(ai.runAI(f.getMines(), f.getBall()));
			}
		}
		if(mode == Mode.HOTSEAT) {
			textOrangeTurn.setText("");
			state = State.PLAYER_PLACEMENT;
		}
		title.setVisible(false);
		hud.setVisible(true);
		if(mode == Mode.MENU) {
			title.setVisible(true);
			hud.setVisible(false);
			state = State.MENU;
		}
		f.updateObjects();
	}

	@SuppressWarnings("static-access")
	@Override
	public void tick() {
		if(in.reload) {
			in.reload = false;
			start();
		}
		if(state != State.MENU && mode != Mode.MENU) {
//			System.out.println("x:" + in.x + "\t" + "y:" + in.y);
//			System.out.println("mines" + mines + "/" + maxMines);
			if(in.nextTurn && state != State.ACTION) {
				if(ai.getDifficulty() == Difficulty.HARD && mode == Mode.AI) {
					f.getMines().addAll(ai.runAI(f.getMines(), f.getBall()));
					f.updateObjects();
				}
				if(state == State.PLAYER_PLACEMENT && mode == Mode.HOTSEAT) {
					state = State.PLAYER2_PLACEMENT;
					textOrangeTurn.setText("Orange's turn.");
					textBlueTurn.setText("");
				}
				else {
					state = State.ACTION;
					roundTime = System.currentTimeMillis();
					if(mode != Mode.FREEPLAY) {
						textTurn.setText("Turn: " + (turn + 1) + "/" + maxTurn);
						textNextTurn.setText("");
						textOrangeTurn.setText("");
					}
				}
				in.nextTurn = false;
			}
			else if(in.nextTurn && mode == Mode.FREEPLAY) {
				tutorial.setVisible(false);
			}
	
			AbstractedPoint ballMid = PointMaths.getCentre(f.getBall().getFutureLocation());
			if(in.kick) {
				AbstractedPoint mouse = new AbstractedPoint(in.x, in.y);
				double d = PointMaths.getDistanceTo(mouse, ballMid);
				if(d < kickDistance) {
					f.getBall().applyVelocity(PointMaths.getDirectionTowards(mouse, ballMid).getRadians(), (kickDistance / d) * kickPower);
				}
			}
			if(in.mine && (state == State.PLAYER_PLACEMENT || state == State.PLAYER2_PLACEMENT || mode == Mode.FREEPLAY)) {
				in.mine = false;
				if(goal == 0 && ((mines < maxMines && mode == Mode.FREEPLAY) || (mines < maxMines && state == State.PLAYER_PLACEMENT) || (minesP2 < maxMinesP2 && state == State.PLAYER2_PLACEMENT))) {
					Mine nm = null;
					if(state == State.PLAYER_PLACEMENT || mode == Mode.FREEPLAY) {
						mines++;
						totalMines++;
						nm = new Mine("BlueMine" + totalMines, QuickRectangle.location(in.x - 32, in.y - 32, 64, 64));
						nm.setRunningAnimationNumber(0);
						nm.setAILocation(-1);
						textMinesBlue.setText("Mines: " + mines + "/" + maxMines);
						System.err.println("add blue");
					}
					if(state == State.PLAYER2_PLACEMENT) {
						minesP2++;
						nm = new Mine("OrangeMine" + totalMines, QuickRectangle.location(in.x - 32, in.y - 32, 64, 64));
						nm.setRunningAnimationNumber(1);
						nm.setAILocation(-2);
						textMinesOrange.setText("Mines: " + minesP2 + "/" + maxMinesP2);
						System.err.println("add orange");
					}
					if(nm != null) {
						f.getMines().add(nm);
						f.updateObjects();
					}
				}
			}
			CollideArea fb = new RectangleCollideArea(f.getBall().getFutureLocation());
			Mine m = (Mine) f.checkCollide(f.getMines(), fb, null);
			if(m != null) {
				if(state == State.ACTION || mode == Mode.FREEPLAY) {
					if(m.getAILocation() == -1) {
						mines--;
						textMinesBlue.setText("Mines: " + mines + "/" + maxMines);
						System.err.println("remove blue\t" + m.getName());
					}
					else if(m.getAILocation() == -2 && mode == Mode.HOTSEAT) {
						minesP2--;
						textMinesOrange.setText("Mines: " + minesP2 + "/" + maxMinesP2);
						System.err.println("remove orange\t" + m.getName());
					}
					f.getMines().remove(m);
		//			m.setRunningAnimationNumber((int) (Math.random() * 2) + 2);
		//			m.getRunningAnimation().setCurrentFrame((int) (Math.random() * 4));
					f.getOldMines().add(m);
					f.getBall().applyVelocity(PointMaths.getDirectionTowards(PointMaths.getCentre(m.getLocation()), ballMid).getRadians(), minePower);
					shakeCameraTime = shakeCameraTime + 15;
					int debrisCreate = (int) (Math.random() * maxDebrisPerMine) + minDebrisPerMine;
					Debris d;
					int size;
					for(int i = 0; i < debrisCreate; i++) {
						size = (int) (Math.random() * 33);
						d = new Debris("debris" + i, QuickRectangle.location(m.getLocation().getX(), m.getLocation().getY(), size, size / 2));
						d.applyVelocity(Math.random() * Math.PI * 2, Math.random() * (minePower / 2), Math.random() * minePower);
						d.getDebrisInAir().setRunningAnimationNumber((int) (Math.random() * 2));
						f.getDebris().add(d);
					}
					f.updateObjects();
				}
				else {
					m.setRunningAnimationNumber(4);
				}
			}
			if(state == State.ACTION || mode == Mode.FREEPLAY) {
				fb = new PerspectiveRectangleArea(f.getBall().getFutureLocation());
				Wall w;
				for(int i = 0; i < f.getWalls().size(); i++) {
					w = f.getWalls().get(i);
					if(fb.checkCollision(w.getLocation())) {
						Debris d;
						int size;
						switch(w.getID()) {
						case 0:
							f.getBall().setVelocityX(-f.getBall().getVelocityX());
							break;
						case 1:
							f.getBall().setVelocityY(-f.getBall().getVelocityY());
							break;
						case 2:
							f.getBall().setVelocityX(-f.getBall().getVelocityX());
							break;
						case 3:
							f.getBall().setVelocityY(-f.getBall().getVelocityY());
							break;
						case 4:
							f.getBall().setVelocityX(-f.getBall().getVelocityX());
							f.getBall().setVelocityY(-f.getBall().getVelocityY());
							break;
						case 5:
							scoreOrange++;
							textScoreOrange.setText(format.format(scoreOrange));
							goal = -5;
							shakeCameraTime = 45;
							for(int j = 0; j < 10; j++) {
								size = (int) (Math.random() * 33);
								d = new Debris("debris_goal" + j, QuickRectangle.location(f.getBall().getLocation().getX(), f.getBall().getLocation().getY(), size, size / 2));
								d.applyVelocity(Math.random() * Math.PI - Math.PI / 2, Math.random() * (minePower / 2), Math.random() * minePower);
								d.getDebrisInAir().setRunningAnimationNumber((int) (Math.random() * 2));
								f.getDebris().add(d);
							}
							f.getBall().setLocation(QuickRectangle.location(-10000, -10000, 0, 0));
							System.err.println(format.format(scoreBlue) + " - " + format.format(scoreOrange));
							break;
						case 6:
							scoreBlue++;
							textScoreBlue.setText(format.format(scoreBlue));
							goal = 5;
							shakeCameraTime = 45;
							for(int j = 0; j < 10; j++) {
								size = (int) (Math.random() * 33);
								d = new Debris("debris_goal" + j, QuickRectangle.location(f.getBall().getLocation().getX(), f.getBall().getLocation().getY(), size, size / 2));
								d.applyVelocity(Math.random() * Math.PI + Math.PI / 2, Math.random() * (minePower / 2), Math.random() * minePower);
								d.getDebrisInAir().setRunningAnimationNumber((int) (Math.random() * 2));
								f.getDebris().add(d);
							}
							f.getBall().setLocation(QuickRectangle.location(-10000, -10000, 0, 0));
							System.err.println(format.format(scoreBlue) + " - " + format.format(scoreOrange));
							break;
						default:
							break;
						}
					}
				}
			}
			
			if(goal == 0 && ((f.getBall().getVelocityX() == 0 && (f.getBall().getLocation().getX() < -64 || f.getBall().getLocation().getX() > 960 + 64)) 
					|| ((f.getBall().getVelocityY() == 0 && (f.getBall().getLocation().getY() < -64 || f.getBall().getLocation().getY() > 960 + 64))))) {
				f.spawnBall(0);
				System.err.println("ball escaped, resetting");
			}
			
			if(shakeCameraTime > 0) {
				shakeCameraTime--;
				Point2 nc = new Point2(f.getMainCamera().getLocation().getX() + (Math.random() * shakeCameraPower - (shakeCameraPower / 2)), 
						f.getMainCamera().getLocation().getY() + (Math.random() * shakeCameraPower - (shakeCameraPower / 2)), f.getGrid());
				if(nc.getX() > shakeBoundX) {
					nc.setX(shakeBoundX - Math.random() * shakeCameraPower);
				}
				else if(nc.getX() < -shakeBoundX) {
					nc.setX(-shakeBoundX + Math.random() * shakeCameraPower);
				}
				if(nc.getY() > shakeBoundY) {
					nc.setY(shakeBoundY - Math.random() * shakeCameraPower);
				}
				else if(nc.getX() < -shakeBoundY) {
					nc.setX(-shakeBoundY + Math.random() * shakeCameraPower);
				}
				f.getMainCamera().setLocation(nc);
			}
			else {
				f.getMainCamera().setLocation(new Point2(0, 0, f.getGrid()));
				if(goal != 0) {
					f.spawnBall(goal);
					goal = 0;
				}
			}
			
			if(state == State.ACTION && f.getBall().getVelocityX() == 0 && f.getBall().getVelocityY() == 0 && mode != Mode.FREEPLAY) {
				if(System.currentTimeMillis() - roundTime < 1500 && System.currentTimeMillis() - roundTime > 200) {
					roundTime = -1;
					f.getBall().applyVelocity(Math.random() * Math.PI * 2, Math.random() * minePower);
				}
				else if(System.currentTimeMillis() - roundTime > 3000) {
					turn++;
					f.updateObjects();
					if(turn >= maxTurn) {
						if(scoreBlue > scoreOrange) {
							((HudItem) title.getObjects().get(0)).setRunningAnimationNumber(3);
						}
						else if(scoreBlue < scoreOrange) {
							((HudItem) title.getObjects().get(0)).setRunningAnimationNumber(4);
						}
						else {
							((HudItem) title.getObjects().get(0)).setRunningAnimationNumber(5);
						}
						state = State.MENU;
						title.setVisible(true);
						hud.setVisible(false); 
//						configureGame();
						return;
					}
					if(ai.getDifficulty() != Difficulty.HARD && mode == Mode.AI) {
						f.getMines().addAll(ai.runAI(f.getMines(), f.getBall()));
					}
					f.updateObjects();
					state = State.PLAYER_PLACEMENT;
					if(mode != Mode.FREEPLAY) {
						textNextTurn.setText("Press [SPACE] to continue.");
					} 
					if(mode == Mode.HOTSEAT) {
						textBlueTurn.setText("Blue's turn.");
					}
				}
			}
			if(state == State.ACTION && f.getBall().getVelocityX() != 0 && f.getBall().getVelocityY() != 0) {
				roundTime = -1;
			}
			
			if(state == State.ACTION || mode == Mode.FREEPLAY) {
				f.getBall().updateLocation(friction);
				f.getBall().getRunningAnimation().run();
			}
			
			for(int i = 0; i < f.getDebris().size(); i++) {
				((Debris) f.getDebris().get(i)).updateHeight(0.5).updateLocation(friction);
				((Debris) f.getDebris().get(i)).updateDebrisInAir();
				if(((Debris) f.getDebris().get(i)).getHeight() > 0) {
					((Debris) f.getDebris().get(i)).getDebrisInAir().getRunningAnimation().run();
				}
				else {
					((Debris) f.getDebris().get(i)).setHeight(0);
					((Debris) f.getDebris().get(i)).updateDebrisInAir();
					if(debrisCtx == null) {
						debrisCtx = (Graphics2D) debrisImage.getGraphics();
					}
					((Debris) f.getDebris().get(i)).render(debrisCtx, (FrozenWorld) debrisWorld);
					f.getDebris().remove(i);
					i--;
				}
			}
			if(debrisCtx != null) {
				debrisCtx.dispose();
				
				f.getDebrisBackground().getRunningAnimation().getFrames().set(0, new Image("null"));
				f.getDebrisBackground().getRunningAnimation().getFrames().get(0).setImage(debrisImage);
				
				debrisCtx = null;
				f.updateObjects();
			}
	//		temp.updateHeight(0.5).updateLocation(friction);
	//		temp.updateDebrisInAir();
	//		if(temp.getHeight() > 0) {
	//			temp.getDebrisInAir().getRunningAnimation().run();
	//		}
		}
		else {
			switch(in.selection) {
			case -1:
				break;
			case 0:
				stop();
				break;
			case 1:
				configureGame(Mode.FREEPLAY, Difficulty.TUTORIAL);
				tutorial.setVisible(true);
				in.selection = -1;
				break;
			case 2:
				configureGame(Mode.HOTSEAT, Difficulty.TUTORIAL);
				in.selection = -1;
				break;
			case 3:
				configureGame(Mode.FREEPLAY, Difficulty.TUTORIAL);
				in.selection = -1;
				break;
			case 4:
				configureGame(Mode.AI, Difficulty.EASY);
				in.selection = -1;
				break;
			case 5:
				configureGame(Mode.AI, Difficulty.MEDIUM);
				in.selection = -1;
				break;
			case 6:
				configureGame(Mode.AI, Difficulty.HARD);
				in.selection = -1;
				break;
			}
		}
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		ctx.setColor(new Color(136, 136, 136));
		ctx.fillRect(0, 0, 960, 640);
		f.renderWorld(ctx);
		hud.renderWorld(ctx);
		tutorial.renderWorld(ctx);
		title.renderWorld(ctx);
//		System.out.println(mode.name() + "\t" + state.name());
		return ctx;
	}

	@Override
	public void stop() {
		
	}
	
}
