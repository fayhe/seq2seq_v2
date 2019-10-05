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
        //TODO: Compare metrics with old model?
        modelDAO.updateModelMetrics(modelName, modelMetricsStr);
    }


    public List<Map<String, Object>> getModels(GetModelsRequest getModelRequest) {
        List<Map<String, Object>> models = modelDAO.getModels(getModelRequest.getTaskName(),
                getModelRequest.getClientName(), getModelRequest.getDocTypeName());
        return models;
    }

    public void selectModel(String modelName) {
        modelDAO.selectModel(modelName);
    }
}


