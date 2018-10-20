
public class RobinHoodHashMap<T extends Comparable<? super T>> {
	static int capacity = 16;
	static int max_displacement;
	Entry[] table;
	int d; // displacement
	int loc;

	public RobinHoodHashMap() {
		table = new Entry[capacity];
		d = 0;
		max_displacement = 0;

	}

	static class Entry<E extends Comparable<? super E>> {
		E element;
		boolean deleted;

		/**
		 * Constructor for the class Entry
		 * 
		 * @param x : The value to be stored
		 */
		public Entry(E x) {
			element = x;
			deleted = false;

		}
	}

	// Code extracted from Java’s HashMap:
	static int hash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	static int indexFor(int h, int length) { // length = table.length is a power of 2
		return h & (length - 1);
	}
	// Key x is stored at table[ hash( x.hashCode( ) ) & ( table.length − 1 ) ].

	public int h(T x) {
		return indexFor(hash(x.hashCode()), table.length);
	}

	public boolean contains(T x) {
		return false;
	}

	public int displacement(T x, int loc) {
		// Calculate displacement of x from its ideal location of h( x ).
		return loc >= h(x) ? loc - h(x) : table.length + loc - h(x);

	}

	public boolean add(T x) {

		if (contains(x)) {
			return table[loc].element == x;
		} else {

			int loc = h(x);
			d = 0;
			while (true) {
				if (table[loc] == null || table[loc].deleted == true) {
					table[loc].element = x;
					if (d > max_displacement) {
						max_displacement = d;
					}
					return true;
				} else if (displacement((T) table[loc].element, loc) >= d) {
					d++;
					loc = (loc + 1) % table.length;
				} else {
					// x has bigger displacement than element at loc, so replace it
					T temp = (T) table[loc].element;
					table[loc].element = x;
					x = temp;
					loc = (loc + 1) % table.length;
					d = displacement(x, loc);
				}

			}

		}

	}

	public T remove(T x) {
		loc = find(x);

		if (table[loc].element.equals(x)) {
			T result = (T) table[loc].element;
			table[loc].deleted = true;
			return result;
		} else {
			return null;
		}

	}

	public int find(T x) {
		loc = h(x);
		int count = 0;
		while (count < max_displacement) {
			if (table[loc].element.equals(x)) {
				break;
			} else {
				loc++;
				count++;
			}

		}
		return loc;

	}
}
