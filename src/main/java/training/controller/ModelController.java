package training.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import training.request.GetModelsRequest;
import training.request.TrainingCallBackRequest;
import training.request.TrainingRequest;
import training.request.SelectModelRequest;
import training.response.GetModelsResponse;
import training.response.TrainingResponse;
import training.service.ModelService;

@RestController
public class ModelController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    ModelService modelService;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping(value="/model_training/v1/training_model",method = RequestMethod.GET)
    @ResponseBody
    public TrainingResponse traingGet(@RequestParam(value="client_name") String clientName,
                                      @RequestParam(value="task_name") String taskName,
                                      @RequestParam(value="doc_type_name") String docTypeName,
                                      @RequestParam(value="training_data_path") String dataPath) {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setTaskName(taskName);
        trainingRequest.setClientName(clientName);
        trainingRequest.setDocTypeName(docTypeName);
        trainingRequest.setTrainingDataPath(dataPath);
        TrainingResponse trainingResponse = modelService.training(trainingRequest);
        return trainingResponse;
    }

    @RequestMapping(value="/model_training/v1/training_model",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public TrainingResponse train(@RequestBody TrainingRequest trainingRequest) {
        TrainingResponse trainingResponse = modelService.training(trainingRequest);
        return trainingResponse;
    }




    @RequestMapping(value="/model_training/v1/training_model",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
            )
    @ResponseBody
    public TrainingResponse train1( TrainingRequest trainingRequest) {
        TrainingResponse trainingResponse = modelService.training(trainingRequest);
        return trainingResponse;
    }



    @RequestMapping(value="/model_training/v1/training_model_callback",
            method = RequestMethod.POST
          )
    @ResponseBody
    public void trainModelCallBack(@RequestBody TrainingCallBackRequest trainingCallBackRequest) {
        modelService.updateTrainingProcess(trainingCallBackRequest.getModelKey(), trainingCallBackRequest.getTrainedModelMetrics());
    }


    @RequestMapping(value="/model_selection/v1/get_models",method = RequestMethod.GET)
    @ResponseBody
    public GetModelsResponse getModels(@RequestParam(value="client_name") String clientName,
                                       @RequestParam(value="task_name") String taskName,
                                       @RequestParam(value="doc_type_name") String docTypeName) {
        GetModelsRequest getModelsRequest = new GetModelsRequest();
        getModelsRequest.setClientName(clientName);
        getModelsRequest.setDocTypeName(docTypeName);
        getModelsRequest.setTaskName(taskName);
        GetModelsResponse getModelsResponse = new GetModelsResponse();
        getModelsResponse.setModels(modelService.getModels(getModelsRequest));
        return getModelsResponse;
    }


    @RequestMapping(value="/model_selection/v1/select_model",method = RequestMethod.PUT)
    @ResponseBody
    public void selectModelCallBack(@RequestBody SelectModelRequest selectModelRequest) {
        modelService.selectModel(selectModelRequest.getModelName());
    }
}
