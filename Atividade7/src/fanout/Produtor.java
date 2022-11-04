package fanout;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Produtor {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String valor = "10";

            channel.basicPublish(EXCHANGE_NAME, "", null, valor.getBytes("UTF-8"));
            System.out.println("Valor enviado: " + valor);
        }
    }

}