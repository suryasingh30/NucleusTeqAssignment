import time

def t(f):
    def w(*args):
        st = time.time()
        r = f(*args)
        print("Time:", time.time() - st)
        return r
    return w