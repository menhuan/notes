import json
import smtplib
from email.mime.text import MIMEText

## 收件邮箱 可以使qq邮箱，也可以是网易邮箱
from time import sleep

from tencentcloud.common import credential
from tencentcloud.common.profile.client_profile import ClientProfile
from tencentcloud.common.profile.http_profile import HttpProfile
from tencentcloud.sms.v20190711 import sms_client, models

from sixteen.constant import EMAIL_LIST
from sixteen.util.caches import get_list, acqure_list_key_len, add_list

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


def __init_email():
    values = ["lianhedianke@163.com", "15732029557"]
    add_list(EMAIL_LIST, values)


def __acquire_send_email(key):
    # 从redis 中拿取 邮箱
    email = get_list(key)
    return email


def send_notice(messages, phone, email_address):
    send_email(f"您设定的{messages[0]}:{messages[1]}已经达到，现在价格是{messages[2]}，请注意查看修改", email_address)
    send_sms(messages, [phone])


def __send_email_message(content, from_sender, password, to_address):
    # 指定编码，防止接收方数据乱码
    try:
        msg = MIMEText(content, 'plain', 'utf-8')
        msg['From'] = from_sender
        msg['To'] = to_address
        # 设置邮件主题
        msg['Subject'] = '价格提醒'
        server = smtplib.SMTP_SSL(smtp_server, smtp_port)
        server.login(from_sender, password)
        server.sendmail(from_sender, to_address, msg.as_string())
        print(msg.as_string())
        server.quit()
        return True
    except Exception as e:
        print("send email failed", e)
        return False


def send_email(message, receiver):
    send_email = None
    length = acqure_list_key_len(EMAIL_LIST)
    if length <= 0:
        __init_email()
    while not send_email:
        send_email = __acquire_send_email(EMAIL_LIST)
        if not send_email:
            sleep(2)
            print("acquire email failed,please wait to acquire email")
        else:
            return __send_email_message(message, send_email[0], send_email[1], receiver)


def __acquire_sms_config():
    # 从数据库拿
    return "AKIDziBMGtMfpkTaR2zik24G8kBNK40hkJEv", "ZrX7SbR2Oa2DguuEI6hyzmLdfSP6Xif9"


def send_sms(content, phones):
    try:
        secret_id, secret_key = __acquire_sms_config()
        cred = credential.Credential(secret_id, secret_key)
        # 实例化要请求产品(以cvm为例)的client对象
        httpProfile = HttpProfile()
        httpProfile.reqMethod = "POST"  # post请求(默认为post请求)
        httpProfile.reqTimeout = 30  # 请求超时时间，单位为秒(默认60秒)
        httpProfile.endpoint = "sms.tencentcloudapi.com"  # 指定接入地域域名(默认就近接入)

        clientProfile = ClientProfile()
        clientProfile.signMethod = "TC3-HMAC-SHA256"  # 指定签名算法
        clientProfile.language = "en-US"
        clientProfile.httpProfile = httpProfile

        client = sms_client.SmsClient(cred, "ap-beijing", clientProfile)
        # 实例化一个请求对象
        req = models.SendSmsRequest()
        req.SmsSdkAppid = "1400301649"
        req.Sign = "爱极客"
        req.PhoneNumberSet = phones
        req.TemplateID = "599228"
        req.TemplateParamSet = content
        resp = client.SendSms(req)
        result = resp.to_json_string(indent=2)
        print(resp.to_json_string(indent=2))
        for status in json.loads(resp.to_json_string(indent=2)).get("SendStatusSet", {}):
            code = status.get("Code")
            if code.lower() == "ok":
                continue
            else:
                return False
        return True
    except Exception as e:
        print(e)
        return False


if __name__ == '__main__':
    send_sms(["test",'tress','12312'], ["+8615612856610"])
   # send_email("message", "963633167@qq.com")
