m = {0:0, 1:1}
def mfib(n):
    if n not in m:
        m[n] = mfib(n-1) + mfib(n-2)
    return m[n]
print(mfib(10))