const amqp = require('amqplib');
const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

async function startServiceA() {
    const connection = await amqp.connect('amqp://localhost');
    const channel = await connection.createChannel();

    const queueAtoB = 'queue_a_to_b';
    const queueBtoA = 'queue_b_to_a';

    await channel.assertQueue(queueAtoB, { durable: false });
    await channel.assertQueue(queueBtoA, { durable: false });

    console.log('--- SERVICE A ĐÃ SẴN SÀNG ---');

    // Lắng nghe tin nhắn từ B
    channel.consume(queueBtoA, (msg) => {
        const data = JSON.parse(msg.content.toString());
        console.log(`\n[Service B]: ${data.text}`);
        process.stdout.write('> Bạn (A): '); // Giữ dấu nhắc nhập liệu
    }, { noAck: true });

    // Hàm gửi tin nhắn
    const askAndSend = () => {
        rl.question('> Bạn (A): ', (answer) => {
            const message = { text: answer, from: 'A' };
            channel.sendToQueue(queueAtoB, Buffer.from(JSON.stringify(message)));
            askAndSend(); // Tiếp tục đợi nhập tin tiếp theo
        });
    };

    askAndSend();
}

startServiceA();