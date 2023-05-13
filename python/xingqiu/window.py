import sys
from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QWidget, QLabel, QLineEdit, QPushButton, QVBoxLayout, QHBoxLayout


class LoginWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Login")
        self.resize(300, 150)

        # 创建标签和文本框
        self.username_label = QLabel("Username:")
        self.username_edit = QLineEdit()

        self.password_label = QLabel("Password:")
        self.password_edit = QLineEdit()
        self.password_edit.setEchoMode(QLineEdit.EchoMode.Password)

        # 创建登录按钮
        self.login_button = QPushButton("Login", clicked=self.login)

        # 创建水平布局，包含标签和文本框
        username_layout = QHBoxLayout()
        username_layout.addWidget(self.username_label)
        username_layout.addWidget(self.username_edit)

        password_layout = QHBoxLayout()
        password_layout.addWidget(self.password_label)
        password_layout.addWidget(self.password_edit)

        # 创建垂直布局，包含所有控件
        main_layout = QVBoxLayout()
        main_layout.addLayout(username_layout)
        main_layout.addLayout(password_layout)
        main_layout.addWidget(self.login_button)

        self.setLayout(main_layout)

    def login(self):
        # 在这里添加登录功能的代码
        username = self.username_edit.text()
        password = self.password_edit.text()
        print(f"Username: {username}, Password: {password}")



if __name__ == "__main__":
    app = QApplication(sys.argv)
    login_window = LoginWindow()
    login_window.show()
    sys.exit(app.exec())