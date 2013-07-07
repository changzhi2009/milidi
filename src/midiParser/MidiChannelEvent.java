package midiParser;

public class MidiChannelEvent extends TrackEvent{
	int deltaTime;
	int eventType, midiChannel, param1, param2;
	public MidiChannelEvent(int deltaTime2, int eventType, int midiChannel, int param12, int param22) {
		this.deltaTime = deltaTime2;
		this.eventType = eventType;
		this.midiChannel = midiChannel;
		this.param1 = param12;
		this.param2 = param22;
	}
	
	public String toString(){
		String output = "Channel " + midiChannel +" Data:";
		switch(eventType){
		case 8:
			output += "Note Off, note #" + param1 + ", velocity " + param2;
			break;
		case 9:
			output += "Note On, note #" + param1 + ", velocity " + param2;
			break;
		case 10:
			output += "Note Aftertouch, note #" + param1 + ", aftertouch value " + param2;
			break;
		case 11:
			output += "Controller, controller #" + param1 + ", controller value " + param2;
			break;
		case 12:
			output += "Program change, program #" + param1 + ", not used " + param2;
			break;
		case 13:
			output += "Channel aftertouch, aftertouch value " + param1 + ", not used " + param2;
			break;
		case 14:
			output += "Pitch bend, pitch value LSB " + param1 + ", pitch value MSB " + param2;
			break;
		default:
			output += "Unknown type " + eventType + ", param1 " + param1 + "param2 " + param2;
		}
		return output;
	}


}
