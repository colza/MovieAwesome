package com.kun.movieisawesome.model;

import com.kun.movieisawesome.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TsaiKunYu on 14/05/16.
 */
public class ModelPeople extends ModelGeneral{
    private Boolean adult;
    private List<Object> also_known_as = new ArrayList<Object>();
    private String biography;
    private String birthday;
    private String deathday;
    private String homepage;
    private Integer id;
    private String name;
    private String place_of_birth;
    private String profile_path;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * The also_known_as
     */
    public List<Object> getAlso_known_as() {
        return also_known_as;
    }

    /**
     *
     * @param also_known_as
     * The also_known_as
     */
    public void setAlso_known_as(List<Object> also_known_as) {
        this.also_known_as = also_known_as;
    }

    /**
     *
     * @return
     * The biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     *
     * @param biography
     * The biography
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     *
     * @return
     * The birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     *
     * @param birthday
     * The birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     *
     * @return
     * The deathday
     */
    public String getDeathday() {
        return deathday;
    }

    /**
     *
     * @param deathday
     * The deathday
     */
    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    /**
     *
     * @return
     * The homepage
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     *
     * @param homepage
     * The homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
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
     * The place_of_birth
     */
    public String getPlace_of_birth() {
        return place_of_birth;
    }

    /**
     *
     * @param place_of_birth
     * The place_of_birth
     */
    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    /**
     *
     * @return
     * The profile_path
     */
    public String getProfile_path() {
        return profile_path;
    }

    /**
     *
     * @param profile_path
     * The profile_path
     */
    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }


    @Override
    public String getShowTitle() {
        return getName();
    }

    @Override
    public String getShowSubtitle() {
        return getName();
    }

    @Override
    public String getRequestPopularUrl() {
        return Constants.BASE_URL + Constants.CATE_PERSON + Constants.GET_POPULAR;
    }

    @Override
    public String getShowDescription() {
        return getBiography();
    }

    @Override
    public String getModelType() {
        return ModelPeople.class.getName();
    }


    @Override
    public String getShowRelease() {
        return null;
    }

    @Override
    public String getAvgVote() {
        return null;
    }

    @Override
    public String getSearchUrl() {
        return Constants.BASE_URL + Constants.GET_SEARCH + Constants.CATE_PERSON ;
    }

    @Override
    public List<Integer> getGenre_ids() {
        return null;
    }

    @Override
    public String getGenreType() {
        return null;
    }

    @Override
    public String getPoster_path() {
        return getProfile_path();
    }

    @Override
    public String getBackdrop_path() {
        return getProfile_path();
    }

}
