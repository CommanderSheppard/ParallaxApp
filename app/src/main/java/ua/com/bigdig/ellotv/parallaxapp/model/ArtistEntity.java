package ua.com.bigdig.ellotv.parallaxapp.model;

import java.util.Arrays;

/**
 * Created by MishaRJ on 04.10.2015.
 */
public class ArtistEntity {
    private long id;
    private String slug;
    private String title;
    private String pictureLink;
    private String[] artists;
    private long source;
    private String youtube_id;
    private long view_count;
    private long like_count;

    public ArtistEntity() {
    }

    // toString() used only for validating, could be deleted too
    @Override
    public String toString() {
        return "ArtistEntity{" +
                "id=" + id +
                ", slug='" + slug + '\'' +
                ", title='" + title + '\'' +
                ", pictureLink='" + pictureLink + '\'' +
                ", artists=" + Arrays.toString(artists) +
                ", source='" + source + '\'' +
                ", youtube_id='" + youtube_id + '\'' +
                ", view_count=" + view_count +
                ", like_count=" + like_count +
                '}';
    }

    public ArtistEntity(long id, String slug, String title, String pictureLink, String[] artists, long source, String youtube_id, long view_count, long like_count) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.pictureLink = pictureLink;
        this.artists = artists;
        this.source = source;
        this.youtube_id = youtube_id;
        this.view_count = view_count;
        this.like_count = like_count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String[] getArtists() {
        return artists;
    }

    public void setArtists(String[] artists) {
        this.artists = artists;
    }

    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public String getYoutube_id() {
        return youtube_id;
    }

    public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
    }

    public long getView_count() {
        return view_count;
    }

    public void setView_count(long view_count) {
        this.view_count = view_count;
    }

    public long getLike_count() {
        return like_count;
    }

    public void setLike_count(long like_count) {
        this.like_count = like_count;
    }
}
