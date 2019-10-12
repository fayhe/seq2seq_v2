package training.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**-
 * Created by fay on 3/10/19.
 */

public class ModelLibTrainingRequest {


    @JsonProperty(value = "model_key")
    String model_key;
    @JsonProperty(value = "model_type_name")
    String model_type_name;
    @JsonProperty(value = "data_path")
    String data_path;
    @JsonProperty(value = "task_type")
    String task_type;

    public String getModel_Key() {
        return model_key;
    }

    public void setModel_Key(String model_Key) {
        this.model_key = model_key;
    }

    public String getModel_type_name() {
        return model_type_name;
    }

    public void setModel_type_name(String model_type_name) {
        this.model_type_name = model_type_name;
    }

    public String getData_path() {
        return data_path;
    }

    public void setData_path(String data_path) {
        this.data_path = data_path;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

}
