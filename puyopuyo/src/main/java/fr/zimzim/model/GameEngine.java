package fr.zimzim.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Random;

import fr.zimzim.casestate.CaseBusy;
import fr.zimzim.casestate.CaseEmpty;
import fr.zimzim.meshe.GameItem;
import fr.zimzim.meshe.Puyo;
import fr.zimzim.observer.MyObservable;
import fr.zimzim.sound.SoundEngine;
import fr.zimzim.util.Settings;

/**
 * The GameEngine class defines all logical operations
 * @author Simon Jambu
 *
 */
public class GameEngine {

	/**
	 * Instance of the game map
	 * @see Map
	 */
	private Map map;

	/**
	 * Boolean map used when checking for Puyos combos.
	 * Used to prevent useless puyos checks
	 * @see GameEngine#checkMap()
	 */
	private boolean[][] hasBeenChecked;

	/**
	 * Holds all active game items (items that are currently dropping and can possibly be moved by player)
	 * @see GameEngine#addActiveItems()
	 */
	private List<GameItem> activeItems;

	/**
	 * Holds the next active game items. Displayed on the screen for player
	 * @see ItemRender
	 */
	private List<GameItem> nextActiveItem;

	/**
	 * Used for generating new items randomly
	 * @see GameEngine#addActiveItems()
	 */
	private Random randomGenerator;

	/**
	 * Used to notify observers (Design pattern Observer)
	 */
	private MyObservable observable;

	/**
	 * Current score of the player
	 */
	private int score;

	/**
	 * Constructor of the GameEngine
	 * Creates all useful datas.
	 */
	public GameEngine() {
		this.map = new Map(Settings.MAP_HEIGHT, Settings.MAP_WIDTH);
		this.hasBeenChecked = new boolean[Settings.MAP_HEIGHT][Settings.MAP_WIDTH];
		this.activeItems = new ArrayList<GameItem>();
		this.nextActiveItem = new ArrayList<GameItem>();
		this.randomGenerator = new Random();
		this.observable = new MyObservable();
	}

	/**
	 * Initializes all datas
	 */
	public void init() {
		this.activeItems.clear();
		this.score = 0;
		this.map.clear();
		for(int i = 0; i<Settings.NB_FALLING_PUYOS; i++) {
			GameItem item = new Puyo(-1,i+2,randomGenerator.nextInt(Settings.NB_PUYOS_TYPES));
			nextActiveItem.add(item);
		}
		observable.setChanged();
		observable.notifyObservers(this);
	}

	/**
	 * Create new active game items
	 * @return true when creation done
	 */
	public boolean addActiveItems() {
		activeItems.addAll(nextActiveItem);
		nextActiveItem.clear();
		for(int i=0; i<Settings.MAP_WIDTH; i++) {
			if(map.getCase(0, i).getState() instanceof CaseBusy) return false;
		} //Game over
		for(int i = 0; i<Settings.NB_FALLING_PUYOS; i++) {
			GameItem item = new Puyo(-1,i+2,randomGenerator.nextInt(Settings.NB_PUYOS_TYPES));
			nextActiveItem.add(item);
		} //New puyos appear on middle top of game-map
		observable.setChanged();
		observable.notifyObservers(this);
		return true;
	}

	/**
	 * Proceeds to one step drop of active items
	 * @return true when one item reach an other item and all the others are dropped
	 */
	public boolean fall() {
		boolean hit = false;
		GameItem tmp = null;
		for(int i=0; i<activeItems.size(); i++) {
			GameItem item = activeItems.get(i);
			if(item.getLine()+1 == Settings.MAP_HEIGHT || map.getCase(item.getLine()+1, item.getColumn()).getState() instanceof CaseBusy ) {
				hit = true;
				tmp = item;
				activeItems.remove(item);
			} // If one of active puyos hit another, drop the other....
		}
		if(!hit) {
			for(int i=0; i<activeItems.size(); i++) {
				activeItems.get(i).setLine(activeItems.get(i).getLine()+1);
			}//...else drop them one step
		}
		else {
			map.getCase(tmp.getLine(), tmp.getColumn()).setState(CaseBusy.getInstance());
			map.getCase(tmp.getLine(), tmp.getColumn()).setItem(tmp);
			drop();
			activeItems.clear();
		}
		//Notify observers that game-state changed
		observable.setChanged();
		observable.notifyObservers(this);
		return hit;
	}

	/**
	 * Moves active items one game map case right
	 */
	public void moveRight() {
		GameItem right = activeItems.get(0);
		for(int i=1; i<activeItems.size(); i++) {
			GameItem item = activeItems.get(i);
			if(item.getColumn() > right.getColumn()) right = item;
		}// Get the more on the right
		if(right.getLine() != -1 && right.getColumn()+1<Settings.MAP_WIDTH){
			Case adjacent = map.getCase(right.getLine(), right.getColumn()+1);
			if(adjacent.getState() instanceof CaseEmpty) {
				for(int j=0; j< activeItems.size(); j++) {
					GameItem item = activeItems.get(j);
					item.setColumn(item.getColumn()+1);
				}
				observable.setChanged();
				observable.notifyObservers(this);
			}// If there's not any item one step right, move them and notify observers
		}

	}

	/**
	 * Moves active items one game-map case left
	 */
	public void moveLeft() {
		GameItem left = activeItems.get(0);
		for(int i=1; i<activeItems.size(); i++) {
			GameItem item = activeItems.get(i);
			if(item.getColumn() < left.getColumn()) left = item;
		}// Get the more on the left
		if(left.getLine() != -1 && left.getColumn()>0) {
			Case adjacent = map.getCase(left.getLine(), left.getColumn()-1);
			if(adjacent.getState() instanceof CaseEmpty) {
				for(int j=0; j< activeItems.size(); j++) {
					GameItem item = activeItems.get(j);
					item.setColumn(item.getColumn()-1);
				}
				observable.setChanged();
				observable.notifyObservers(this);
			}// If there's not any item one step left, move them and notify observers
		}

	}

	/**
	 * Rotate active items (axe is left item) one step left
	 */
	public void rotateLeft(){
		if(activeItems.size() > 1) {
			GameItem item = activeItems.get(activeItems.size()-1);
			GameItem axe = activeItems.get(0);
			if(axe.getLine() != -1) {
				// xy
				//y
				//x
				if(axe.getColumn()<item.getColumn() && axe.getLine() > 0){
					item.setLine(axe.getLine()-1);
					item.setColumn(axe.getColumn());
				}
				// yx
				//x
				//y
				else if(axe.getColumn()>item.getColumn()
						&& axe.getLine() < Settings.MAP_HEIGHT-1
						&& map.getCase(axe.getLine()+1, axe.getColumn()).getState() instanceof CaseEmpty){
					item.setLine(axe.getLine()+1);
					item.setColumn(axe.getColumn());
				}

				else if(axe.getColumn()==item.getColumn()){
					//y
					//x
					//yx
					if(axe.getLine() > item.getLine()
							&& axe.getColumn() > 0
							&& map.getCase(axe.getLine(), axe.getColumn()-1).getState() instanceof CaseEmpty) {
						item.setLine(axe.getLine());
						item.setColumn(axe.getColumn()-1);
					}
					//x
					//y
					//xy
					else if(axe.getLine() < item.getLine()
							&& axe.getColumn() < Settings.MAP_WIDTH-1
							&& map.getCase(axe.getLine(), axe.getColumn()+1).getState() instanceof CaseEmpty){
						item.setLine(axe.getLine());
						item.setColumn(axe.getColumn()+1);
					}
				}
			}
			observable.setChanged();
			observable.notifyObservers(this);
		}

	}

	/**
	 * Rotate active items (axe is left item) one step right
	 */
	public void rotateRight() {
		if(activeItems.size() > 1) {
			GameItem item = activeItems.get(activeItems.size()-1);
			GameItem axe = activeItems.get(0);
			if(axe.getLine() != -1) {
				// xy
				//x
				//y
				if(axe.getColumn()<item.getColumn()
						&& axe.getLine() < Settings.MAP_HEIGHT-1
						&& map.getCase(axe.getLine()+1, axe.getColumn()).getState() instanceof CaseEmpty){
					item.setLine(axe.getLine()+1);
					item.setColumn(axe.getColumn());
				}
				// yx
				//y
				//x
				else if(axe.getColumn()>item.getColumn()
						&& axe.getLine() > 0){
					item.setLine(axe.getLine()-1);
					item.setColumn(axe.getColumn());
				}

				else if(axe.getColumn()==item.getColumn()){
					//y
					//x
					//xy
					if(axe.getLine() > item.getLine()
							&& axe.getColumn() < Settings.MAP_WIDTH-1
							&& map.getCase(axe.getLine(), axe.getColumn()+1).getState() instanceof CaseEmpty) {
						item.setLine(axe.getLine());
						item.setColumn(axe.getColumn()+1);
					}
					//x
					//y
					//yx
					else if(axe.getLine() < item.getLine()
							&& axe.getColumn() > 0
							&& map.getCase(axe.getLine(), axe.getColumn()-1).getState() instanceof CaseEmpty){
						item.setLine(axe.getLine());
						item.setColumn(axe.getColumn()-1);
					}
				}
			}
			observable.setChanged();
			observable.notifyObservers(this);
		}

	}

	/**
	 * Checks for puyos combos
	 * @return true if at least one combo found
	 */
	public boolean checkMap() {
		boolean delete = false;
		for(int i=0; i<Settings.MAP_HEIGHT; i++) {
			for(int j=0; j<Settings.MAP_WIDTH; j++) {
				if(map.getCase(i, j).getState() instanceof CaseBusy && !hasBeenChecked[i][j]) {
					List<Case> toDelete = getCaseToDelete(map.getCase(i, j), new ArrayList<Case>());
					// Get the combo
					if(toDelete.size() >= Settings.COMBO_SIZE) {
						delete(toDelete);
						this.score+=toDelete.size();
						SoundEngine.KICK.play();
						delete = true;
						refreshMap();
						//checkMap();
					} // If greater than defined combo size, delete them, increase player score and refresh game-map
				}
			}
		}// For each puyo (not even explored), check for combos
		for(int i=0; i<Settings.MAP_HEIGHT; i++) {
			for(int j=0; j<Settings.MAP_WIDTH; j++) {
				hasBeenChecked[i][j] = false;
			}
		}
		return delete;
	}

	/**
	 * Refresh game map when linked puyos are deleted
	 */
	private void refreshMap() {
		for(int i=Settings.MAP_HEIGHT-1; i>0; i--) {
			for(int j=0; j<Settings.MAP_WIDTH; j++) {
				Case c = map.getCase(i, j);
				if(c.getState() instanceof CaseBusy) {
					int line = c.getLine();
					while(line+1<Settings.MAP_HEIGHT && map.getCase(line+1, c.getColumn()).getState() instanceof CaseEmpty) {
						line++;
					}
					if(line != c.getLine()) {
						map.getCase(line, c.getColumn()).setState(CaseBusy.getInstance());
						map.getCase(line, c.getColumn()).setItem(c.getItem());
						c.setItem(null);
						c.setState(CaseEmpty.getInstance());
					}
				}
			}
		}
		//For each puyo from bottom to top, replace them because some have been deleted

	}

	/**
	 * Delete linked puyos
	 * @param toDelete: Puyos to delete from the game map
	 */
	private void delete(List<Case> toDelete) {
		for(int i=0; i<toDelete.size(); i++) {
			Case c = toDelete.get(i);
			c.setState(CaseEmpty.getInstance());
			c.setItem(null);
		}
	}

	/**
	 * Recursive method to get linked puyos
	 * Starts from a busy case, gets his successors (neighbours of the same type), re-calls method with successors.
	 * Builds a list during exploration. This list contains all linked puyos of same type.
	 * The <code>hasBeenChecked</code> matrix is used to avoid already checked cases in the upper method.
	 * @param c: the current (busy) case
	 * @param toKick: the current cases to delete
	 * @return
	 */
	private List<Case> getCaseToDelete(Case c, List<Case> toKick) {
		List<Case> successors = getSuccessors(c, toKick);
		if(!toKick.contains(c)) toKick.add(c);
		if(successors.size() == 0) {
			return toKick;
		} // If there's no more puyos linked, return the list
		else{
			for(int i =0; i<successors.size(); i++) {
				Case succ = successors.get(i);
				if(!toKick.contains(succ)){
					hasBeenChecked[succ.getLine()][succ.getColumn()] = true;
					getCaseToDelete(succ, toKick);
				}
			} // else for each successor, proceed to a check
		}
		return toKick;
	}

	/**
	 * Build a list of same types puyos right around case c
	 * @param c: the current map case
	 * @param l: possible successors
	 * @return candidates: same types puyos right around case c
	 */
	private List<Case> getSuccessors(Case c, List<Case> l) {
		List<Case> result = new ArrayList<Case>();
		int line = c.getLine();
		int col = c.getColumn();

		if(line > 0) result.add(map.getCase(line-1, col));
		if(line < Settings.MAP_HEIGHT-1) result.add(map.getCase(line+1, col));
		if(col > 0) result.add(map.getCase(line, col-1));
		if(col < Settings.MAP_WIDTH-1) result.add(map.getCase(line, col+1));

		List<Case> candidates = new ArrayList<Case>();
		for(int i = 0; i<result.size(); i++) {
			Case current = result.get(i);
			if(!l.contains(current)
					&& current.getState() instanceof CaseBusy
					&& current.getItem().getType() == c.getItem().getType()) candidates.add(current);
		} // If there are puyos of same types around case c, return them in a list
		return candidates;
	}

	/**
	 * Makes active items drop (a reverse of the list is done if one is under another)
	 */
	public void drop() {
		boolean reverse = false;
		for(int i=0; i<activeItems.size()-1; i++){
			if(activeItems.get(i).getLine() < activeItems.get(i+1).getLine()) reverse = true;
		}
		if(reverse) {
			List<GameItem> tmp = new ArrayList<GameItem>();
			tmp.addAll(activeItems);
			activeItems.clear();
			for(int i=tmp.size()-1; i>=0; i--){
				activeItems.add(tmp.get(i));
			}
		}

		for(int j=0; j<activeItems.size(); j++) {
			GameItem other = activeItems.get(j);
			int line = other.getLine();

			while(line+1 < Settings.MAP_HEIGHT && map.getCase(line+1, other.getColumn()).getState() instanceof CaseEmpty) {
				line++;
			}
			if(line != -1) {
				other.setLine(line);
				map.getCase(other.getLine(), other.getColumn()).setState(CaseBusy.getInstance());
				map.getCase(other.getLine(), other.getColumn()).setItem(other);
			}
		}
	}


	/**
	 * Returns the map case in [i][j] 
	 * @param i: The line
	 * @param j: The column
	 * @return the case in [i][j]
	 */
	public Case getCase(int i, int j) {
		return this.map.getCase(i, j);
	}

	/**
	 * Returns current-active-game-items
	 * @return activeItems
	 */
	public List<GameItem> getActiveItems(){
		return this.activeItems;
	}

	/**
	 * Returns current next-active-game-items
	 * @return  nextActiveItem
	 */
	public List<GameItem> getNextItems(){
		return this.nextActiveItem;
	}

	/**
	 * Adds a new observer
	 * @param o: The observer
	 */
	public void addObserver(Observer o) {
		this.observable.addObserver(o);
	}

	/**
	 * Returns player's current score
	 * @return score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Returns the game map
	 * @return map
	 */
	public Map getMap() {
		return this.map;
	}



}
