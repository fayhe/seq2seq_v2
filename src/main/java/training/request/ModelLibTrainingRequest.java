package training.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**-
 * Created by fay on 3/10/19.
 */

public class ModelLibTrainingRequest {


    @JsonProperty(value = "model_key")
    String model_key;
    @JsonProperty(value = "data_path")
    String data_path;
    @JsonProperty(value = "task_type")
    String task_type;
    @JsonProperty(value = "model_type")
    String model_type;

    @JsonProperty(value = "client")
    String client;
    @JsonProperty(value = "doc_type")
    String doc_type;


    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public String getModel_key() {
        return model_key;
    }

    public void setModel_key(String model_key) {
        this.model_key = model_key;
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
