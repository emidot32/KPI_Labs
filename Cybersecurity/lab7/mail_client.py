import imaplib
import email
import smtplib
from lab7.configs import *

imap_session = imaplib.IMAP4_SSL('imap.gmail.com')
smtp_session = smtplib.SMTP('smtp.gmail.com', 587)


def get_last_msg(subject):
    imap_session.login(LOGIN, PASSWORD)
    imap_session.select("inbox")
    result, data = imap_session.search(None, 'FROM', OPPONENT_MAIL, 'SUBJECT', subject)

    ids = data[0]
    id_list = ids.split()
    latest_email_id = id_list[-1]

    result, data = imap_session.fetch(latest_email_id, "(RFC822)")

    raw = email.message_from_bytes(data[0][1])
    return get_text(raw).decode('utf-8')


def get_text(msg):
    if msg.is_multipart():
        return get_text(msg.get_payload(0))
    else:
        return msg.get_payload(None, True)


def send_msg(subject, text):
    new_message = email.message.Message()
    new_message.set_unixfrom('pymotw')
    new_message['Subject'] = subject
    new_message['From'] = LOGIN
    new_message['To'] = OPPONENT_MAIL
    new_message.set_payload(text)
    smtp_session.starttls()
    smtp_session.login(LOGIN, PASSWORD)
    text = new_message.as_string()
    smtp_session.sendmail(LOGIN, OPPONENT_MAIL, text.encode('utf-8'))
    smtp_session.quit()

