package training.service;

import com.alibaba.fastjson.JSONObject;
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


    public void updateTrainingProcess(String modelKey, JSONObject modelMetricsJSON){
        String[] modelKeys = modelKey.split("_");
        String clientName = modelKeys[0];
        String docTypeName = modelKeys[1];
        String taskName = modelKeys[2];
        String modelTypeName = modelKeys[3];
        String modelName = taskName + "_" + clientName + "_" + docTypeName + "_" + modelTypeName;
        System.out.println("generated modelName:" + modelName);
        String modelMetricsStr = JSONObject.toJSONString(modelMetricsJSON);
        // CLASSIFICATION_QMA_EMAIL_BERTCLASSIFICATION
        //get max training process id
        int processId = trainingDAO.getMaxTrainingProcessId(modelName, clientName , docTypeName);
        System.out.println("process id:" + processId);
        trainingDAO.updateTrainingProcess(TrainingDAO.STATUS_COMPLETED, processId, modelMetricsStr);
        //TODO: Compare metrics with old model?
        trainingDAO.updateModel(modelName, modelMetricsStr);
        }
    }


