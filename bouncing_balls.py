import pygame
import random
import sys

# Initialize Pygame
pygame.init()

# Screen dimensions
SCREEN_WIDTH, SCREEN_HEIGHT = 800, 600
screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))

# Colors
BLACK = (0, 0, 0)

class Ball:
    def __init__(self, x, y, radius, speed_x, speed_y, color):
        self.x = x
        self.y = y
        self.radius = radius
        self.speed_x = speed_x
        self.speed_y = speed_y
        self.color = color

    def move(self):
        self.x += self.speed_x
        self.y += self.speed_y

        # Bounce off the walls
        if self.x - self.radius <= 0 or self.x + self.radius >= SCREEN_WIDTH:
            self.speed_x = -self.speed_x
        if self.y - self.radius <= 0 or self.y + self.radius >= SCREEN_HEIGHT:
            self.speed_y = -self.speed_y

    def draw(self, screen):
        pygame.draw.circle(screen, self.color, (int(self.x), int(self.y)), self.radius)
      
def run_game():
    clock = pygame.time.Clock()
    balls = []
    # Initial ball for demonstration
    balls.append(Ball(x=400, y=300, radius=20, speed_x=5, speed_y=5, color=(random.randint(0, 255), random.randint(0, 255), random.randint(0, 255))))

    running = True
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1:  # Left click to add a ball
                    x, y = event.pos
                    balls.append(Ball(x, y, radius=20, speed_x=random.randint(-5, 5), speed_y=random.randint(-5, 5), color=(random.randint(0, 255), random.randint(0, 255), random.randint(0, 255))))
                elif event.button == 3 and balls:  # Right click to remove the last ball
                    balls.pop()
        
        # Check for key presses to adjust speed
        keys = pygame.key.get_pressed()
        if keys[pygame.K_SPACE]:
            for ball in balls:
                ball.speed_x *= 1.1
                ball.speed_y *= 1.1
        if keys[pygame.K_BACKSPACE]:
            for ball in balls:
                ball.speed_x *= 0.9
                ball.speed_y *= 0.9

        screen.fill(BLACK)
        for ball in balls:
            ball.move()
            ball.draw(screen)

        pygame.display.flip()
        clock.tick(60)

    pygame.quit()
    sys.exit()


if __name__ == "__main__":
    run_game()
