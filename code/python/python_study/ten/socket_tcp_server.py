import socket

"""
tcp 服务端开发
"""


def tcp_server_link():
    socket_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 设置端口
    port = 10001
    socket_server.bind(("0.0.0.0", port))
    # 进行监听，并且也必须制定等待连接的最大数量
    socket_server.listen(10)
    # 超过这个数量的连接会进行排队
    while True:
        link, addr = socket_server.accept()
        # 处理返回的连接
        while True:
            data = link.recv(1024)
            if not data or data.decode('utf-8') == 'exit':
                break
            print(data.decode('utf-8'))

if __name__ == '__main__':
    tcp_server_link()
    