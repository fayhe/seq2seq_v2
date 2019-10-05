package training.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import training.dao.TrainingDAO;
import training.request.TrainingRequest;
import training.response.TrainingResponse;
import training.service.TrainingService;

@RestController
public class TrainingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    TrainingService trainingService;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping(value="/model_training/v1/training_model",method = RequestMethod.POST)
    @ResponseBody
    public TrainingResponse training(@RequestBody TrainingRequest trainingRequest) {
        TrainingResponse trainingResponse = trainingService.training(trainingRequest);
        return trainingResponse;
    }

}
