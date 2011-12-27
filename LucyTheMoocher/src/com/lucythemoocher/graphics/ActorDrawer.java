package com.lucythemoocher.graphics;

public class ActorDrawer {
	private Animation anim_;
	private boolean persistenceEnabled_;
	private PersistentEffect persistentEffect_;
	private int persistenceTime_;

	public ActorDrawer() {
		anim_ = new Animation();
		persistenceEnabled_ = false;
		persistentEffect_ = new PersistentEffect();
	}

	/**
	 * Draw the actor
	 */
	public void draw(int x, int y) {
		if (persistenceEnabled_) {
			persistentEffect_.add(getAnim().getCurrentImage(), x, y, persistenceTime_);
		} else {
			// only draw if there's no persistence
			getAnim().draw(x, y);
		}
		// we draw even if it's disable to draw the remaining persistent pic
		persistentEffect_.draw(); 
		
	}
	
	public void update() {
		getAnim().update();
		 // we update even if disabled to update the remaining persistent pic
		persistentEffect_.update();
	}
	
	public void initializeAnimation(int resource, int picH, int picW) {
		getAnim().initialize(resource, picH, picW);
	}
	
	public void setAnimation(int tab[], float period) {
		getAnim().setAnimation(tab, period);
	}
	
	public void enablePersistence(int persistenceTime) {
		persistenceEnabled_ = true;
		persistenceTime_ = persistenceTime;
		persistentEffect_.reset();
	}
	
	public void disablePersistence() {
		persistenceEnabled_ = false;
	}
	
	public Animation getAnim() {
		return anim_;
	}
	
	

}
