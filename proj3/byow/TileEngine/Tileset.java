package byow.TileEngine;

import java.awt.Color;

public class Tileset {
    public static final TETile PLAYER = new TETile('@', Color.white, Color.black, "player",
            "/Users/nabkizil/Desktop/sp19-s1684/proj3/pics/AVATAR.png");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile MYFLOOR = new TETile(' ', Color.white, Color.black,
            "floor");
    public static final TETile MYWALL = new TETile('▒', Color.yellow, Color.black,
            "wall");
    public static final TETile HEART = new TETile('A', Color.green, Color.black,
            "Heart", "/Users/nabkizil/Desktop/sp19-s1684/proj3/pics/HEART.png");
    public static final TETile SWORD = new TETile('X', Color.gray,
            Color.black, "Sword", "/Users/nabkizil/Desktop/sp19-s1684/proj3/pics/SWORD.png");
    public static final TETile APPLE = new TETile('B', Color.yellow,
            Color.black, "Fruit", "/Users/nabkizil/Desktop/sp19-s1684/proj3/pics/APPLE.png");
    public static final TETile ENEMY = new TETile('E', Color.red,
            Color.black, "Enemy", "/Users/nabkizil/Desktop/sp19-s1684/proj3/pics/ENEMY.png");
    public static final TETile FRIES = new TETile('F', Color.pink,
            Color.black, "Fries", "/Users/nabkizil/Desktop/sp19-s1684/proj3/pics/FRIES.gif");
    public static final TETile STAR = new TETile('G', Color.magenta,
            Color.black, "U A STAR", "/Users/nabkizil/Desktop/sp19-s1684/proj3/pics/STAR.png");

}


