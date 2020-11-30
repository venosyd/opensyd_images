package com.venosyd.open.images.logic;

import java.io.InputStream;

import com.venosyd.open.commons.log.Debuggable;
import com.venosyd.open.commons.util.LoadImage;
import com.venosyd.open.commons.util.SaveImage;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
class ImagesBSImpl implements ImagesBS, Debuggable {

	@Override
	public String save(String collection, String item, InputStream photoStream) {
		return SaveImage.saveImage("storage/" + collection, item, photoStream);
	}

	@Override
	public String load(String collection, String item) {
		return LoadImage.loadImageBase64("storage/" + collection, item);
	}
}
