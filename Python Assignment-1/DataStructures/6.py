stack = []
def push(x):
    stack.append(x)
def pop():
    return stack.pop()
def peek():
    return stack[-1]