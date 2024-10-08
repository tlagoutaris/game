package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.Color;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.worldWidth / 2;
        worldY = gp.worldHeight /2;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try {

            up_idle = ImageIO.read(getClass().getResourceAsStream("/player/player_up_idle.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png"));

            down_idle = ImageIO.read(getClass().getResourceAsStream("/player/player_down_idle.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));

            right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        boolean moving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        if (moving) {

            if (spriteNum == 3) {
                spriteNum = 1; // reset sprite if it was in an up/down idle frame
            }

            // Update direction
            if (keyH.upPressed) {
                worldY -= speed;
                direction = "up";
            } else if (keyH.downPressed) {
                worldY += speed;
                direction = "down";
            } else if (keyH.leftPressed) {
                worldX -= speed;
                direction = "left";
            } else if (keyH.rightPressed) {
                worldX += speed;
                direction = "right";
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else { // for handling idle animations
            if (direction.equalsIgnoreCase("up") || direction.equalsIgnoreCase("down")) {
                spriteNum = 3;
            } else {
                spriteNum = 1; // left or right
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":

                if (spriteNum == 1) {
                    image = up1;
                }

                if (spriteNum == 2) {
                    image = up2;
                }

                if (spriteNum == 3) {
                    image = up_idle;
                }

                break;
            case "down":

                if (spriteNum == 1) {
                    image = down1;
                }

                if (spriteNum == 2) {
                    image = down2;
                }

                if (spriteNum == 3) {
                    image = down_idle;
                }

                break;
            case "left":

                if (spriteNum == 1) {
                    image = left1;
                }

                if (spriteNum == 2) {
                    image = left2;
                }

                break;
            case "right":

                if (spriteNum == 1) {
                    image = right1;
                }

                if (spriteNum == 2) {
                    image = right2;
                }

                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }


}
