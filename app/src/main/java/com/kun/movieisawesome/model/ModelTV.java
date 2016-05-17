package com.kun.movieisawesome.model;

import com.kun.movieisawesome.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TsaiKunYu on 14/05/16.
 */
public class ModelTV extends ModelGeneral{

    private String poster_path;
    private Double popularity;
    private Integer id;
    private String backdrop_path;
    private Double vote_average;
    private String overview;
    private String first_air_date;
    private List<String> origin_country = new ArrayList<String>();
    private List<Integer> genre_ids = new ArrayList<Integer>();
    private String original_language;
    private Integer vote_count;
    private String name;
    private String original_name;
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
     * The vote_average
     */
    public Double getVote_average() {
        return vote_average;
    }

    /**
     *
     * @param vote_average
     * The vote_average
     */
    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
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
     * The first_air_date
     */
    public String getFirst_air_date() {
        return first_air_date;
    }

    /**
     *
     * @param first_air_date
     * The first_air_date
     */
    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    /**
     *
     * @return
     * The origin_country
     */
    public List<String> getOrigin_country() {
        return origin_country;
    }

    /**
     *
     * @param origin_country
     * The origin_country
     */
    public void setOrigin_country(List<String> origin_country) {
        this.origin_country = origin_country;
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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The original_name
     */
    public String getOriginal_name() {
        return original_name;
    }

    /**
     *
     * @param original_name
     * The original_name
     */
    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    @Override
    public String getShowTitle() {
        return getName();
    }

    @Override
    public String getShowSubtitle() {
        return getOriginal_name();
    }

    @Override
    public String getModelType() {
        return ModelTV.class.getName();
    }

    @Override
    public String getRequestPopularUrl() {
        return Constants.BASE_URL + Constants.CATE_TV + Constants.GET_POPULAR + "?" + Constants.ATTACH_API_KEY;
    }

    @Override
    public String getShowDescription() {
        return getOverview();
    }

    @Override
    public String getShowRelease() {
        return getFirst_air_date();
    }

    @Override
    public String getAvgVote() {
        return String.valueOf(getVote_average());
    }

    @Override
    public String getSearchUrl() {
        return Constants.BASE_URL + Constants.GET_SEARCH + Constants.CATE_TV + "?" + Constants.ATTACH_API_KEY;
    }

    @Override
    public String getGenreType() {
        return Constants.PREF_TV_GENRE_LIST;
    }

}
