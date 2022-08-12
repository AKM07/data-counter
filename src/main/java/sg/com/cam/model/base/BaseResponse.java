package sg.com.cam.model.base;

import sg.com.cam.util.CustomException;

public class BaseResponse {
    private Object responseData;

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

}
