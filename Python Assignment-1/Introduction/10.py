import random

num = random.randint(1, 100)
t = 0

while t < 5:
    g = int(input("Guess: "))
    t += 1
    if g < num:
        print("Low")
    elif g > num:
        print("High")
    else:
        print("Got it in", t)
        break

if t == 5 and g != num:
    print("Lost. It was", num)