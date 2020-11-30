package com.venosyd.open.images.logic;

import java.io.InputStream;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public interface ImagesBS {

    /**
     * singleton
     */
    ImagesBS INSTANCE = new ImagesBSImpl();

    /** */
    String save(String collection, String item, InputStream photoStream);

    /** */
    String load(String collection, String item);
}
