import time

from Crypto.PublicKey import DSA
from Crypto.Signature import DSS
from Crypto.Hash import SHA256


in_memory_password = ["pass1, ""pass2", "pass3"]


def generate_key():
    key = DSA.generate(2048)
    file = open("public_key.pem", "bw")
    file.write(key.publickey().export_key())
    file.close()
    return key


def sign_message(text):
    key = generate_key()
    start_time = time.perf_counter_ns()
    text = text.encode('utf-8')
    hash_obj = SHA256.new(text)
    signer = DSS.new(key, 'fips-186-3')
    signature = signer.sign(hash_obj)
    print("Час підпису: ", (time.perf_counter_ns() - start_time) / 1000)
    return signature


def verify(signature):
    file = open("public_key.pem", "r")
    flag = False
    start_time = time.perf_counter_ns()
    pub_key = DSA.import_key(file.read())
    verifier = DSS.new(pub_key, 'fips-186-3')
    for password in in_memory_password:
        hash_obj = SHA256.new(password.encode('utf-8'))
        try:
            verifier.verify(hash_obj, signature)
            flag = True
            break
        except ValueError:
            pass
    print("Час верифікації: ", (time.perf_counter_ns() - start_time) / 1000)
    if flag:
        print("You are authenticated!")
    else:
        print("You are not authenticated!")


msg = "pass3pass3pass3pass3pass3"
signature = sign_message(msg)
print(msg)
print(signature)
verify(signature)
