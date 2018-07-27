#!/usr/bin/env python

import json
import os
import sys

directory = os.getcwd() + "/" + sys.argv[1]
print ("Stats for" + directory)


def getTxBytes(json):
    return json["networks"]["eth0"]["tx_bytes"]

def getRxBytes(json):
    return json["networks"]["eth0"]["rx_bytes"]


def getCPUUsage(json):
    return json["cpu_stats"]["cpu_usage"]["total_usage"]


with open(directory + "/stats-after.json") as after:
    afterJson = json.load(after)

with open(directory + "/stats-before.json") as before:
    beforeJson = json.load(before)

print "Total CPU Usage in seconds: " + "{:.2f}".format(
    (getCPUUsage(afterJson) - getCPUUsage(beforeJson)) / float(1000000000))

print "Total RX in MB: " + "{:.2f}".format(
    (getRxBytes(afterJson) - getRxBytes(beforeJson)) / (float(1024) * float(1024)))

print "Total TX in MB: " + "{:.2f}".format(
    (getTxBytes(afterJson) - getTxBytes(beforeJson)) / (float(1024) * float(1024)))

print "Total RX+TX in MB: " + "{:.2f}".format(
    (getTxBytes(afterJson) - getTxBytes(beforeJson)) / (float(1024) * float(1024)) + (
                getRxBytes(afterJson) - getRxBytes(beforeJson)) / (float(1024) * float(1024)))
