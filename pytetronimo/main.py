import pyglet
from pyglet.window import key
from pyglet import clock

WIDTH = 800
HEIGHT = 600
L_SHAPE_0 = [[1, 0, 0],
             [1, 0, 0],
             [1, 1, 0]]
L_SHAPE_1 = [[0, 0, 0],
             [1, 1, 1],
             [1, 0, 0]]
L_SHAPE_2 = [[1, 1, 0],
             [0, 1, 0],
             [0, 1, 0]]
L_SHAPE_3 = [[0, 1, 0],
             [0, 1, 0],
             [1, 1, 0]]
L_SHAPE_4 = [[0, 0, 0],
             [1, 0, 0],
             [1, 1, 1]]


T_SHAPE_0 = [[1, 1, 1],
             [0, 1, 0],
             [0, 0, 0]]
T_SHAPE_1 = [[0, 0, 1],
             [0, 1, 1],
             [0, 0, 1]]
T_SHAPE_2 = [[0, 0, 0],
             [0, 1, 0],
             [1, 1, 1]]
T_SHAPE_3 = [[1, 0, 0],
             [1, 1, 0],
             [1, 0, 0]]


BOX_SHAPE = [[1, 1, 0],
             [1, 1, 0],
             [0, 0, 0]]
shapes = {"l_shape": [L_SHAPE_0, L_SHAPE_1, L_SHAPE_2, L_SHAPE_3, L_SHAPE_4],
          "t_shape": [T_SHAPE_0]}
window = pyglet.window.Window(width=WIDTH, height=HEIGHT)
tetronimo_block = pyglet.resource.image('block.png')
grid = [[0, 0, 1, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        [1, 0, 0, 0, 0, 0, 0, 0, 1, 1]]
grid.reverse()
#tetronimo_block.anchor_x = -WIDTH // 2 + 4 * 15
#tetronimo_block.anchor_y = -HEIGHT // 2


@window.event
def on_draw():
    window.clear()
    draw_grid()
    draw_tetronimo()

def draw_tetronimo():
    pass


def draw_grid():

    for row_num, row in enumerate(grid):
        for block_num, block in enumerate(row):
            if block == 1:
                tetronimo_block.blit(block_num * 15, row_num * 15)


def update_grid():
    pass


def update_tetronimo():
    pass


@window.event
def on_key_press(symbol, modifiers):
    if symbol == key.LEFT:
        pass
    elif symbol == key.RIGHT:
        pass


def update(dt):

    #print("{} FPS".format(clock.get_fps()))
    pass

if __name__ == '__main__':
    dt = clock.tick()
    pyglet.clock.schedule(update)
    pyglet.app.run()


def debug_shape(shape):
    count = 0
    for i in shape:
        if count == 3:
            print()
            count = 0
        print(i, end="")
        count += 1


def draw_shapes():
    pass
