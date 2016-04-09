package com.amrendra.codefiesta.loaders;

import android.content.Context;

import com.amrendra.codefiesta.model.ApiResponse;
import com.amrendra.codefiesta.model.Website;

/**
 * Created by Amrendra Kumar on 06/04/16.
 */
public class WebsiteLoader extends BaseLoader<ApiResponse<Website.Response>> {

    public WebsiteLoader(Context context) {
        super(context);
    }

    @Override
    public ApiResponse<Website.Response> loadInBackground() {
/*        Call<Website.Response> call = RestApiClient.getInstance().getResourceList();
        ApiResponse<Website.Response> data = new ApiResponse<>();
        Error error = null;
        try {
            Response response = call.execute();
            if (response.isSuccess()) {
                data.setContent((Website.Response) response.body());
            } else {
                Debug.e("REST call for WebsiteLoader fails : " + response.errorBody().toString(), false);
                error = new Error(response.code(), response.message());
            }
        } catch (IOException e) {
            Debug.e("IOError fetching the WebsiteLoader list : " + e.getMessage(), true);
            error = new Error(Error.CONNECTION_ERROR, "Connection Error");
        } catch (Exception e) {
            Debug.e("Error fetching the WebsiteLoader list : " + e.getMessage(), true);
            error = new Error(Error.UNKNOWN_ERROR, "Unknown Error");
        }
        if (error != null) {
            data.setError(error);
        }
        return data;*/
        return null;
    }
}
