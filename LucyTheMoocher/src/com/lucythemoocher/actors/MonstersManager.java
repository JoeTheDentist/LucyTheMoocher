package com.lucythemoocher.actors;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * 
 * @author gloups
 *
 */
public class MonstersManager {
	private ArrayList<Monster> monsters_;


	public MonstersManager() {
		monsters_ = new ArrayList<Monster>();
	}

	public void update() {
		Iterator<Monster> it = getIterator();
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
	
	public Iterator<Monster> getIterator() {
		return monsters_.iterator();
	}
	
}
