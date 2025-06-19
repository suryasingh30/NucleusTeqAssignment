def v(s):
    c = 0
    for i in s:
        if i.lower() in 'aeiou':
            c += 1
    return c