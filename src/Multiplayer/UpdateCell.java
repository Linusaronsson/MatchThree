package Multiplayer;

import Model.Jewel;


public class UpdateCell extends Message {
	private int x;
    private int y;
    private Jewel jewel_type;

    public UpdateCell(int x, int y, Jewel jewel_type) {
        super(MessageType.CELL_UPDATE);
        this.x = x;
        this.y = y;
        this.jewel_type = jewel_type;
    }

    public int getY() { return y; }
    public int getX() { return x; }
    public Jewel getJewelType() { return jewel_type; }
}
