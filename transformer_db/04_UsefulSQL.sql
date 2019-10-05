select m.model_name 
from model m, model_type mt, doc_type dt
where m.model_type_name = mt.model_type_name 
  and dt.doc_type_name = m.doc_type_name
  and mt.task_name = 'CLASSIFICATION'
  and dt.client_name = 'QMA' 
  and dt.doc_type_name =  'EMAIL';


  INSERT INTO training_process(model_name,status) VALUES('CLASSIFICATION_QMA_EMAIL_BERTCLASSIFICATION', 'IN_PROGRESS');


  select max(tp.training_process_id)
from model m, training_process tp, doc_type dt
where tp.model_name = m.model_name 
  and dt.doc_type_name = m.doc_type_name
  and tp.model_name = 'CLASSIFICATION_QMA_EMAIL_BERTCLASSIFICATION'
  and dt.client_name = 'QMA' 
  and dt.doc_type_name =  'EMAIL'; 



http://localhost:8080//model_training/v1/training_model
{"client_name":"QMA","task_name":"CLASSIFICATION", "doc_type_name":"EMAIL", "training_data_path":"/ttt/uuu"}

{
    "training_process_id": [
        25
    ]
}


http://localhost:8080//model_training/v1/training_model_callback
{"model_key":"QMA_EMAIL_CLASSIFICATION_BERTCLASSIFICATION","trained_model_metrics":{"precisicion":0.99,"recall":0.8,"f1score":1,"number_of_records":1000}}
{"model_key":"QMA_EMAIL_CLASSIFICATION_BERTCLASSIFICATION","trained_model_metrics":{"precisicion":0.99,"recall":0.8,"f1score":1,"number_of_records":1000}}

get model req:
http://localhost:8080/model_selection/v1/get_models?client_name=QMA&task_name=CLASSIFICATION&doc_type_name=EMAIL

resp:
{
    "models": [
        {
            "model_type_name": "BERTCLASSIFICATION",
            "model_name": "CLASSIFICATION_QMA_EMAIL_BERTCLASSIFICATION",
            "is_model_used_for_prediction": "N",
            "model_metrics": "{\"recall\":0.8,\"number_of_records\":1001,\"f1score\":1,\"precisicion\":0.99}"
        }
    ]
}


select model:
http://localhost:8080/model_selection/v1/select_model


{"model_name":"CLASSIFICATION_QMA_EMAIL_BERTCLASSIFICATION"}