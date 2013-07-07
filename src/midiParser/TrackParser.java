package midiParser;

import java.io.IOException;
import java.io.RandomAccessFile;
import frontEnd.Log;

public class TrackParser {
	Track track = new Track();
	TrackParser(RandomAccessFile in, int length) throws IOException{
		int remainingLength = length;
		long position = in.getFilePointer();
		while(remainingLength > 0){
			Log.log("Remaining track length: " + remainingLength);
			TrackEvent event = parseTrackEvent(in);
			remainingLength -= in.getFilePointer()-position;
			position = in.getFilePointer();
		}
	}
	int oldtemp = 0;
	private TrackEvent parseTrackEvent(RandomAccessFile in) throws IOException {
		int deltaTime = MidiParser.readVariableLength(in);
		Log.log("------");
		Log.log("TrackEvent deltatime: " + deltaTime);
		TrackEvent output = new MidiChannelEvent(deltaTime,0,0,(byte)0,(byte)0);
		int temp = in.readUnsignedByte();
		if((temp & 0x80)!=0x80){
			in.seek(in.getFilePointer()-1);
			temp = oldtemp;
		}
		if((temp & 0xF0) == 0xF0){
			Log.log("Meta-data, type is " + temp);
			int subtype = in.readUnsignedByte();
			Log.log("subtype is " + subtype);
			long length = MidiParser.readVariableLength(in);
			Log.log("length is " + length);
			byte[] data = new byte[(int) length];
			in.read(data, 0, (int)length);
			String t = "";
			for(int i = 0;i<data.length;i++){
				t += (char)data[i];
			}
			Log.log("Data read: " + t);
		}else if((temp & 0xC0) == 0xC0 || (temp & 0xD0)==0xD0){
			int eventType = (temp&0xF0)>>4;
			int midiChannel = (temp&0xF);
			byte param1 = in.readByte();
			output = new MidiChannelEvent(deltaTime, eventType, midiChannel, param1, (byte)0);
			Log.log(output.toString());
		}else if((temp&0x80)==0x80){
			int eventType = (temp&0xF0)>>4;
			int midiChannel = (temp&0xF);
			int param1 = in.readUnsignedByte();
			int param2 = in.readUnsignedByte();
			output = new MidiChannelEvent(deltaTime, eventType, midiChannel, param1, param2);
			Log.log(output.toString());
		}
		else{
			Log.log("0x80 AND " + temp + " = " + (temp&0x80));
			Log.log("Completely unknown event occured, trying to skip with 2 bytes " + temp +" "+in.readUnsignedByte()+" "+in.readUnsignedByte());
		}
		oldtemp = temp;
		return output;
	}
}
