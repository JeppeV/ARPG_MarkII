package generator.standard;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public interface Map {

    int BORDER = 1;

    char getTile(int x, int y);

    char getTile(Coordinates coords);

    int getWidthInTiles();

    int getHeightInTiles();
}
