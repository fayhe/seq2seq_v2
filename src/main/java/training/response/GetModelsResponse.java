package training.response;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**-
 * Created by fay on 3/10/19.
 */

public class GetModelsResponse {

    @JsonProperty(value = "models")
    List<Map<String,Object>>  models;

    public List<Map<String, Object>> getModels() {
        return models;
    }

    public void setModels(List<Map<String, Object>> models) {
        this.models = models;
    }

}
