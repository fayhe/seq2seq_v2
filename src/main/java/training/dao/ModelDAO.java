package training.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.*;
import java.sql.*;

/**
 * Created by fay on 3/10/19.
 */

@Component
//@PropertySource("classpath:application.properties")
public class ModelDAO extends GenericDAO {

    public static final String STATUS_COMPLETED = "COMPLETED";

    public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    public List<String> getModelName(String taskName, String clientName, String docTypeName) {
        Connection connection = null;
        Statement statement = null;
        List<String> modelNames = new ArrayList<String>();
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("class loaded");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("connection get");
            String sql = "select m.model_name " +
                    "from model m, model_type mt, doc_type dt " +
                    "where m.model_type_name = mt.model_type_name " +
                    "and dt.doc_type_name = m.doc_type_name " +
                    "and mt.task_name = '" + taskName + "' " +
                    "and dt.client_name = '" + clientName + "' " +
                    "and dt.doc_type_name =  '" + docTypeName + "' ";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                System.out.println(name);
                modelNames.add(name);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                    System.out.println("connection release");
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return modelNames;
    }


    public String getModelTypeName(String modelName) {
        Connection connection = null;
        Statement statement = null;
        String modeTypelName = "";
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "select m.model_type_name " +
                    "from model m " +
                    "where m.model_name = '" + modelName + "'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                System.out.println(name);
                modeTypelName = name;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return modeTypelName;
    }



    public void createTrainingProcess(String modelName) {
        Connection connection = null;
        Statement statement = null;
        List<String> modelNames = new ArrayList<String>();
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "INSERT INTO training_process(model_name,status)" +
                    " VALUES('" + modelName + "', 'IN_PROGRESS')";
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {

                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void updateTrainingProcess(String status, Integer processId, String modelMetricsStr) {
        Connection connection = null;
        Statement statement = null;
        List<String> modelNames = new ArrayList<String>();
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "UPDATE training_process" +
                    " SET status='" + status + "'," +
                    " trained_model_metrics ='" + modelMetricsStr + "' " +
                    " WHERE training_process_id = " + processId ;
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {

                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void updateModelMetrics(String modelName, String modelMetricsStr) {
        Connection connection = null;
        Statement statement = null;
        List<String> modelNames = new ArrayList<String>();
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "UPDATE model" +
                    " SET model_metrics='" + modelMetricsStr + "'" +
                    " WHERE model_name = '" + modelName + "'" ;
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {

                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void selectModel(String modelName) {
        Connection connection = null;
        Statement statement = null;
        List<String> modelNames = new ArrayList<String>();
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "UPDATE model" +
                    " SET is_model_used_for_prediction= 'Y' " +
                    " WHERE model_name = '" + modelName + "'" ;
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {

                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }



    public Integer getMaxTrainingProcessId(String modelName , String clientName, String docTypeName) {
        Connection connection = null;
        Statement statement = null;
        Integer maxTrainingProcessId = 0 ;
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "  select max(tp.training_process_id)" +
            " from model m, training_process tp, doc_type dt" +
            " where tp.model_name = m.model_name" +
            " and dt.doc_type_name = m.doc_type_name" +
            " and tp.model_name = '" + modelName + "'" +
            " and dt.client_name = '" + clientName + "'" +
            " and dt.doc_type_name =  '" + docTypeName + "'" +
            " and tp.status = '" + STATUS_IN_PROGRESS  + "'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                maxTrainingProcessId = resultSet.getInt(1);
                System.out.println("max process:" + maxTrainingProcessId);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return maxTrainingProcessId;
    }


    public List<Map<String,Object>> getModels(String taskName, String clientName, String docTypeName) {
        Connection connection = null;
        Statement statement = null;
        List<Map<String,Object>> models =  new <HashMap<String,Object>>ArrayList();
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "select m.model_name, m.is_model_used_for_prediction," +
                    " m.model_metrics, m.model_type_name " +
                    "from model m, model_type mt, doc_type dt " +
                    "where m.model_type_name = mt.model_type_name " +
                    "and dt.doc_type_name = m.doc_type_name " +
                    "and mt.task_name = '" + taskName + "' " +
                    "and dt.client_name = '" + clientName + "' " +
                    "and dt.doc_type_name =  '" + docTypeName + "' ";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String modelName = resultSet.getString(1);
                String isUsedForPrediction = resultSet.getString(2);
                String modelMetrics = resultSet.getString(3);
                String modelTypeName = resultSet.getString(4);
                Map model = new HashMap<String, Object>();
                model.put("model_name", modelName);
                model.put("model_type_name", modelTypeName);
                model.put("is_model_used_for_prediction", isUsedForPrediction);
                model.put("model_metrics", modelMetrics);
                models.add(model);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return models;
    }




    public static void main(String args[]) {

        ModelDAO trainingDAO= new ModelDAO();
        trainingDAO.selectModel("CLASSIFICATION_QMA_EMAIL_BERTCLASSIFICATION");
//        List<Map<String,Object>> models = trainingDAO.getModels("CLASSIFICATION", "QMA", "EMAIL");
//        for(Map<String,Object> model : models ){
//            System.out.println(model.get("model_name"));
//            System.out.println(model.get("model_type_name"));
//            System.out.println(model.get("is_model_used_for_prediction"));
//            System.out.println(model.get("model_metrics"));
//        }
        System.out.println();

//        System.out.print(trainingDAO.getModelTypeName("CLASSIFICATION_QMA_EMAIL_BERTCLASSIFICATION"));
//        List<String> modelNames = trainingDAO.getModelName("CLASSIFICATION", "QMA", "EMAIL");
//        for (String modelName : modelNames) {
//            System.out.println("model name:" + modelName);
//            int processId = trainingDAO.getMaxTrainingProcessId(modelName, "QMA","EMAIL");
//            System.out.println("process id:" + processId);
//            trainingDAO.updateTrainingProcess(ModelDAO.STATUS_COMPLETED, processId,"");
//        }

//
//        List<String> modelNames = trainingDAO.getModelName("CLASSIFICATION", "QMA", "EMAIL");
//        for (String modelName : modelNames) {
//            trainingDAO.createTrainingProcess(modelName);
//            trainingDAO.getMaxTrainingProcessId(modelName, "QMA","EMAIL");
//        }
    }

}
