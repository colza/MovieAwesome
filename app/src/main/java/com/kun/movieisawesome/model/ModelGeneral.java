package com.kun.movieisawesome.model;

import java.util.List;

/**
 * Created by TsaiKunYu on 15/05/16.
 */
public abstract class ModelGeneral {
    public abstract Integer getId();
    public abstract String getPoster_path();
    public abstract String getBackdrop_path();
    public abstract String getShowTitle();
    public abstract String getShowSubtitle();
    public abstract String getShowDescription();
    public abstract String getModelType();
    public abstract String getRequestPopularUrl();
    public abstract String getShowRelease();
    public abstract String getAvgVote();
    public abstract String getSearchUrl();
    public abstract List<Integer> getGenre_ids();
    public abstract String getGenreType();
}
