import sys
from PyQt6.QtWidgets import QApplication, QWidget, QFormLayout, QLineEdit, QPushButton, QDateTimeEdit,QFileDialog,QMessageBox
from PyQt6.QtCore import QDateTime
import collect as collect
from PyQt6.QtCore import QThreadPool, QRunnable, pyqtSlot
import asyncio

class MyTask(QRunnable):
    def __init__(self, data):
        super().__init__()
        self.data = data

    @pyqtSlot()
    def run(self):
        asyncio.run(self.do_task())

    async def do_task(self):
        collect.download(self.data[0],self.data[1],self.data[2],self.data[3])

class Form(QWidget):
    def __init__(self):
        super().__init__()

        self.initUI()
        self.setFixedSize(1080,800)
        self.submitButton = QPushButton("Submit", self)


    def initUI(self):
        layout = QFormLayout()

        # Add form fields
        group_id = QLineEdit()
        group_id.setText("例子:48844284281428") # Set default value
        layout.addRow("请输入链接中的groupid:", group_id)

        # Add file path field
        file_path_field = QLineEdit()
        file_path_button = QPushButton("请选择保存的pdf目录")
        file_path_button.clicked.connect(lambda: self.select_file(file_path_field))
        layout.addRow("保存路径:", file_path_field)
        layout.addRow(file_path_button)
        
        # Add date/time field
        date_start_time_field = QDateTimeEdit()
        date_start_time_field.setMinimumDateTime(QDateTime.currentDateTime().addDays(-600)) # Set minimum date to 10 days ago
        date_start_time_field.setDisplayFormat("yyyy-MM-dd HH:mm:ss")
        date_start_time_field.setDateTime(QDateTime.currentDateTime().addDays(-10)) # Set default date/time to current date/time
        layout.addRow("开始时间:", date_start_time_field)
        
        date_end_time_field = QDateTimeEdit()
        date_end_time_field.setMinimumDateTime(QDateTime.currentDateTime().addDays(10)) # Set minimum date to 10 days ago
        date_end_time_field.setDisplayFormat("yyyy-MM-dd HH:mm:ss")
        date_end_time_field.setDateTime(QDateTime.currentDateTime()) # Set default date/time to current date/time
        layout.addRow("结束时间:", date_end_time_field)

        # Add submit button
        submit_button = QPushButton("Submit")
        submit_button.clicked.connect(lambda: self.submit(group_id.text(), file_path_field.text(), date_start_time_field.dateTime(),date_end_time_field.dateTime()))

        layout.addRow(submit_button)

        self.setLayout(layout)
        self.show()
        QMessageBox.information(self, "Success", "提交任务成功,正在执行中,请稍后查看结果")

    def select_file(self, field):
        file_path = QFileDialog.getExistingDirectory(self,"Select Directory")
        if file_path:
            field.setText(file_path)

    def submit(self, group_id, pdf_save_path,start_time,end_time):
        # Call Python method with form data
        data = [group_id,pdf_save_path,start_time,end_time]
        task = MyTask(data)
        QThreadPool.globalInstance().start(task)
    
    @pyqtSlot()
    def on_task_finished(self):
        # 在这里执行任务完成后的操作
        pass

if __name__ == '__main__':
    app = QApplication(sys.argv)
    form = Form()
    sys.exit(app.exec())
