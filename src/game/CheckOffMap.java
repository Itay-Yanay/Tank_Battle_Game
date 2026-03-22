package game;

/**
 * @name CheckOffMap
 * @author Max Dustin and Itay Yanay
 * 
 * Checks if a Tank object has gone outside of the map and returns it to a neutral position
 *
 */
public interface CheckOffMap {
	
	/**
	 * Returns tank to a predetermined location if outside of designated map boundaries
	 * @param tank
	 */
	public void goToSpawnCheck(Tank tank);
	
}
