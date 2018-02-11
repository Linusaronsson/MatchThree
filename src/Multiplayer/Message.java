package Multiplayer;

import java.io.Serializable;

import Model.Jewel;
/**
 * Message sent between two users playing
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 123456789L;
    private MessageType type;

    public enum MessageType {
    	REQUESTED_GAME, //game request: (empty payload)
        ACCEPTED_GAME, //game accepted: notifies that game was accepted, and sends initial generated board.
        END_GAME,
        CELL_UPDATE
    }

    /**
     * 
     * @param type
     */
    public Message(MessageType type) {
        this.type = type;
    }
    
    /**
     * 
     * @param type
     */
    public String asString(MessageType type) {
    	switch(type) {
    	case REQUESTED_GAME:
    		return "REQUESTED_GAME";
    	case ACCEPTED_GAME:
    		return "ACCEPTED_GAME";
    	case END_GAME:
    		return "END_GAME";
    	case CELL_UPDATE:
    		return "CELL_UPDATE";
    	}
    	return null;
    }
    
    /**
     * ...
     */
    public MessageType getType() { return type; }


    /**
     * ...
     */
    public String toString() {
    	return "Message type: " + asString(type) + "\n";
    }
}
