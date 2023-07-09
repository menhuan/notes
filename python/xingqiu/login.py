from PyQt6.QtWidgets import QApplication, QMainWindow, QLabel, QLineEdit, QPushButton,QMessageBox
import os
import form as form
class LoginWindow(QMainWindow):
    def __init__(self):
        super().__init__()

        self.setWindowTitle("登录")
        self.setGeometry(100, 100, 300, 200)

        self.username_label = QLabel("用户名:", self)
        self.username_label.move(50, 50)
        self.username_input = QLineEdit(self)
        self.username_input.move(120, 50)

        self.password_label = QLabel("密码:", self)
        self.password_label.move(50, 80)
        self.password_input = QLineEdit(self)
        self.password_input.setEchoMode(QLineEdit.EchoMode.Password)
        self.password_input.move(120, 80)

        self.login_button = QPushButton("登录", self)
        self.login_button.move(120, 120)
        self.login_button.clicked.connect(self.login)

    def login(self):
        username = self.username_input.text()
        password = self.password_input.text()

        # 获取本地环境变量中的账号信息
        stored_username = os.getenv("USERNAME","test")
        stored_password = os.getenv("PASSWORD","test")

        if username == stored_username and password == stored_password:
            self.open_form()
            print("登录成功")
        else:
            print("登录失败")
            QMessageBox.warning(self, "登录失败", "账号或密码不正确")
    
    def open_form(self):
        self.hide()  # 隐藏登录窗口
        self.form = form.Form()
        self.form.show()
        
if __name__ == "__main__":
    import sys

    app = QApplication(sys.argv)

    window = LoginWindow()
    window.show()

    sys.exit(app.exec())
