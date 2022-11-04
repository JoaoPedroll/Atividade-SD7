package fanout;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumidor {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        channel.queueDelete("teste");

        String queueName = "teste";

        channel.queueDeclare(queueName, false, false, false, null);

        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println("Ligado, esperando menssagens...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), "UTF-8");

            int valor = Integer.parseInt(message);

            System.out.println("Valor recebido dobrado: " + valor*2);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}