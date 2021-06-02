package com.task.booksapp.network;

/***
 * this is singleton for the  services which used the default configuration
 * and in its constructor we call createRetrofit which decide which retrofit object must be used based on the params
 */
public class Services extends BaseServices {

    private static Services mInstance;

    /***
     *
     * this constructor create retrofit object with the default values from build config
     */
    private Services() {
        // createRetrofit(Buil.Base_Url);
    }

    /***
     *
     * @param baseUrl
     * this constructor create retrofit object with the received values from params
     */
    private Services(String baseUrl) {
        createRetrofit(baseUrl);
    }

    /***
     * @param baseUrl
     * @return return instance of services object which create retrofit
     * object with the received values from params with the received params
     */
    public static synchronized Services getInstance(String baseUrl) {
        if (mInstance == null) {
            mInstance = new Services(baseUrl);
        }
        return mInstance;
    }


}
