import socket



def client_link():
    socket_client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    address = ("127.0.0.1",10001)
    socket_client.connect(address)
    content = b"i am student"
    socket_client.send(content) 

if __name__ == '__main__':
    client_link()
    