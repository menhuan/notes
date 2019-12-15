import socket 

def link_udp_client():
    socket_client = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
    content = b"i am student"
    addr = ("127.0.0.1",10002)
    socket_client.sendto(content,addr)
    socket_client.close()


if __name__ == '__main__':
    link_udp_client()
    