-- insert task data 
INSERT INTO task(task_name) VALUES('CLASSIFICATION');
INSERT INTO task(task_name) VALUES('NER');

-- insert client data  
INSERT INTO client(client_name, status) VALUES('QMA', 'ACTIVE');

-- insert doc_type data  
INSERT INTO doc_type(doc_type_name, client_name) VALUES('EMAIL', 'QMA');
INSERT INTO doc_type(doc_type_name, client_name) VALUES('SWIFT599', 'QMA');

-- insert client_task_doc_type data  
INSERT INTO client_task_doc_type(client_name, task_name, doc_type_name ) VALUES('QMA', 'CLASSIFICATION', 'EMAIL');
INSERT INTO client_task_doc_type(client_name, task_name, doc_type_name ) VALUES('QMA', 'NER', 'EMAIL');
INSERT INTO client_task_doc_type(client_name, task_name, doc_type_name ) VALUES('QMA', 'CLASSIFICATION', 'SWIFT599');
INSERT INTO client_task_doc_type(client_name, task_name, doc_type_name ) VALUES('QMA', 'NER', 'SWIFT599');


-- insert model_type data  
INSERT INTO model_type(model_type_name, task_name ) VALUES('BERTCLASSIFICATION', 'CLASSIFICATION');
INSERT INTO model_type(model_type_name, task_name ) VALUES('BILSTMNER', 'NER');

        
-- insert model data 
INSERT INTO model(model_name, model_type_name, doc_type_name ) VALUES('QMA_EMAIL_CLASSIFICATION_BERTCLASSIFICATION', 'BERTCLASSIFICATION', 'EMAIL');
INSERT INTO model(model_name, model_type_name, doc_type_name ) VALUES('QMA_SWIFT599_CLASSIFICATION_BERTCLASSIFICATION', 'BERTCLASSIFICATION', 'SWIFT599');
INSERT INTO model(model_name, model_type_name, doc_type_name ) VALUES('QMA_EMAIL_NER_BILSTMNER', 'BILSTMNER', 'EMAIL');
INSERT INTO model(model_name, model_type_name, doc_type_name ) VALUES('QMA_SWIFT599_NER_BILSTMNER', 'BILSTMNER', 'SWIFT599');























