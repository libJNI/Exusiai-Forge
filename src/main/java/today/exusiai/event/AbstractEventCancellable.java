package today.exusiai.event;

public abstract class AbstractEventCancellable implements Event
{
    private boolean cancelled;
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean state) {
        this.cancelled = state;
    }
}
