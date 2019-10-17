package training.utils;

import training.request.ModelLibLoadingequest;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import training.request.ModelLibTrainingRequest;

/**
 * Created by fay on 4/10/19.
 */
@Component
public class ModelLibClient {

    @Value("${modellib.enable}")
    public boolean MODEL_LIB_ENABLE;

    @Value("${modellib.modeltraining.url}")
    public String MODEL_LIB_TRANING_URL;

    @Value("${modellib.modelloading.url}")
    public String MODEL_LIB_LOADING_URL;

    ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        ModelLibClient modelLibClient = new ModelLibClient();
        //String json = "{\"client_name\":\"QMA\",\"task_name\":\"CLASSIFICATION\", \"doc_type_name\":\"EMAIL\"}";
        //System.out.println(modelLibClient.HttpPostWithJson("http://localhost:8080//model_training/v1/training_model", json));
        //modelLibClient.triggerModelTraining("data_path", "QMA", "EMAIL",  "NER", "BERTCLASSIFICATION");
        modelLibClient.triggerModelLoad( "QMA", "EMAIL",  "NER", "BERTCLASSIFICATION");
    }

    public void triggerModelTraining(String dataPath,
                                       String clientName, String docTypeName,  String taskTypeName,
                                       String modelTypeName){
        if(MODEL_LIB_ENABLE) {
            String modelKey = clientName + "_" + docTypeName + "_" + taskTypeName + "_" + modelTypeName;
            ModelLibTrainingRequest modelLibTrainingRequest = createModelLibTraininngRequest(dataPath, modelKey,
                    modelTypeName, taskTypeName, clientName);
            String modelLibTrainingRequestStr = JSONObject.toJSONString(modelLibTrainingRequest);
            System.out.println("modelLibTraining Request:" + modelLibTrainingRequestStr);
            Runnable task1 = new Runnable() {
                @Override
                public void run() {
                    // do something
                    HttpPostWithJson(MODEL_LIB_TRANING_URL, modelLibTrainingRequestStr);
                    System.out.println("Trigger model lib model train API end. modelKey:" + modelKey);
                }
            };
            Future<?> f1 = executor.submit(task1);
            //return HttpPostWithJson(MODEL_LIB_TRANING_URL, modelLibTrainingRequestStr);
        }
    }



    public void triggerModelLoad(String clientName, String docTypeName,  String taskTypeName,
                                     String modelTypeName){

        if(MODEL_LIB_ENABLE) {
            String modelKey = clientName + "_" + docTypeName + "_" + taskTypeName + "_" + modelTypeName;
            ModelLibLoadingequest modelLibLoadingRequest = createModelLibLoadRequest(modelKey,
                    modelTypeName, docTypeName, taskTypeName, clientName);
            String modelLibLoadRequestStr = JSONObject.toJSONString(modelLibLoadingRequest);
            Runnable task1 = new Runnable() {
                @Override
                public void run() {
                    // do something
                    HttpPostWithJson(MODEL_LIB_LOADING_URL, modelLibLoadRequestStr);
                    System.out.println("Load model lib model load API end. modelKey:" + modelKey);
                }
            };
            Future<?> f1 = executor.submit(task1);
            //return HttpPostWithJson(MODEL_LIB_TRANING_URL, modelLibTrainingRequestStr);
        }
    }

    private ModelLibTrainingRequest createModelLibTraininngRequest(String dataPath, String modelKey,
                                                                  String modelTypeName, String taskType,
                                                                   String clientName) {
        ModelLibTrainingRequest modelLibTrainingRequest = new ModelLibTrainingRequest();
        modelLibTrainingRequest.setData_path(dataPath);
        modelLibTrainingRequest.setModel_key(modelKey);
        modelLibTrainingRequest.setModel_type(modelTypeName);
        modelLibTrainingRequest.setTask_type(taskType);
        modelLibTrainingRequest.setClient(clientName);
        return modelLibTrainingRequest;
    }

    private ModelLibLoadingequest createModelLibLoadRequest( String modelKey,
                                                             String modelTypeName,
                                                             String docTypeName,
                                                             String taskTypeName,
                                                             String clientName) {
        ModelLibLoadingequest mdelLibLoadingequest = new ModelLibLoadingequest();
        mdelLibLoadingequest.setModel_key(modelKey);
        mdelLibLoadingequest.setModel_type(modelTypeName);
        mdelLibLoadingequest.setClient(clientName);
        mdelLibLoadingequest.setTask_type(taskTypeName);
        mdelLibLoadingequest.setDoc_type(docTypeName);
        return mdelLibLoadingequest;
    }

    private String HttpPostWithJson(String url, String json) {
        String returnValue = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        System.out.println("url:" +  url);
        System.out.println("request:" +  json);
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
