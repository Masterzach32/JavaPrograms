package net.masterzach32.tilerpg.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.masterzach32.tilerpg.main.RPGGame;
import net.masterzach32.tilerpg.main.state.State;
import net.masterzach32.tilerpg.main.ui.gui.Gui;

public class KeyHandler implements KeyListener {
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_O) {
			System.exit(0);
		}
		// Stage 1 Listeners
		if (State.getState() == RPGGame.menuState) {
			State.setState(RPGGame.levelState);
		}
		// Stage 2 Listeners
		else if (State.getState() == RPGGame.levelState) {
			if (RPGGame.level_1 != null) {
				if (e.getKeyCode() == KeyEvent.VK_1) {
					RPGGame.current_level = 1;
					State.setState(RPGGame.gameState);
				}
			}
			if (RPGGame.level_2 != null) {
				if (e.getKeyCode() == KeyEvent.VK_2) {
					RPGGame.current_level = 2;
					State.setState(RPGGame.gameState);
				}
			}
			if (RPGGame.level_3 != null) {
				if (e.getKeyCode() == KeyEvent.VK_3) {
					RPGGame.current_level = 3;
					State.setState(RPGGame.gameState);
				}
			}
			if (RPGGame.level_4 != null) {
				if (e.getKeyCode() == KeyEvent.VK_4) {
					RPGGame.current_level = 4;
					State.setState(RPGGame.gameState);
				}
			}
			if (RPGGame.level_5 != null) {
				if (e.getKeyCode() == KeyEvent.VK_5) {
					RPGGame.current_level = 5;
					State.setState(RPGGame.gameState);
				}
			}
			if (RPGGame.level_6 != null) {
				if (e.getKeyCode() == KeyEvent.VK_6) {
					RPGGame.current_level = 6;
					State.setState(RPGGame.gameState);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_M) {
				State.setState(RPGGame.menuState);
			}
		}
		// Stage 3 Listeners
		else if (State.getState() == RPGGame.gameState) {
			if (e.getKeyCode() == KeyEvent.VK_M) {
				State.setState(RPGGame.menuState);
			}
			if (e.getKeyCode() == KeyEvent.VK_X) {
				int x = RPGGame.getGame().getPlayer().getXp();
				x += 16e6;
				RPGGame.getGame().getPlayer().setXp(x);
			}
			if (e.getKeyCode() == KeyEvent.VK_C) {
				RPGGame.getGame().getPlayer().attackEntity(50, RPGGame.getGame().getPlayer());
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				RPGGame.getGame().getPlayer().up = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				RPGGame.getGame().getPlayer().dn = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				RPGGame.getGame().getPlayer().lt = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				RPGGame.getGame().getPlayer().rt = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_Q) {
				RPGGame.getGame().getPlayer().abilityA();
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				RPGGame.getGame().getPlayer().abilityB();
			}
			if (e.getKeyCode() == KeyEvent.VK_E) {
				RPGGame.getGame().getPlayer().abilityC();
			}
			if (e.getKeyCode() == KeyEvent.VK_R) {
				RPGGame.getGame().getPlayer().abilityD();
			}
			if (e.getKeyCode() == KeyEvent.VK_I && !Gui.inv.getVisibility()) {
				Gui.inv.showGui();
			}
			else if (e.getKeyCode() == KeyEvent.VK_I && Gui.inv.getVisibility()) {
				Gui.inv.hideGui();
			}
			if (e.getKeyCode() == KeyEvent.VK_P) {
				RPGGame.getGame().pause();
			}
			else if (e.getKeyCode() == KeyEvent.VK_P) {
				RPGGame.getGame().unpause();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			RPGGame.getGame().getPlayer().up = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			RPGGame.getGame().getPlayer().dn = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			RPGGame.getGame().getPlayer().lt = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			RPGGame.getGame().getPlayer().rt = false;
		}
	}
}
