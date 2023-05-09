#!/bin/python3

from os import sys

class Item:
    def __init__(self, name, income, points, experience):
        self.name = name
        self.income = income
        self.experience = experience


class Job:
    def __init__(self, name, rules=None):
        self.name = name
        self.rules = rules


class Rule:
    def __init__(self, name):
        self.name = name


def usage():
    print("usage: python3 main.py <edit_file.txt> <.yml file>")
    sys.exit(1)


if __name__ == "__main__":
    if len(sys.argv) != 3:
        usage()



    edit_file = open(sys.argv[1])
    edit_data = edit_file.read()
    yml_file = open(sys.argv[2])
    yml_data = yml_file.read()




    edit_file.close()
    yml_file.close()
