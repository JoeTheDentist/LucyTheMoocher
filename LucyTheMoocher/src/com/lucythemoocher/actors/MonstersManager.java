package com.lucythemoocher.actors;

import java.util.ArrayList;
import java.util.ListIterator;


/**
 * 
 * @author gloups
 *
 */
public class MonstersManager {
	private ArrayList<Monster> monsters_;


	public MonstersManager() {
		monsters_ = new ArrayList<Monster>();
		Monster m = new Monster();
		m.update();
		addMonster(m);
		
	}

	public void update() {
		ListIterator<Monster> it = getIterator();
		while(it.hasNext()) {
			Monster monster = it.next();
			if (monster.isToRemove()) {
				it.remove();
				continue;
			}
			monster.update();
		}
	}
	
	public void render() {
		for (Monster monster: monsters_) {
			monster.draw();
		}
	}
	
	public void addMonster(Monster monster) {
		monsters_.add(monster);
	}
	
	public ListIterator<Monster> getIterator() {
		return monsters_.listIterator();
	}
	
}
