def h(n, src, aux, dst):
    if n == 1:
        print(f"Move disk 1 from {src} to {dst}")
        return
    h(n-1, src, dst, aux)
    print(f"Move disk {n} from {src} to {dst}")
    h(n-1, aux, src, dst)
print(v("hello"))