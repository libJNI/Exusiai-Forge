package today.exusiai.event.events;

import today.exusiai.event.*;

public class EventChat implements Event {
	private String message;
	
    public EventChat(final String message) {
        this.message = message;
    }
    
    public String getMessage() {
		return this.message;
	}
}
