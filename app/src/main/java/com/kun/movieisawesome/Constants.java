package com.kun.movieisawesome;

/**
 * Created by TsaiKunYu on 14/05/16.
 */
public class Constants {
    public static final String API_KEY_VALUE = "0a08e38b874d0aa2d426ffc04357069d";
    public static final String ATTACH_API_KEY = "api_key=" + API_KEY_VALUE;
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String GENRE = "genre/";
    public static final String LIST = "list";
    public static final String CATE_MOVIE = "movie/";
    public static final String CATE_TV = "tv/";
    public static final String CATE_PERSON = "person/";
    public static final String GET_CONFIG = "configuration";
    public static final String GET_POPULAR = "popular/";
    public static final String GET_SEARCH = "search/";
    public static final String PARAM_QUERY = "query";

    public static final String RESTFUL_GET_CONFIG = Constants.BASE_URL + Constants.GET_CONFIG + "?" + Constants.ATTACH_API_KEY;
    public static final String RESTFUL_GET_MOVIE_GENRE_LIST = Constants.BASE_URL + Constants.GENRE + Constants.CATE_MOVIE + LIST + "?" + Constants.ATTACH_API_KEY;
    public static final String RESTFUL_GET_TV_GENRE_LIST = Constants.BASE_URL + Constants.GENRE + Constants.CATE_TV + LIST + "?" + Constants.ATTACH_API_KEY;


    public static final String PREF_CONFIG_IMAGE = "configImage";
    public static final String PREF_MOVIE_GENRE_LIST = "movieGenreList";
    public static final String PREF_TV_GENRE_LIST = "tvGenreList";


    public static final String RESP_JSON_KEY_RESULTS = "results";
    public static final String RESP_JSON_KEY_IMAGES = "images";
}
