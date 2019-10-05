package training.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import training.dao.TrainingDAO;
import training.request.TrainingRequest;
import training.response.TrainingResponse;
import training.utils.ModelLibClient;

import java.util.ArrayList;
import java.util.List;

/**-
 * Created by fay on 3/10/19.
 */

@Component
public class TrainingService {

    @Autowired
    TrainingDAO trainingDAO;
    @Autowired
    ModelLibClient modelLibClient;

    public TrainingResponse training(TrainingRequest trainingRequest) {
        TrainingResponse trainingResponse = new TrainingResponse();
        List<String> modelNames = trainingDAO.getModelName(trainingRequest.getTaskName(), trainingRequest.getClientName(),
                trainingRequest.getDocTypeName());
        for (String modelName : modelNames) {
            //trigger model lib
            System.out.println("modelName:" + modelName);
            String modelTypeName = trainingDAO.getModelTypeName(modelName);
            System.out.println("modelTypeName:" + modelTypeName);
            modelLibClient.triggerModelTraining(trainingRequest.getTrainingDataPath(), trainingRequest.getClientName(),
                    trainingRequest.getDocTypeName(), trainingRequest.getTaskName(), modelTypeName);
            trainingDAO.createTrainingProcess(modelName);
            Integer trainingProcessId =  trainingDAO.getMaxTrainingProcessId(modelName, trainingRequest.getClientName(), trainingRequest.getDocTypeName());
            trainingResponse.addTrainingProcessId(trainingProcessId);
        }
        return trainingResponse;
    }



}
