package training.request;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;

/**-
 * Created by fay on 3/10/19.
 */

public class TrainingCallBackRequest {


    @JsonProperty(value = "trained_model_metrics")
    JSONObject trainedModelMetrics;

    @JsonProperty(value = "model_key")
    String modelKey;

    public JSONObject getTrainedModelMetrics() {
        return trainedModelMetrics;
    }

    public void setTrainedModelMetrics(JSONObject trainedModelMetrics) {
        this.trainedModelMetrics = trainedModelMetrics;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

}
