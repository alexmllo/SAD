const socket = io('http://localhost:3000')
const messageContainer = document.getElementById('message-container')

const messageForm = document.getElementById('send-container')
const messageInput = document.getElementById('message-input')

const nameModal = document.getElementById('name-window')
const nameInput = document.getElementById('name-input')
const submitNameButton = document.getElementById('submit-name')
const roomInput =document.getElementById('room-input')

nameModal.style.display = 'block'
submitNameButton.addEventListener('click', () => {
    const name = nameInput.value
    const room = roomInput.value
    while(name == null || name == ''){
        name = nameInput.value
    }
    nameModal.style.display = 'none'
    appendMessage(`You joined the chat room *${room}*`, '#30ff24')
    socket.emit('name-user', name, room)
    nameInput.value = ''
    roomInput.value = ''
})

socket.on('chat-message', data => {
    appendMessage(`${data.name}: ${data.message}`, '#000000')
})

socket.on('user-connected', name => {
    appendMessage(`${name} connected`, '#30ff24')
})

socket.on('user-disconnect', name => {
    appendMessage(`${name} disconnected`, '#ff0000')
})

/* Agregamos el evento submit al formulario send-container que contiene los campos de entrada de texto y el boton de envio de mensajes */
messageForm.addEventListener('click', e => {
    e.preventDefault()  /* Para que no se recargue la pagina */
    const message = messageInput.value
    if (message == '') return
    appendMessage(`You: ${message}`, '#00d7ff')
    socket.emit('send-chat-message', message)
    messageInput.value = ''
})

/* Para mostrar los mensajes por pantalla */
function appendMessage(message, color) {
    /* Creamos elemento div en el dominio del html */
    const messageElement = document.createElement('div')
    /* Le asignamos el mensage recivido como texto interno */
    messageElement.innerText = message
    /* Agragamos el elemento al contenedor de mensages */
    messageElement.style.color = color
    messageContainer.append(messageElement)
    window.scrollBy(0, messageContainer.scrollHeight)
}