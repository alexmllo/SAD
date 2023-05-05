const { Socket } = require("socket.io")

const io = require("socket.io")(3000, {
    cors: {
        origin: "*"
    }
})

const users = {}
const rooms = {}

io.on('connection', (socket) => {
    socket.on('name-user', (name, room) => {
        console.log(room)
        users[socket.id] = name
        rooms[socket.id] = room
        socket.join(room)
        socket.to(room).emit('user-connected', name)
    })
    socket.on('send-chat-message', message => {
        socket.to(rooms[socket.id]).emit('chat-message', { message: message, name: users[socket.id] })
    })
    socket.on('disconnect', () => {
        if (users[socket.id] != null) {
            socket.to(rooms[socket.id]).emit('user-disconnect', users[socket.id])
            socket.leave(rooms[socket.id])
            delete users[socket.id]
            delete rooms[socket.id]
        }
    })
})