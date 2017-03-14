import sys
import sdl2
import sdl2.ext

L_SHAPE_0 = [1, 0, 0,
             1, 0, 0,
             1, 1, 0]
L_SHAPE_1 = [0, 0, 0,
             1, 1, 1,
             1, 0, 0]
L_SHAPE_2 = [1, 1, 0,
             0, 1, 0,
             0, 1, 0]
L_SHAPE_3 = [0, 1, 0,
             0, 1, 0,
             1, 1, 0]
L_SHAPE_4 = [0, 0, 0,
             1, 0, 0,
             1, 1, 1]


T_SHAPE_0 = [1, 1, 1,
             0, 1, 0,
             0, 0, 0]
T_SHAPE_1 = [0, 0, 1,
             0, 1, 1,
             0, 0, 1]
T_SHAPE_2 = [0, 0, 0,
             0, 1, 0,
             1, 1, 1]
T_SHAPE_3 = [1, 0, 0,
             1, 1, 0,
             1, 0, 0]


BOX_SHAPE = [1, 1, 0,
             1, 1, 0,
             0, 0, 0]
shapes = {"l_shape": [L_SHAPE_0, L_SHAPE_1, L_SHAPE_2, L_SHAPE_3, L_SHAPE_4],
          "t_shape": [T_SHAPE_0]}


def debug_shape(shape):
    count = 0
    for i in shape:
        if count == 3:
            print()
            count = 0
        print(i, end="")
        count += 1

def run():
    sdl2.ext.init()
    window = sdl2.ext.Window("The Pong Game", size=(800, 600))
    window.show()
    running = True
    while running:
        events = sdl2.ext.get_events()
        for event in events:
            if event.type == sdl2.SDL_QUIT:
                running = False
                break
        window.refresh()
    return 0

# run()


def test_shape():
    s = Shape(shapes['l_shape'])
    debug_shape(s.rotate())
    print()


class Shape():

    def __init__(self, shape):
        self.shape = shape
        self.rotation = 0
        print(self.shape)
    def rotate(self):
        # 1 up, 0 down
        self.rotation += 1
        if len(self.shape) > 3:
            return self.shape[self.rotation -1]
        return self.shape
test_shape()
