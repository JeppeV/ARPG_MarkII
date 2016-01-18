package generator.standard;

/**
 * Created by Jeppe Vinberg on 18-01-2016.
 */
public interface Map {

    public char getTile(int x, int y);

    public int getWidthInTiles();

    public int getHeightInTiles();
}
