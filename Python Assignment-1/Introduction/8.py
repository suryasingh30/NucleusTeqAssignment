print("1. C to F")  
print("2. F to C")  
c = int(input("Pick: "))  

if c == 1:  
    x = float(input("C: "))  
    print((x * 9/5) + 32, "F")  
elif c == 2:  
    y = float(input("F: "))  
    print((y - 32) * 5/9, "C")  
else:  
    print("Wrong")  