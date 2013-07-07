package midiParser;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import frontEnd.Log;

public class MidiParser{

	static byte[] bitify(int b){
		byte[] output = new byte[8];
		for(int i = 0;i<8;i++){
			if((b&(1<<(7-i))) != 0){
				output[i]=1;
			}
			else{
				output[i]=0;
			}
		}
		return output;
	}
	static long variableLengthToLong(byte[] input){
		long output = 0;
		int j = 0;
		for(int i = input.length-1;i>=0;i--){
			output += input[i]*Math.pow(2, j++); 
		}
		return output;
	}
	static int midiDecTime2normalTime(Integer[] n) {
		int l=n.length;    int t=0;
		for (int i=0 ; i<l-1 ; i++) {
			t += (n[i]-128) * Math.pow(2,7 * (l-i-1)) ;
		}
		t += n[l-1];
		return t;
	}

	static int readVariableLength(RandomAccessFile in) throws IOException{
		ArrayList<Integer> unprocessed = new ArrayList<Integer>();
		int temp = 0;
		do{
			temp = in.readUnsignedByte();
			unprocessed.add(temp);
		} while ((temp&0x80)==0x80);
		Integer[] intermediary = unprocessed.toArray(new Integer[0]);
		return midiDecTime2normalTime(intermediary);
	}
	public Midi parseMidi(RandomAccessFile in) throws IOException{
		Midi midi = new Midi();
		readHeader(in, midi);
		readTracks(in, midi);
		return midi;
	}
	public static String byte2String(byte[] bytes){
		String t = "";
		for(int i = 0;i<bytes.length;i++){
			t += (char)bytes[i];
		}
		return t;
	}
	private void readTracks(RandomAccessFile in, Midi midi) throws IOException {
		byte[] chunkID = new byte[4];

		in.read(chunkID, 0, 4);
		do{
			int chunkSize = in.readInt();
			Log.log("Track chunk size: " + chunkSize);
			TrackParser trackParser = new TrackParser(in, chunkSize);
			in.read(chunkID, 0, 4);
		}
		while("MTrk".equals(byte2String(chunkID)) && in.length() > in.getFilePointer());
	}
	private void readHeader(RandomAccessFile in, Midi midi) throws IOException {
		byte[] chunkID = new byte[4];
		in.read(chunkID, 0, 4);

		if("MThd".equals(chunkID.toString())){
			throw new IOException("Header chunkID not correct! Expected MThd, got " + chunkID);
		}
		if(!(in.readInt()==6)){
			throw new IOException("Unknown midi format, chunkSize is not 6!");
		}
		int formatType = in.readUnsignedShort();
		Log.log("Format type is: " + formatType);
		int numTracks = in.readUnsignedShort();
		Log.log("Number of tracks is: " + numTracks);
		int timeDivision = in.readUnsignedShort();
		Log.log("Time division type " +(timeDivision & 0x8000) + ", value "+ (timeDivision&0x7FFF));
		midi.setFormatType(formatType);
		midi.setTimeDivisionFormat(timeDivision&0x8000);
		midi.setTimeDivisionValue(timeDivision&0x7FFF);
		midi.setTrackCount(numTracks);


	}
	public MidiParser(File file) throws IOException{
		RandomAccessFile in = new RandomAccessFile(file, "r");
		Midi parsed = parseMidi(in);
	}
}
