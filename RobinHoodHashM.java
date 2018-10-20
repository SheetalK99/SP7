# SP7
package sak170006;

public class RobinHoodHashMap<T extends Comparable<? super T>> {
	static int capacity = 16;
	Entry[] table = new Entry[capacity];
	int d = 0; // displacement

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
			return false;
		} else {

			int loc = h(x);
			d = 0;
			while (true) {
				if (table[loc] == null || table[loc].deleted == false) {
					table[loc].element = x;
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

}
