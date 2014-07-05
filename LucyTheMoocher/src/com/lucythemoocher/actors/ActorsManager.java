package com.lucythemoocher.actors;

import java.util.Map;

import com.lucythemoocher.controls.ActionController;

public class ActorsManager {
	private static Map<Integer, Actor> actors_;
	private static int nbActors = 0;
	
	public static PlayerCharacter newPlayerCharacter() {
		PlayerCharacter pc = new PlayerCharacter(new ActionController());
		add(pc);
		return pc;
	}
	
	public static void add(Actor a) {
		actors_.put(nbActors, a);
		a.setId(nbActors);
		nbActors++;
	}
}
