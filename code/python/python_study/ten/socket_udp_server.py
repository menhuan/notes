

import socket
from time import sleep

def link_udp_server():
    socket_server = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
    port = 10002
    address = ("0.0.0.0",port)
    socket_server.bind(address)
    while True:
        data,addr = socket_server.recvfrom(1024)
        print(f"data is {data}")
        sleep(1)