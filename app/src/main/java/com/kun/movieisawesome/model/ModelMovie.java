package com.kun.movieisawesome.model;

import com.kun.movieisawesome.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TsaiKunYu on 14/05/16.
 */
public class ModelMovie extends ModelGeneral{

    private String poster_path;
    private Boolean adult;
    private String overview;
    private String release_date;
    private List<Integer> genre_ids = new ArrayList<Integer>();
    private Integer id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private Double popularity;
    private Integer vote_count;
    private Boolean video;
    private Double vote_average;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The poster_path
     */
    public String getPoster_path() {
        return poster_path;
    }

    /**
     *
     * @param poster_path
     * The poster_path
     */
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    /**
     *
     * @return
     * The adult
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     *
     * @param adult
     * The adult
     */
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    /**
     *
     * @return
     * The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     *
     * @param overview
     * The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     *
     * @return
     * The release_date
     */
    public String getRelease_date() {
        return release_date;
    }

    /**
     *
     * @param release_date
     * The release_date
     */
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    /**
     *
     * @return
     * The genre_ids
     */
    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    /**
     *
     * @param genre_ids
     * The genre_ids
     */
    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The original_title
     */
    public String getOriginal_title() {
        return original_title;
    }

    /**
     *
     * @param original_title
     * The original_title
     */
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    /**
     *
     * @return
     * The original_language
     */
    public String getOriginal_language() {
        return original_language;
    }

    /**
     *
     * @param original_language
     * The original_language
     */
    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The backdrop_path
     */
    public String getBackdrop_path() {
        return backdrop_path;
    }

    /**
     *
     * @param backdrop_path
     * The backdrop_path
     */
    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    /**
     *
     * @return
     * The popularity
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     *
     * @param popularity
     * The popularity
     */
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    /**
     *
     * @return
     * The vote_count
     */
    public Integer getVote_count() {
        return vote_count;
    }

    /**
     *
     * @param vote_count
     * The vote_count
     */
    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    /**
     *
     * @return
     * The video
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     *
     * @param video
     * The video
     */
    public void setVideo(Boolean video) {
        this.video = video;
    }

    /**
     *
     * @return
     * The vote_average
     */
    public String getVote_average() {
        return String.valueOf(vote_average);
    }

    /**
     *
     * @param vote_average
     * The vote_average
     */
    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public String getShowImage() {
        return getPoster_path();
    }

    @Override
    public String getShowTitle() {
        return title;
    }

    @Override
    public String getShowSubtitle() {
        return original_title;
    }

    @Override
    public String getShowDescription() {
        return getOverview();
    }

    @Override
    public String getModelType() {
        return ModelMovie.class.getName();
    }

    @Override
    public String getRequestPopularUrl() {
        return Constants.BASE_URL + Constants.CATE_MOVIE + Constants.GET_POPULAR + "?" + Constants.ATTACH_API_KEY;
    }

    @Override
    public String getSearchUrl() {
        return Constants.BASE_URL + Constants.GET_SEARCH + Constants.CATE_MOVIE + "?" + Constants.ATTACH_API_KEY;
    }

    @Override
    public String getShowRelease() {
        return getRelease_date();
    }

    @Override
    public String getAvgVote() {
        return String.valueOf(getVote_average());
    }

    @Override
    public String getGenreType() {
        return Constants.PREF_MOVIE_GENRE_LIST;
    }
}
