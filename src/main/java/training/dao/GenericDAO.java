package training.dao;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by fay on 3/10/19.
 */
public class GenericDAO {
    @Value("${transformer.db.url}")
    protected  String URL;
    @Value("${transformer.db.user}")
    protected String USER;
    @Value("${transformer.db.password}")
    protected String PASSWORD;
}
