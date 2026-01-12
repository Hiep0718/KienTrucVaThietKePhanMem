const amqp = require('amqplib');
const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

async function startServiceB() {
    const connection = await amqp.connect('amqp://localhost');
    const channel = await connection.createChannel();

    const queueAtoB = 'queue_a_to_b';
    const queueBtoA = 'queue_b_to_a';

    await channel.assertQueue(queueAtoB, { durable: false });
    await channel.assertQueue(queueBtoA, { durable: false });

    console.log('--- SERVICE B ĐÃ SẴN SÀNG ---');

    // Lắng nghe tin nhắn từ A
    channel.consume(queueAtoB, (msg) => {
        const data = JSON.parse(msg.content.toString());
        console.log(`\n[Service A]: ${data.text}`);
        process.stdout.write('> Bạn (B): ');
    }, { noAck: true });

    // Hàm gửi tin nhắn
    const askAndSend = () => {
        rl.question('> Bạn (B): ', (answer) => {
            const message = { text: answer, from: 'B' };
            channel.sendToQueue(queueBtoA, Buffer.from(JSON.stringify(message)));
            askAndSend();
        });
    };

    askAndSend();
}

startServiceB();