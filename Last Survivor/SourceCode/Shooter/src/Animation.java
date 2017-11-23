//Class to control the animation

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Animation {

    private int frameCount;                 // Counts ticks for change
    private int frameDelay;                 // frame delay 1-12 (You will have to play around with this)
    public int currentFrame;               // animations current frame
    private int animationDirection;         // animation direction (i.e counting forward or backward)
    private int totalFrames;                // total amount of frames for your animation

    private boolean stopped;                // has animations stopped
    public boolean finished;

    private List<Frame> frames = new ArrayList<Frame>();    // Arraylist of frames 

    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        this.stopped = true;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();
        this.finished = false;
    }

    public void start() { //Start the animation
        if (!stopped) {
            return;
        }

        if (frames.size() == 0) {
            return;
        }

        stopped = false;
    }

    public void stop() { //Stop the animation
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }
    
    private void addFrame(BufferedImage frame, int duration) { //add the frames of the sprite sheet
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    public BufferedImage getSprite() { //"Extract" a sprite
        return frames.get(currentFrame).getFrame();
    }

    public void update() { //Update the animation so it runs
        if (!stopped) { 	
            frameCount++;
            
            System.out.println("Updated");
            
            if (frameCount > frameDelay) {
                frameCount = 0;
                currentFrame += animationDirection;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                	finished = true;
                }
                else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        }

    }

}