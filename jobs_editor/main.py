#!/bin/python3

from os import sys

class Item:
    def __init__(self):
        self.name = None
        self.elems = []


class Action:
    def __init__(self, name, items=[]):
        self.name = name
        self.items = items

    def dump(self):
        print(self.name)
        for item in self.items:
            print(f"  {item.name}")
            print(f"    {item.elems}")
        print()

def usage():
    print("usage: python3 main.py <edit_file.txt> <.yml file>")
    sys.exit(1)


def is_obj(line):
    return len(line) == 2


def iter_yml_file(filepath, action):
    data = filepath.read()
    data = data.split('\n')
    for line in data:
        line = line.split(' ')
        line = [elem for elem in line if elem != '']
        # Remove the `:` from entries.
        line[0] = line[0][:-1]
        if line[0] == action.name:
            pass


def parse_edit_file(filepath):
    data = filepath.read().split('\n')
    data = [line.split() for line in data]
    action = Action(data[0][0])
    items = []

    for i in range(1, len(data) - 1):
        line = data[i]
        if is_obj(line):
            item = Item()
            item.name = line[0]
            j = i + 1
            while True:
                line = data[j]
                if line[0] == '}':
                    action.items.append(item)
                    break
                item.elems.append(line[0])
                j += 1

    return action

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("ERROR: incorrect file args provided.")
        usage()

    edit_file_arg = sys.argv[1]
    yml_file_arg = sys.argv[2]

    if yml_file_arg.split('.')[1] != "yml":
        print("ERROR: the second file must end with `.yml`.")
        usage()

    edit_file = open(edit_file_arg)
    yml_file = open(yml_file_arg)

    iter_yml_file(yml_file, parse_edit_file(edit_file))

    edit_file.close()
    yml_file.close()
