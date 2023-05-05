const { Socket } = require("socket.io")

const io = require("socket.io")(3000, {
    cors: {
        origin: "*"
    }
})

const users = {}

/* La funcion se ejecuta cada vez que se conecta un cliente y le dara una instancia socket a cada uno */
io.on('connection', (socket) => {
    /* Recive el evento de un nuevo usuario, hace un broadcast emit a todos los usuarios menos al que se acab de unir */
    socket.on('name-user', name => {
        users[socket.id] = name
        socket.broadcast.emit('user-connected', name)
    })
    /* Ahora el server tiene que coger el evento de un mensaje enviado por un cliente */
    socket.on('send-chat-message', message => {
        /* Hace un emit a todos los clientes menos al que lo ha enviado, este emit debe ser cogido por los clientes */
        socket.broadcast.emit('chat-message', { message: message, name: users[socket.id] })
    })
    socket.on('disconnect', () => {
        if (users[socket.id] != null) {
            socket.broadcast.emit('user-disconnect', users[socket.id])
            delete users[socket.id]
        }
    })
})