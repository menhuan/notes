import email
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.mime.image import MIMEImage
import poplib
import imaplib
from django.conf import settings

## 收件邮箱 可以使qq邮箱，也可以是网易邮箱
to_address = "963633167@qq.com"

## 发件人

from_sender = "lianhedianke@163.com"
user = "lianhedianke@163.com"
# 设置发送密码
password = "15732029557"

smtp_server = "smtp.163.com"
smtp_port = 465

pop_server = "pop.163.com"
imap_server = "imap.163.com"


def send_txt_email():
    # 指定编码，防止接收方数据乱码
    msg = MIMEText('this is a email', 'plain', 'utf-8')
    msg['From'] = from_sender
    msg['To'] = to_address
    # 设置邮件主题
    msg['Subject'] = '这是我学习发送的第一个邮件'

    server = smtplib.SMTP_SSL(smtp_server, smtp_port)
    server.login(from_sender, password)
    server.sendmail(from_sender, to_address, msg.as_string())
    print(msg.as_string())
    server.quit()


def send_file_email():
    # 设置好文件内容
    message = MIMEMultipart()
    message['From'] = from_sender
    message['To'] = to_address
    message['Subject'] = "this is title"

    # 添加html形式的文本内容
    with open("test_html.html", 'r') as f:
        html_content = f.read()
        html = MIMEText(str(html_content), 'html', 'utf-8')
    message.attach(html)

    with open('/Users/ruiqi/Downloads/默认文件1584974964625.png', 'rb') as h:
        image = MIMEImage(h.read())
        image['Content-Type'] = 'application/octest-stream'
        image['Content-Disposition'] = 'attachment;filename="1.png"'
        # image.add_header('Content-ID', '<image1>')
        message.attach(image)

    with open('/Users/ruiqi/Downloads/默认文件1584974964625.png', 'rb') as h:
        image = MIMEImage(h.read())
        image['Content-Type'] = 'application/octest-stream'
        image['Content-Disposition'] = 'attachment;filename="1.png"'
        image.add_header('Content-ID', '<image1>')
        message.attach(image)

    server = smtplib.SMTP_SSL(smtp_server, smtp_port)
    server.login(from_sender, password)
    server.sendmail(from_sender, to_address, message.as_string())
    server.quit()
    print("发送完毕")


def receive_pop_email():
    pop = poplib.POP3_SSL(host=pop_server)
    pop.set_debuglevel(1)
    pop.user(user)
    pop.pass_(password)
    # 列出邮件信息，包含邮件的大小和ID
    pop_reslut = pop.list()
    print(pop_reslut)
    print("==========")
    print(pop_reslut[2])
    pop_stat = pop.stat()
    # 第一项是代表的邮件ID
    for index in range(1, pop_stat[0]):
        print(pop.top(index, 0))
        if index > 5:
            break

    print(pop.retr(1))
    for line_content in pop.retr(1):
        print(line_content)


def receive_imap_email():
    imap = imaplib.IMAP4_SSL(imap_server)
    imap.login(user, password)
    response, data = imap.list()
    print(response)
    print(data[0])
    print(data[1:3])
    response, data = imap.select('INBOX')
    print(response)
    print(data)
    imap.select(settings.EMAIL_GATEWAY_IMAP_FOLDER)
    status, num_ids_data = imap.search(None, 'ALL')
    for id in ids:
        res, featch_data = imap.fetch(str.encode(str(id)), '(RFC822)')
        print(res)
        if featch_data[0] is not None:
            for part in email.message_from_string(str(featch_data[0][1])).walk():
                print(part.get_payload(decode=True))


if __name__ == '__main__':
    receive_imap_email()
