package xyz.gucciclient.event;

public class Track {
   private long id;
   private String name;
   private String artists;

   public Track(String name, String artists, long id) {
      this.id = id;
      this.name = name;
      this.artists = artists;
   }

   public long getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getArtists() {
      return this.artists;
   }
}
