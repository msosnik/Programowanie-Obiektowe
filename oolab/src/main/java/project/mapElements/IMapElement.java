package project.mapElements;

import project.map.Vector2d;

public interface IMapElement { // czy ten interfejs jest przydatny, jak Pan ma klasę abstrakcyjną?

    public Vector2d getPosition();

    public boolean isAt(Vector2d position);

    public String toString();

}
