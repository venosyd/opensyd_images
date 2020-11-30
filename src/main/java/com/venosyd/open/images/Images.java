package com.venosyd.open.images;

import java.io.InputStream;

import com.venosyd.open.images.logic.ImagesBS;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public abstract class Images {

    /** */
    public static String save(String collection, String item, InputStream photoStream) {
        return ImagesBS.INSTANCE.save(collection, item, photoStream);
    }

    /** */
    public static String load(String collection, String item) {
        return ImagesBS.INSTANCE.load(collection, item);
    }
}
