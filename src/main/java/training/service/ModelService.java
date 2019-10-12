package training.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import training.dao.ModelDAO;
import training.request.GetModelsRequest;
import training.request.TrainingRequest;
import training.response.TrainingResponse;
import training.utils.ModelLibClient;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**-
 * Created by fay on 3/10/19.
 */

@Component
public class ModelService {

    @Autowired
    ModelDAO modelDAO;
    @Autowired
    ModelLibClient modelLibClient;

    public TrainingResponse training(TrainingRequest trainingRequest) {
        TrainingResponse trainingResponse = new TrainingResponse();
        List<String> modelNames = modelDAO.getModelName(trainingRequest.getTaskName(), trainingRequest.getClientName(),
                trainingRequest.getDocTypeName());
        for (String modelName : modelNames) {
            //trigger model lib
            System.out.println("modelName:" + modelName);
            String modelTypeName = modelDAO.getModelTypeName(modelName);
            System.out.println("modelTypeName:" + modelTypeName);
            modelLibClient.triggerModelTraining(trainingRequest.getTrainingDataPath(), trainingRequest.getClientName(),
                    trainingRequest.getDocTypeName(), trainingRequest.getTaskName(), modelTypeName);
            modelDAO.createTrainingProcess(modelName);
            Integer trainingProcessId = modelDAO.getMaxTrainingProcessId(modelName, trainingRequest.getClientName(), trainingRequest.getDocTypeName());
            trainingResponse.addTrainingProcessId(trainingProcessId);
        }
        return trainingResponse;
    }


    public void updateTrainingProcess(String modelKey, JSONObject modelMetricsJSON) {
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
        int processId = modelDAO.getMaxTrainingProcessId(modelName, clientName, docTypeName);
        System.out.println("process id:" + processId);
        modelDAO.updateTrainingProcess(ModelDAO.STATUS_COMPLETED, processId, modelMetricsStr);
        modelDAO.updateModelMetrics(modelName, modelMetricsStr);
    }


    public List<Map<String, Object>> getModels(GetModelsRequest getModelRequest) {
        List<Map<String, Object>> models = modelDAO.getModels(getModelRequest.getTaskName(),
                getModelRequest.getClientName(), getModelRequest.getDocTypeName());
        return models;
    }

    public void selectModel(String modelName) {
        //trigger model load
        String[] splittedModelName = modelName.split("_");
        String taskName = splittedModelName[0];
        String clientName = splittedModelName[1];
        String docTypeName = splittedModelName[2];
        String modelTypeName = splittedModelName[3];
        modelLibClient.triggerModelLoad( clientName,  docTypeName, taskName, modelTypeName);
        modelDAO.selectModel(modelName);
    }
}


