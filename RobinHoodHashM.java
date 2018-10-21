import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RobinHoodHashMap<T extends Comparable<? super T>> {
	static int capacity = 16;
	static int max_displacement;
	Entry[] table;
	int d; // displacement
	int loc;
	int size;
	static double threshold;

	public RobinHoodHashMap() {
		table = new Entry[capacity];
		for (int i = 0; i < capacity; i++) {
			table[i] = new Entry(null);
		}
		d = 0;
		max_displacement = 0;
		size = 0;
		threshold = 0.75;
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
		return indexFor(hash(x.hashCode()), table.length - 1);
	}

	public boolean contains(T x) {
		loc = find(x);
		return table[loc].element == x;
	}

	public int displacement(T x, int loc) {
		// Calculate displacement of x from its ideal location of h( x ).
		return loc >= h(x) ? loc - h(x) : table.length + loc - h(x);

	}

	public int size() {
		return size;
	}

	public boolean add(T x) {

		if (contains(x)) {
			return false;
		} else {

			if (size++ / capacity > threshold) {
				resize();
			}
			loc = h(x);
			d = 0;
			while (true) {
				if (table[loc].element == null || table[loc].deleted == true) {
					table[loc].element = x;
					if (d > max_displacement) {
						max_displacement = d;
					}
					size++;
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

	public void resize() {
		Entry[] temp = table;
		table = new Entry[capacity * 2];
		capacity = capacity * 2;

		// initialize array objects

		for (int i = 0; i < capacity; i++) {
			table[i] = new Entry(null);
		}

		int count = temp.length - 1;

		while (count-- > 0) {
			if (temp[count].element != null) {
				add((T) temp[count].element);
			}

		}

	}

	public T remove(T x) {
		loc = find(x);

		if (table[loc].element == x) {
			T result = (T) table[loc].element;
			table[loc].deleted = true;
			size--;
			return result;
		} else {
			return null;
		}

	}

	public int find(T x) {
		loc = h(x);
		int count = 0;
		while (count <= max_displacement) {
			if (table[loc].element == x) {
				break;
			} else {
				loc = (loc + 1) % capacity;
				count++;
			}

		}
		return loc;

	}

	public void printList() {
		for (int i = 0; i < table.length; i++) {
			System.out.println(table[i].element);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		RobinHoodHashMap<Integer> map = new RobinHoodHashMap<>();
		/*
		map.add(10);
		map.add(12);
		map.add(13);
		map.add(14);
		map.add(10000);
		map.add(10);
		map.add(13);
		map.add(144);
		map.add(10000);
		map.add(1001);
		map.add(101);
		map.add(121);
		map.add(131);
		map.add(141);
		map.add(100001);
		map.add(101);
		map.add(131);
		map.add(1441);
		map.add(100001);
		map.add(1001);
		System.out.println(map.remove(55));

		System.out.println(map.remove(13));
		System.out.println(map.contains(12));
		System.out.println(map.remove(1441));
		System.out.println(map.contains(1001));
		*/
	
		//map.printList();
		Scanner sc;
		  File file = new File("F:\\UT Dallas\\Courses\\Sem1-Fall18\\Implementation of Data Structures\\LP\\LP2\\Starter\\Test\\lp2-t03.txt");
		    sc = new Scanner(file);
		
		String operation = "";
		long operand = 0;
		int modValue = 999983;
		long result = 0;
		Long returnValue = null;
		
		while (!((operation = sc.next()).equals("End"))) {
		    switch (operation) {
		    case "Add": {
			operand = sc.nextLong();
			if(map.add((int) operand)) {
			    result = (result + 1) % modValue;
			}
			break;
		    }
		    case "Remove": {
			operand = sc.nextLong();
			if (map.remove((int) operand) != null) {
			    result = (result + 1) % modValue;
			}
			break;
		    }
		    case "Contains":{
			operand = sc.nextLong();
			if (map.contains((int) operand)) {
			    result = (result + 1) % modValue;
			}
			break;
		    }
			
		    }
		}


		map.printList();
	}

}
