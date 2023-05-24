public class ProducerConsumerExample {
    public static void main(String[] args) {
        Drop drop = new Drop(5000);
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}