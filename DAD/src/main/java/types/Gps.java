package types;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Temperature {

	private static final AtomicInteger Counter = new AtomicInteger();

	private int id;
	private float value;
	private long timestamp;
	private String location;
	private int accuracy;

	@JsonCreator

	public Temperature(
			@JsonProperty("value") float value, 
			@JsonProperty("timestamp") long timestamp,
			@JsonProperty("location") String location,
			@JsonProperty("accuracy") int accuracy) {
		super();
		this.id=Counter.getAndIncrement();
		this.value = value;
		this.timestamp = timestamp;
		this.accuracy = accuracy;
	}
		
		
		public Temperature() {
			super();
			this.id=Counter.getAndIncrement();
			this.value = 0;
			this.timestamp = Calendar.getInstance().getTimeInMillis();		
		
//		Calendar calendar =Calendar.getInstance(); 
//		calendar.set(Calendar.DAY_OF_MONTH,25);
//		calendar.set(Calendar.MONTH,Calendar.JANUARY);
//		calendar.set(Calendar.HOUR,22);		
//		calendar.set(Calendar.MINUTE,15);
//		this.timestamp=calendar.getTimeInMillis();
				
		this.location = "";
		this.accuracy = 0;
	
	}

	public int getId() {
		return id;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accuracy;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + Float.floatToIntBits(value);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Temperature other = (Temperature) obj;
		if (accuracy != other.accuracy)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (Float.floatToIntBits(value) != Float.floatToIntBits(other.value))
			return false;
		return true;
	}

}