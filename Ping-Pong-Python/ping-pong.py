import turtle
import random
from datetime import datetime

playerScore = 0

screen = turtle.Screen()
screen.title("Ping-Pong")
screen.bgcolor("black")
screen.setup(width=800, height=600)
screen.tracer(0)

playerPaddle = turtle.Turtle()
playerPaddle.speed(0)
playerPaddle.shape("square")
playerPaddle.color("white")
playerPaddle.shapesize(stretch_wid=5, stretch_len=1)
playerPaddle.penup()
playerPaddle.goto(350, 0)

compPaddle = turtle.Turtle()
compPaddle.speed(0)
compPaddle.shape("square")
compPaddle.color("white")
compPaddle.shapesize(stretch_wid=5, stretch_len=1)
compPaddle.penup()
compPaddle.goto(-350, 0)

ball = turtle.Turtle()
ball.speed(0)
ball.shape("circle")
ball.color("white")
ball.penup()
ball.goto(0,0)
random.seed(datetime.now())
ball.dx = -0.1 if random.randint(1,10) % 2 == 0 else 0.1
ball.dy = -0.1 if random.randint(1, 10) % 2 == 0 else 0.1

score = turtle.Turtle()
score.speed(0)
score.color("white")
score.penup()
score.hideturtle()
score.goto(0, 260)
score.write("Player Score: 0", align="center", font=("Courier", 20, "normal"))

def paddleUp():
    y = playerPaddle.ycor()
    if not y > 220:
        y += 30
    playerPaddle.sety(y)

def paddleDown():
    y = playerPaddle.ycor()
    if not y < -220:
        y -= 30
    playerPaddle.sety(y)



while True:
    screen.listen()
    screen.onkeypress(paddleUp, "Up")
    screen.onkeypress(paddleDown, "Down")

    screen.update()

     # move the ball
    ball.setx(ball.xcor() + ball.dx)
    ball.sety(ball.ycor() + ball.dy)

    # update the computer paddle
    compPaddle.sety(ball.ycor())

    # border checking
    if ball.ycor() > 280:
        ball.sety(280)
        ball.dy *= -1

    if ball.ycor() < -280:
        ball.sety(-280)
        ball.dy *= -1

    #left and right
    if (ball.xcor() < -340 and ball.xcor() > -350) and (compPaddle.ycor() + 50 > ball.ycor() > compPaddle.ycor() - 50):
        score.clear()
        score.write("Player Score: {}".format(playerScore), align="center", font=("Courier", 20, "normal"))
    if ball.xcor() > 380:
        score.clear()
        score.write("Player Score: {}".format(playerScore), align="center", font=("Courier", 20, "normal"))
        score.goto(0, 0)
        score.write("Game Over", align="center", font=("Courier", 40, "bold"))
        ball.goto(0,0)
        break

    if (ball.xcor() > 340 and ball.xcor() < 350) and (playerPaddle.ycor() + 50 > ball.ycor() > playerPaddle.ycor() - 50):
        playerScore += 1
        score.clear()
        score.write("Player Score: {}".format(playerScore), align="center", font=("Courier", 20, "normal"))


    # paddle and ball collisions
    if (ball.xcor() > 340 and ball.xcor() < 350) and (playerPaddle.ycor() + 50 > ball.ycor() > playerPaddle.ycor() - 50):
        ball.setx(340)
        ball.dx *= -1
        if abs(ball.dx) < 1:
            ball.dx += -0.1 if random.randint(1,10) % 2 == 0 else 0.1
            if ball.dx == 0:
                ball.dx -= 0.1
        if abs(ball.dy) < 1:
            ball.dy += -0.1 if random.randint(1,10) % 2 == 0 else 0.1
            if ball.dy == 0:
                ball.dy -= 0.1

    if (ball.xcor() < -340 and ball.xcor() > -350) and (compPaddle.ycor() + 50 > ball.ycor() > compPaddle.ycor() - 50):
        ball.setx(-340)
        ball.dx *= -1
        if abs(ball.dx) < 1:
            ball.dx += -0.1 if random.randint(1,10) % 2 == 0 else 0.1
            if ball.dx == 0:
                ball.dx += 0.1
        if abs(ball.dy) < 1:
            ball.dy += -0.1 if random.randint(1,10) % 2 == 0 else 0.1
            if ball.dy == 0:
                ball.dy += 0.1

turtle.done()
