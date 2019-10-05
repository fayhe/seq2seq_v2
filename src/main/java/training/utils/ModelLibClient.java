package training.utils;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import training.request.ModelLibTrainingRequest;

/**
 * Created by fay on 4/10/19.
 */
@Component
public class ModelLibClient {

    //TODO: Read url from application.properties
    public static final String MODEL_LIB_TRANING_URL  = "http://localhost:8080//model_training/v1/training_model";

    public static void main(String[] args) {
        ModelLibClient modelLibClient = new ModelLibClient();
        //String json = "{\"client_name\":\"QMA\",\"task_name\":\"CLASSIFICATION\", \"doc_type_name\":\"EMAIL\"}";
        //System.out.println(modelLibClient.HttpPostWithJson("http://localhost:8080//model_training/v1/training_model", json));
        modelLibClient.triggerModelTraining("data_path", "QMA", "EMAIL",  "NER", "BERTCLASSIFICATION");
    }

    public String triggerModelTraining(String dataPath,
                                       String clientName, String docTypeName,  String taskTypeName,
                                       String modelTypeName){
        String modelKey = clientName + "_" + docTypeName + "_" + taskTypeName + "_" + modelTypeName;
        ModelLibTrainingRequest modelLibTrainingRequest = createModelLibTraininngRequest(dataPath, modelKey,
                modelTypeName, taskTypeName);
        String modelLibTrainingRequestStr = JSONObject.toJSONString(modelLibTrainingRequest);
        return HttpPostWithJson(MODEL_LIB_TRANING_URL, modelLibTrainingRequestStr);
    }


    private ModelLibTrainingRequest createModelLibTraininngRequest(String dataPath, String modelKey,
                                                                  String modelTypeName, String taskType) {
        ModelLibTrainingRequest modelLibTrainingRequest = new ModelLibTrainingRequest();
        modelLibTrainingRequest.setData_path(dataPath);
        modelLibTrainingRequest.setModel_Key(modelKey);
        modelLibTrainingRequest.setModel_type_name(modelTypeName);
        modelLibTrainingRequest.setTask_type(taskType);
        return modelLibTrainingRequest;
    }

    private String HttpPostWithJson(String url, String json) {
        String returnValue = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {

            httpClient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(url);

            StringEntity requestEntity = new StringEntity(json, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);

            returnValue = httpClient.execute(httpPost, responseHandler);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return returnValue;
    }


}
