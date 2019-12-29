from twilio.rest import Client


# Your Account Sid and Auth Token from twilio.com/console
# DANGER! This is insecure. See http://twil.io/secure
account_sid = 'AC826fa27807aefe45abc211455af15493'
auth_token = 'f7c356daa96647c32991603ca068b420'
client = Client(account_sid, auth_token)

def send_message(message):
    message = client.messages \
                    .create(
                        body=f"{message}",
                        from_='+12028048563',
                        to='+8615612856610'
                    )
    print(message.error_code)

