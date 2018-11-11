package com.music;

import com.music.model.Artist;
import com.music.model.DataSource;
import com.music.model.SongArtist;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        if(!dataSource.open()) {
            System.out.println("Can't open datasource");
            return;
        }
        List<Artist> artists = dataSource.queryArtists(DataSource.ORDER_BY_ASC);
        if(artists == null) {
            System.out.println("No artists!");
            return;
        }
        for(Artist artist : artists) {
            System.out.println("ID = " + artist.getId() + ", Name = " + artist.getName());
        }

        List<String> albumsForArtist = dataSource.queryAlbumsForArtist("Pink Floyd", DataSource.ORDER_BY_ASC);
        for(String album : albumsForArtist) {
            System.out.println(album);
        }

        List<SongArtist> songArtists = dataSource.queryArtistForSong("Heartless", DataSource.ORDER_BY_ASC);
        if(songArtists == null) {
            System.out.println("Couldn't find the artist for that song");
            return;
        }
        for(SongArtist artist : songArtists) {
            System.out.println("Artist name = " + artist.getArtistName() + "\n" +
                    "Album Name = " + artist.getAlbumName() + "\n" +
                    "Track = " + artist.getTrack());
        }
        dataSource.querySongsMetaData();

        int count = dataSource.getCount(DataSource.TABLE_SONGS);
        System.out.println("Number of songs is " + count);
        dataSource.createViewForSongArtist();
        songArtists = dataSource.querySongInfoView("Go Your Own Way");
        if(songArtists.isEmpty()) {
            System.out.println("Couldn't find the artist for the song");
            return;
        }
        for(SongArtist artist : songArtists) {
            System.out.println("FROM VIEW \n Artist name = " + artist.getArtistName() + "\n" +
                    " Album name = " + artist.getAlbumName() + "\n" +
                    " Track number = " + artist.getTrack());
        }
        dataSource.close();
    }
}
