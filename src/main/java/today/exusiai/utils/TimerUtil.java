package today.exusiai.utils;

public class TimerUtil {
   public long previousMS = 0L;

   public boolean hasReached(double ms) {
      return (double)(this.getTime() - this.previousMS) >= ms;
   }

   public long getTime() {
      return System.nanoTime() / 1000000L;
   }

   public void reset() {
      this.previousMS = this.getTime();
   }
}
