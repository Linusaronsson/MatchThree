package Multiplayer;

import Model.Jewel;


public class UpdateBoard extends Message {
	Jewel[] board;

    public UpdateBoard(Jewel[] board) {
        super(MessageType.ACCEPTED_GAME);
        this.board = board;
    }

    public Jewel[] getBoard() { return board; }
}
