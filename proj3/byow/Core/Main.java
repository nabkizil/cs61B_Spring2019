package byow.Core;

import byow.TileEngine.TETile;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byow.Core.Engine class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) throws java.io.IOException,
            LineUnavailableException, UnsupportedAudioFileException {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Engine engine = new Engine();
            TETile[][] worldStatus = engine.interactWithInputString(args[0]);
            System.out.println(TETile.toString(worldStatus));
        } else {
            Engine engine = new Engine();
            engine.interactWithKeyboard();
        }
    }
}
