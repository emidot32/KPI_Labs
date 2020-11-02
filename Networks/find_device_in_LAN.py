import requests
import scapy.config
import scapy.layers.l2
import math
import os
import sys
import time

from config import *


def send_message(chat_id, message):
    send_text = 'https://api.telegram.org/bot' + TOKEN + '/sendMessage?chat_id=' + str(
        chat_id) + '&parse_mode=Markdown&text=' + message
    requests.get(send_text)


def pretty_output(results: list):
    output = ""
    for i in range(len(results)):
        output += f"{i + 1}. Device: \n    MAC: {results[i][0]},\n    IP: {results[i][1]},\n    Manufacturer: {results[i][2]}\n"
    return output


def reformat_notation(bytes_network, bytes_netmask):
    network = scapy.utils.ltoa(bytes_network)
    netmask = 32 - int(round(math.log(0xFFFFFFFF - bytes_netmask, 2)))
    net = "%s/%s" % (network, netmask)
    return net


def scan():
    results = []
    for network, netmask, _, interface, address, _ in scapy.config.conf.route.routes:
        if (interface == 'wlp3s0' or interface == 'enp2s0') and netmask != 0:
            print(f"Network: {network}, Netmask: {netmask}, _: {_}, Interface: {interface}, Address: {address}\n")
            net = reformat_notation(network, netmask)
            ans, unans = scapy.layers.l2.arping(net, iface=interface)

            for s, r in ans.res:
                conf = scapy.config.Conf()
                manuf = conf.manufdb._get_short_manuf(r.src)
                manuf = "unknown" if manuf == r.src else manuf
                results.append((r.src, r.psrc, manuf))
    return results


def main():
    if os.geteuid() != 0:
        print('You need to be root to run this script')
        sys.exit(1)

    results = scan()
    print(results)
    send_message(CHAT_ID, "Your network consists of these devices:\n" + pretty_output(results))
    cur_time = time.clock()

    while 1:
        if time.clock() - cur_time > 30:
            new_results = scan()
            if len(new_results) > len(results):
                diffs = set(new_results) - set(results)
                print(f"Пришел: {diffs}")
                send_message(CHAT_ID, "New connected devices:\n" + pretty_output(list(diffs)))
            elif len(new_results) < len(results):
                diffs = set(results) - set(new_results)
                print(f"Ушел: {diffs}")
                send_message(CHAT_ID, "Devices are disconnected:\n" + pretty_output(list(diffs)))
            results = new_results
            cur_time = time.clock()

        if time.clock() - cur_time > 300 and input("Enter 'stop' for scanning network: ") == 'stop':
            break


main()
