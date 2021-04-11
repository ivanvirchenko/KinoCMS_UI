package com.avada.kino.util;

import java.io.File;

public interface UploadPaths {
    String MOVIES_UPLOAD_PATH = "public/share" + File.separator + "movies";
    String NEWS_UPLOAD_PATH = "public/share" + File.separator + "news";
    String PROMOTION_UPLOAD_PATH = "public/share" + File.separator + "promotion";
    String BANNER_UPLOAD_PATH = "public/share" + File.separator + "banners";
    String CINEMA_UPLOAD_PATH = "public/share" + File.separator + "cinema";
    String HALLS_UPLOAD_PATH = "public/share" + File.separator + "cinema" + File.separator + "halls";
}
