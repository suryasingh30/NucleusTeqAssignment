def fl(l):
    r = []
    for i in l:
        if type(i) == list:
            r += fl(i)
        else:
            r.append(i)
    return r
print(fl([1,[2,3]]))