package connection;

import com.example.demo.connection.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionFactoryTest {

    @Test
    public void deveRealizarConexaoSemExcecao(){
        Connection connection = ConnectionFactory.getConnection();
    }

}
