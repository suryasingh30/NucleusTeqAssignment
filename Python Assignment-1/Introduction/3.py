yr = int(input("Enter year: "))
print(yr % 4 == 0 and (yr % 100 != 0 or yr % 400 == 0))