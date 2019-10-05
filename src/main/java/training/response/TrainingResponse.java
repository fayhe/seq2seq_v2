package training.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
/**-
 * Created by fay on 3/10/19.
 */

public class TrainingResponse {

    @JsonProperty(value = "training_process_id")
    List<Integer> trainingProcessId = new ArrayList<Integer>();

    public List<Integer> getTrainingProcessId() {
        return trainingProcessId;
    }

    public void addTrainingProcessId(Integer trainingProcessId) {
        this.trainingProcessId.add(trainingProcessId);
    }

}
