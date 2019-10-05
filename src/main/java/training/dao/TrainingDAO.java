package training.dao;

import org.springframework.stereotype.Component;

import java.util.*;
import java.sql.*;

/**
 * Created by fay on 3/10/19.
 */

@Component
public class TrainingDAO extends GenericDAO {
    public List<String> getModelName(String taskName, String clientName, String docTypeName) {
        Connection connection = null;
        Statement statement = null;
        List<String> modelNames = new ArrayList<String>();
        try {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
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
            " and dt.doc_type_name =  '" + docTypeName + "'";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                maxTrainingProcessId = resultSet.getInt(1);
                System.out.println(maxTrainingProcessId);

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


    public static void main(String args[]) {
        TrainingDAO trainingDAO= new TrainingDAO();
        System.out.print(trainingDAO.getModelTypeName("CLASSIFICATION_QMA_EMAIL_BERTCLASSIFICATION"));
//        List<String> modelNames = TrainingDAO.getModelName("CLASSIFICATION", "QMA", "EMAIL");
//        for (String modelName : modelNames) {
//            TrainingDAO.createTrainingProcess(modelName);
//            TrainingDAO.getMaxTrainingProcessId(modelName, "QMA","EMAIL");
//        }
    }

}
