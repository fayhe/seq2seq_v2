package training.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**-
 * Created by fay on 3/10/19.
 */

public class ModelLibLoadingequest {

    @JsonProperty(value = "model_key")
    String model_key;
    @JsonProperty(value = "model_type")
    String model_type;

    public String getModel_key() {
        return model_key;
    }

    public void setModel_key(String model_key) {
        this.model_key = model_key;
    }

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

}
