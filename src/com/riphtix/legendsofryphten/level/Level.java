package com.riphtix.legendsofryphten.level;

import com.riphtix.legendsofryphten.entities.Entity;
import com.riphtix.legendsofryphten.gfx.Screen;
import com.riphtix.legendsofryphten.level.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private byte[] tiles;
    public int width;
    public int height;

    public List<Entity> entities = new ArrayList<Entity>();

    public Level(int width, int height) {
        tiles = new byte[width * height];
        this.width = width;
        this.height = height;
        this.generateLevel();
    }

    public void generateLevel() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width] = Tile.GRASS.getId();
            }
        }
    }

    public void tick() {
        for(Entity entity: entities){
            entity.tick();
        }
    }

    public void renderTiles(Screen screen, int xOffset, int yOffset) {
        if (xOffset < 0)
            xOffset = 0;
        if (xOffset > ((width << 3) - screen.width))
            xOffset = ((width << 3) - screen.width);
        if (yOffset < 0)
            yOffset = 0;
        if (yOffset > ((height << 3) - screen.height))
            yOffset = ((height << 3) - screen.height);

        screen.setOffset(xOffset, yOffset);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getTile(x, y).render(screen, this, x << 3, y << 3);
            }
        }
    }

    public void renderEntities(Screen screen){
        for(Entity entity: entities){
            entity.render(screen);
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x > width || y < 0 || y > height)
            return Tile.VOID;
        return Tile.tiles[tiles[x + y * width]];
    }

    public void addEntity(Entity entity){
        this.entities.add(entity);
    }
}
