package com.kun.movieisawesome.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TsaiKunYu on 14/05/16.
 */
public class ModelConfigImage {
    private String base_url;
    private String secure_base_url;
    private List<String> backdrop_sizes = new ArrayList<String>();
    private List<String> logo_sizes = new ArrayList<String>();
    private List<String> poster_sizes = new ArrayList<String>();
    private List<String> profile_sizes = new ArrayList<String>();
    private List<String> still_sizes = new ArrayList<String>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The base_url
     */
    public String getBase_url() {
        return base_url;
    }

    /**
     *
     * @param base_url
     * The base_url
     */
    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    /**
     *
     * @return
     * The secure_base_url
     */
    public String getSecure_base_url() {
        return secure_base_url;
    }

    /**
     *
     * @param secure_base_url
     * The secure_base_url
     */
    public void setSecure_base_url(String secure_base_url) {
        this.secure_base_url = secure_base_url;
    }

    /**
     *
     * @return
     * The backdrop_sizes
     */
    public List<String> getBackdrop_sizes() {
        return backdrop_sizes;
    }

    /**
     *
     * @param backdrop_sizes
     * The backdrop_sizes
     */
    public void setBackdrop_sizes(List<String> backdrop_sizes) {
        this.backdrop_sizes = backdrop_sizes;
    }

    /**
     *
     * @return
     * The logo_sizes
     */
    public List<String> getLogo_sizes() {
        return logo_sizes;
    }

    /**
     *
     * @param logo_sizes
     * The logo_sizes
     */
    public void setLogo_sizes(List<String> logo_sizes) {
        this.logo_sizes = logo_sizes;
    }

    /**
     *
     * @return
     * The poster_sizes
     */
    public List<String> getPoster_sizes() {
        return poster_sizes;
    }

    /**
     *
     * @param poster_sizes
     * The poster_sizes
     */
    public void setPoster_sizes(List<String> poster_sizes) {
        this.poster_sizes = poster_sizes;
    }

    /**
     *
     * @return
     * The profile_sizes
     */
    public List<String> getProfile_sizes() {
        return profile_sizes;
    }

    /**
     *
     * @param profile_sizes
     * The profile_sizes
     */
    public void setProfile_sizes(List<String> profile_sizes) {
        this.profile_sizes = profile_sizes;
    }

    /**
     *
     * @return
     * The still_sizes
     */
    public List<String> getStill_sizes() {
        return still_sizes;
    }

    /**
     *
     * @param still_sizes
     * The still_sizes
     */
    public void setStill_sizes(List<String> still_sizes) {
        this.still_sizes = still_sizes;
    }

}
