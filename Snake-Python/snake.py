import pygame
import os
import random

from pygame.locals import *

current_path = os.path.dirname(__file__)
resource_path = os.path.join(current_path, 'resources')
FPS = 12
clock = pygame.time.Clock()
SIZE = 20
WIDTH, HEIGHT = 1000, 800

def colorize(image, newColor):
    image = image.copy()

    # zero out RGB values
    image.fill((0, 0, 0, 255), None, pygame.BLEND_RGBA_MULT)
    # add in new RGB values
    image.fill(newColor[0:3] + (0,), None, pygame.BLEND_RGBA_ADD)

    return image

class Dot:
    def __init__(self, parent_screen):
        self.parent_screen = parent_screen
        self.image = pygame.image.load(os.path.join(resource_path, 'white_block.png')).convert()
        self.image = pygame.transform.scale(self.image, (SIZE, SIZE))
        self.x = random.randint(3, 47) * SIZE
        self.y = random.randint(3, 37) * SIZE

    def draw(self):
        self.parent_screen.blit(self.image, (self.x, self.y))
        pygame.display.flip()

    def move(self):
        self.x = random.randint(3, 47) * SIZE
        self.y = random.randint(3, 37) * SIZE

class Snake:
    def __init__(self, parent_screen):
        self.parent_screen = parent_screen
        self.body = pygame.image.load(os.path.join(resource_path, 'block.png')).convert()
        self.body = pygame.transform.scale(self.body, (SIZE, SIZE))
        self.head = pygame.image.load(os.path.join(resource_path, 'white_block.png')).convert()
        self.head = colorize(pygame.transform.scale(self.head, (SIZE, SIZE)), (230, 0, 0))

        #direction 1 is up; -1 is down; -2 is left; 2 is right; 0 is stop
        self.direction = 0
        self.length = 1
        self.x = [random.randint(3, 45) * SIZE]
        self.y = [random.randint(3, 35) * SIZE]

    def move(self, direction):
        if -direction != self.direction or direction == self.direction:
            self.direction = direction

    def walk(self):
        if self.direction == 0:
            self.draw()
            return
        
        #update body
        for i in range(self.length - 1, 0, -1):
            self.x[i] = self.x[i - 1]
            self.y[i] = self.y[i - 1]

        #update head
        if self.direction == 1: #up
            self.y[0] -= SIZE
        elif self.direction == -1: #down
            self.y[0] += SIZE
        elif self.direction == 2: #right
            self.x[0] += SIZE
        elif self.direction == -2: #left
            self.x[0] -= SIZE
 
        self.draw()

    def increaseLength(self):
        self.length += 1
        self.x.append(-1)
        self.y.append(-1)

    def draw(self):
        for i in range(self.length):
            if i == 0:
                self.parent_screen.blit(self.head, (self.x[i], self.y[i]))
            else:
                self.parent_screen.blit(self.body, (self.x[i], self.y[i]))

        pygame.display.flip()

class Game:
    def __init__(self):
        pygame.init()
        self.surface = pygame.display.set_mode((WIDTH, HEIGHT))
        pygame.display.set_caption("Snake")
        self.dot = Dot(self.surface)
        self.dot.draw()
        self.snake = Snake(self.surface)
        self.snake.draw()
        
    def play(self):
        self.surface.fill((0, 0, 0))
        self.snake.walk()
        self.dot.draw()
        self.showScore()
        pygame.display.flip()

        #snake eats dot
        if self.isCollision(self.snake.x[0], self.snake.y[0], self.dot.x, self.dot.y):
            self.snake.increaseLength()
            self.dot.move()

        #snake collides with itself
        for i in range(3, self.snake.length):
            if self.isCollision(self.snake.x[0], self.snake.y[0], self.snake.x[i], self.snake.y[i]):
                raise("Collision occured")

        #snake collides with border
        if not (0 <= self.snake.x[0] <= WIDTH - SIZE) or not (60 <= self.snake.y[0] <= HEIGHT - SIZE):
            raise("Collision occured")

    def showGameOver(self):
        font = pygame.font.SysFont('arial', 30)
        line1 = font.render(f"Game over! Your score is {self.snake.length}", True, (255, 255, 255))
        self.surface.blit(line1, (200, 300))
        line2 = font.render("To play again press Enter. To exit press Escape!", True, (255, 255, 255))
        self.surface.blit(line2, (200, 350))
        pygame.display.flip()

    def showScore(self):
        font = pygame.font.SysFont('arial',30)
        score = font.render(f"Score: {self.snake.length}",True,(200,200,200))
        self.surface.blit(score,(850,10))

    def reset(self):
        self.snake = Snake(self.surface)
        self.dot = Dot(self.surface)

    def run(self):
        run = True
        gameOver = False
        pause = False

        while run:
            clock.tick(FPS)
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    run = False
                elif event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_ESCAPE:
                        run = False
                    elif event.key == pygame.K_RETURN:
                        pause = False
                    elif event.key == pygame.K_UP:
                        self.snake.move(1)
                    elif event.key == pygame.K_DOWN:
                        self.snake.move(-1)
                    elif event.key == pygame.K_LEFT:
                        self.snake.move(-2)
                    elif event.key == pygame.K_RIGHT:
                        self.snake.move(2)
            try:
                if not pause:
                    self.play()
            except Exception as e:
                self.showGameOver()
                pause = True
                self.reset()
    
    def isCollision(self, x1, y1, x2, y2):
        if x1 >= x2 and x1 < x2 + SIZE:
            if y1 >= y2 and y1 < y2 + SIZE:
                return True
        return False


if __name__ == "__main__":
    game = Game()
    game.run()
