phonebook = {}
def add(name, num):
    phonebook[name] = num
def get(name):
    return phonebook.get(name)