# bouncing-ball

1. Ball Entities
Each ball should be represented as an object with attributes for color, size (radius), position (x, y coordinates), and velocity (speed and direction).
Colors: Each ball should have a unique color, randomly assigned from a color set.
Size Range: 5 to 20 pixels in diameter.
Position: Randomized x, y within the window size m, n.
Initial Speed: Randomized within a range ( 1 to 5 units per frame).
Initial Direction: Randomized with 8 pre-set directions.
2. Bouncing Mechanics
Collision Detection: Implement boundary collision detection for the window edges, that is when x=m or y=n.
Bounce Physics: On collision with a boundary, invert the relevant component of the ball's velocity vector to simulate bouncing.
3. User Control
Interface: Implement a GUI panel for user controls.
Control Options:
Add/Remove Balls: Buttons to increase or decrease the number of balls.
Speed Adjustment: Slider or input field to adjust the speed of all balls.
Direction Adjustment: Buttons to change direction of the balls.
4. Collision Dynamics
Detect collisions between balls and adjust their velocities to simulate a bounce(e.g. when x1 = x2+/- 1 and y1 = y2). This can be simplified to inverting velocity vectors.
