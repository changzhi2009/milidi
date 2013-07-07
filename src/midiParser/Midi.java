package midiParser;

public class Midi {
	public int formatType, timeDivisionFormat, timeDivisionValue, trackCount;
	
	public void setFormatType(int formatType) {
		this.formatType = formatType;
	}

	public void setTimeDivisionFormat(int i) {
		timeDivisionFormat = i;
		
	}

	public void setTimeDivisionValue(int i) {
		timeDivisionValue = i;
	}

	public void setTrackCount(int numTracks) {
		trackCount = numTracks;
	}
}
