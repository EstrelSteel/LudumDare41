package com.estrelsteel.ld41;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

	public boolean kick;
	public boolean mine;
	public boolean nextTurn;
	public int selection;
	
	public boolean reload;
	public int x;
	public int y;
	
	public InputHandler() {
		kick = false;
		mine = false;
		selection = -1;
		
		reload = false;
		x = 0;
		y = 0;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 32 || e.getKeyCode() == 13) {
			nextTurn = true;
		}
		if(e.getKeyCode() >= 48 && e.getKeyCode() <= 57) {
			selection = e.getKeyCode() - 48;
		}
		if(e.getKeyCode() == 27) {
			reload = true;
			System.out.println("restarting");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 32 || e.getKeyCode() == 13) {
			nextTurn = false;
		}
		if(e.getKeyCode() >= 48 && e.getKeyCode() <= 57) {
			selection = -1;
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(e.getButton() == MouseEvent.BUTTON1) {
			mine = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			mine = true;
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(e.getButton() == MouseEvent.BUTTON1) {
			mine = false;
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			mine = false;
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
