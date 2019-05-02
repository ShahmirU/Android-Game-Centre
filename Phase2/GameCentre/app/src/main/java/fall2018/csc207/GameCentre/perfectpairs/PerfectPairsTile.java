package fall2018.csc207.GameCentre.perfectpairs;

import fall2018.csc207.GameCentre.R;
import fall2018.csc207.GameCentre.slidingtiles.Tile;

public class PerfectPairsTile extends Tile {

    /**
     * Whether the tile is currently facing the player.
     */
    private boolean revealing = false;

    /**
     * Whether the tile and its pair have been discovered.
     */
    private boolean discovered = false;

    /**
     * The front side of the tile.
     */
    private int tileFace;

    /**
     * The back side of the tile.
     */
    private int tileBack;

    /**
     * The shape on the front of the tile.
     */
    private String tileShape;

    /**
     * A tile in the Perfect Pairs grid; look up and set the front and back of tile.
     *
     * @param shape The shape on the tile.
     */
    public PerfectPairsTile(String shape) {
        super(shape);
        tileShape = shape;
        tileFace = super.getBackground();
        tileBack = R.drawable.tile_border;
    }

    /**
     * A tile in the Perfect Pairs grid; look up and set the front and back of tile.
     *
     * @return The shape on the tile.
     */
    public String getTileShape() {
        return tileShape;
    }


    /**
     * Return true if the tile has been discovered.
     *
     * @return true if the tile has been discovered; false otherwise.
     */
    public boolean isDiscovered() {
        return discovered;
    }

    /**
     * Set discovered to newDiscovered.
     */
    public void setDiscovered(boolean newDiscovered) {
        this.discovered = newDiscovered;
    }

    /**
     * Return the tile image based on which side is facing the currentPlayer.
     *
     * @return the background id.
     */
    public int getBackground() {
        if (revealing)
            return tileFace;
        else
            return tileBack;
    }

    /**
     * Return true if the tile is currently facing the currentPlayer.
     *
     * @return true if the tile is facing the currentPlayer; false otherwise.
     */
    public boolean isRevealing() {
        return revealing;
    }

    /**
     * Set revealing to newRevealing.
     *
     * @param newRevealing Whether the card is now facing the currentPlayer.
     */
    public void setRevealing(boolean newRevealing) {
        this.revealing = newRevealing;
    }

    /**
     * Whether this tile is equivalent to tile2.
     *
     * @param tile2 The tile this tile is being equated to.
     */
    public boolean equals(PerfectPairsTile tile2) {
        return (this.getTileShape().equals(tile2.getTileShape()));
    }

    /**
     * Turn over the tile.
     */
    public void turnTile() {
        if (this.isRevealing()) {
            this.setRevealing(false);
        } else {
            this.setRevealing(true);
        }
    }
}
