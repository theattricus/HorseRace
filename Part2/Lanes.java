import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.IndexOutOfBoundsException;

public class Lanes implements Iterable<Lane> { // Number of lanes immutable. Create new Lanes object every time.
	private ArrayList<Lane> lanes;
	/**
	 * Lanes constructor, taking lane count and racetrack length.
	 * @param count is desired number of lanes.
	 * @param length is race track length.
	 */
	public Lanes(int count, int length) {
		this.lanes = new ArrayList<Lane>(count);
		for(int i = 0; i<count; i++) {
			lanes.add(new Lane(null, length));
		}
	}
	/**
	 * Clone previous lanes, up to count.
	 * @param count is desired number of lanes.
	 * @param length is race track length.
	 * @param other is old lanes object.
	 */
	public Lanes(int count, int length, Lanes other) {
		this.lanes = new ArrayList<Lane>(count);
		for(int i=0;i<count;i++) {
			lanes.add((i<other.lanes.size()) ? other.lanes.get(i) : new Lane(null, length));
		}
	}
	public int getCount() {
		return lanes.size();
	}

	public Horse getHorse(int laneNumber) throws IndexOutOfBoundsException {
		if(laneNumber<0 || laneNumber>=lanes.size()) {
			throw new IndexOutOfBoundsException("Tried setting horse in non-existent lane "+laneNumber+", lane count is "+getCount());
		}
		return lanes.get(laneNumber).getHorse();
	}

	public void setHorse(Horse theHorse, int laneNumber) throws IndexOutOfBoundsException {
		if(laneNumber<0 || laneNumber>=lanes.size()) {
			throw new IndexOutOfBoundsException("Tried adding horse to non-existent lane "+laneNumber+", lane count is "+getCount());
		}
		lanes.get(laneNumber).setHorse(theHorse);
	}

	@Override
	public Iterator<Lane> iterator() {
		return lanes.iterator();
	}

	public Iterator<Lane> used_lanes() { //Learned from https://www.baeldung.com/java-creating-custom-iterator
		return new Iterator<Lane>() {
			private int currentIndex = 0;

			private boolean isLaneUsed(Lane lane) {
				return lane.getHorse() != null;
			}

			@Override
			public boolean hasNext() {
				while(currentIndex < lanes.size()) {
					Lane lane = lanes.get(currentIndex);
					if(isLaneUsed(lane)) {
						return true;
					}
					currentIndex++;
				}
				return false;
			}

			@Override
			public Lane next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				return lanes.get(currentIndex++);
			}
		};
	}
}
