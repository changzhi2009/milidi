package frontEnd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import midiParser.MidiParser;

public class frontend {
	
	public static void main(String[] args) {
		File file = new File("C:\\Users\\Erdogan\\Documents\\midi\\s_club_7-never_had_a_dream_come_true.mid");
		try {
			Log.enableLogs(new File("C:\\Users\\Erdogan\\Documents\\midi\\log.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			MidiParser parser = new MidiParser(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not find file " + file.toString());
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Log.disableLogs();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
