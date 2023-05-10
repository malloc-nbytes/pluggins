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
        self.wildcard = False

    def find_item(self, name):
        for i in range(0, len(self.items)):
            if self.items[i].name == name:
                return i
        return -1

    def dump(self):
        print(self.name)
        for item in self.items:
            print(f"  {item.name}")
            print(f"    {item.elems}")
        print()

def usage():
    print("usage: python3 main.py <edit_file.txt> <.yml file>")
    sys.exit(1)


def simplify_line(line):
    line = line.split(' ')
    line = [elem for elem in line if elem != '']
    line[0] = line[0][:-1]
    return line


def add_to_res(line, item, idx):
    res = None
    if item.elems[idx] == '|':
        res = line[1]
    elif item.elems[idx][0] == '+':
        res = str(float(line[1]) + float(item.elems[idx][1:]))
    elif item.elems[idx][0] == '-':
        res = str(float(line[1]) - float(item.elems[idx][1:]))
    else:
        res = item.elems[idx]
    res += '\n'
    return res


def iter_yml_file(filepath, action):
    new_file = open("output.txt", "w")
    res = ""
    data = filepath.read()
    data = data.split('\n')
    found_action = False
    idx = -1

    for i in range(0, len(data) - 1):
        line = simplify_line(data[i])
        if found_action:
            if len(line) == 1:
                idx = action.find_item(line[0])
                if idx == -1 and action.wildcard:
                    idx = action.find_item('*')
            elif idx != -1:
                item = action.items[idx]
                res += (' ' * 6) + line[0] + ": "
                if line[0] == "income":
                    res += add_to_res(line, item, 0)
                elif line[0] == "points":
                    res += add_to_res(line, item, 1)
                else:
                    res += add_to_res(line, item, 2)
                continue
        elif len(line) == 1 and line[0] == action.name:
            found_action = True
        res += data[i]
        res += '\n'

    new_file.write(res)
    new_file.close()
    print(res)

def parse_edit_file(filepath):
    data = filepath.read().split('\n')
    data = [line.split() for line in data]
    action = Action(data[0][0])
    items = []

    for i in range(1, len(data) - 1):
        line = data[i]
        if len(line) == 2:
            item = Item()
            item.name = line[0]
            if item.name == '*':
                action.wildcard = True
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
