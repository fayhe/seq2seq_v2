package training.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**-
 * Created by fay on 3/10/19.
 */

public class GetModelsRequest {

    @JsonProperty(value = "client_name")
    String clientName;
    @JsonProperty(value = "task_name")
    String taskName;
    @JsonProperty(value = "doc_type_name")
    String docTypeName;


    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
    }

    public String getClientName() {
        return clientName;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDocTypeName() {
        return docTypeName;
    }


}
