def countw(lst):
    d = {}
    for w in lst:
        d[w] = d.get(w, 0) + 1
    return d