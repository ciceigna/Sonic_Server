package Items;

import com.badlogic.gdx.math.Vector2;

public class DefItem {
	public Vector2 posicion;
	public Class<?> tipo;
	
	public DefItem(Vector2 posicion, Class<?> tipo) {
		this.posicion = posicion;
		this.tipo = tipo;
	}
}
