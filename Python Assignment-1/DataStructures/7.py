from collections import deque
q = deque()
def enq(x):
    q.append(x)
def deq():
    return q.popleft()