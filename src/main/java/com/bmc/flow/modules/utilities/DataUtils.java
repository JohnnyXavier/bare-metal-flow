package com.bmc.flow.modules.utilities;

/**
 * this is a utility class to call on an image service in case the record does not have any image on the database.
 * <p>
 * given that uplading images is not a feature currently this allows for a better front end experience when playing with the application.
 */
public class DataUtils {

    private DataUtils() {
    }

    /**
     * returns an existing image url or calls a service to generate one.
     *
     * @param coverImage  an image present on the database
     * @param placeholder a placeholder name to help generate different images from the service
     *
     * @return the image url
     */
    public static String getImage(final String coverImage, final String placeholder) {
        return coverImage == null ? "https://robohash.org/" + placeholder : coverImage;
    }

}
