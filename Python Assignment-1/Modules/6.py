import argparse
p = argparse.ArgumentParser()
p.add_argument('x', type=float)
p.add_argument('y', type=float)
p.add_argument('--add', action='store_true')
args = p.parse_args()
if args.add: print(args.x + args.y)