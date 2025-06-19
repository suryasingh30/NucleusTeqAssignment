n = int(input("enter number: "))

if type(n) != int:
    print('input shuold be an integer')

for i in range(2,n):
    if n % i == 0:
        print("not prime")
        break

print("prime")