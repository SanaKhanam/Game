/**
 * Contains all graphical components, each one in charge of displaying a specific 'part' of the game
 */
package fr.zimzim.render;

/**
 * Generic type of a graphical component
 * @author Simon Jambu
 *
 */
public interface GraphicComponent {
	
	public void update();
	public void clear();

}
