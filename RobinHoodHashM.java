import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class RobinHoodHashMap<T extends Comparable<? super T>> {
	static int capacity;
	static int max_displacement;
	Entry[] table;

	int loc;
	static int size;
	static double threshold; // threshold capacity after which rehashing and size doubled;

	public RobinHoodHashMap() {
		capacity = 2;
		table = new Entry[capacity];
		for (int i = 0; i < capacity; i++) {
			table[i] = new Entry(null);
		}
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
		return h & (length);
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
		return loc >= h(x) ? loc - h(x) : table.length - 1 + loc - h(x);

	}

	public static int size() {
		return size;
	}

	public boolean add(T x) {

		if (contains(x)) {
			return false;
		} else {

			if (((size + 1) / capacity) > threshold) {
				resize();
			}
			loc = h(x);

			int d = 0;
			while (true) {
				if (table[loc].element == null || table[loc].deleted == true) {
					table[loc].element = x; // if loc is empty
					if (d > max_displacement) {
						max_displacement = d; 
					}
					size++;
					return true;
				} else if (displacement((T) table[loc].element, loc) >= d) {
					// if x has smaller displacement thab element at loc
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
		// double capacity
		// initialize array objects

		for (int i = 0; i < capacity; i++) {
			table[i] = new Entry(null);
		}

		int count = temp.length - 1;
		size=0;

		while (count >= 0) {
			if (temp[count].element != null) {
				// rehashing
				add((T) temp[count].element);
			}
			count--;
		}

	}

	
	public int find(T x) {
		loc = h(x);
		int count = 0;
		// check for x for loc+max_displacement steps
		while (count <= max_displacement && max_displacement!=0) {
			if (table[loc].element == x) {
				break;
			} else {
				loc = (loc + 1) % capacity;
				count++;
			}

		}
		return loc;

	}

	static <T> int distinctElements(T[] arr) {
		RobinHoodHashMap<Integer> map1 = new RobinHoodHashMap<>();
		for (int i = 0; i < arr.length; i++) {
			map1.add((Integer) arr[i]);
		}
		System.out.println(map1.table.length);
		return size();

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

	public void printList() {
		for (int i = 0; i < table.length; i++) {
			System.out.println(i + ":" + table[i].element);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		 RobinHoodHashMap<Integer> map = new RobinHoodHashMap<>();

		// Testing with random numbers
		Random rand = new Random();
		Integer eval_arr[] = new Integer[40];

		for (int i = 0; i < 40; i++) {
			eval_arr[i] = rand.nextInt();
		}
		
		
		Timer timer1 = new Timer();
		// check distinct number of elements
		System.out.println(RobinHoodHashMap.distinctElements(eval_arr));
		timer1.end();
		System.out.println(timer1);

		Set<Integer> set1 = new HashSet<Integer>();

		Timer timer2 = new Timer();

		for (int i = 0; i < eval_arr.length; i++) {
			set1.add((Integer) eval_arr[i]);
		}

		timer2.end();
		System.out.println(timer2);
	/*	
		for (int i = 0; i < eval_arr.length; i++) {
			System.out.println(eval_arr[i]+":"+map.add((Integer) eval_arr[i]));
		}
*/
		
		/*for (int i = 0; i < eval_arr.length; i++) {
			System.out.println(eval_arr[i]+":"+map.contains((Integer) eval_arr[i]));
		}*/


		System.out.println(set1.size());
		
		/*
		map.add(-1619053230);
		map.add(-1139597371);
		map.add(-1518982680);
		map.add(2127260777);
		map.add(1068144905);
		map.add(-360145701);
		map.add(100713508);
		map.add(-1281306626);
		map.add(-2025046130);
		map.add(1226394489);
		map.add(264513491);
		map.add(792809967);
		map.add(-557969103);
		map.add(1776174614);
		map.add(168902457);
		map.add(751892086);
		map.add(-877631327);
		map.add(1910703821);
		map.add(-847410232);
		map.add(-502184080);

		map.printList();
		System.out.println(map.size());
		
		 * map.add(512); map.add(310); map.add(261); map.printList();
		 * 
		 * 
		 * /* map.add(14); map.add(10000); map.add(10); map.add(13); map.add(144);
		 * map.add(10000); map.add(1001); map.add(101); map.add(121); map.add(131);
		 * map.add(141); map.add(100001); map.add(101); map.add(131); map.add(1441);
		 * map.add(100001); map.add(1001); System.out.println(map.remove(55));
		 * 
		 * System.out.println(map.remove(13)); System.out.println(map.contains(12));
		 * System.out.println(map.remove(1441)); System.out.println(map.contains(1001));
		 */

		// map.printList();
		/*
		 * Scanner sc; File file = new File(
		 * "F:\\UT Dallas\\Courses\\Sem1-Fall18\\Implementation of Data Structures\\SP\\SP7\\test_file.txt"
		 * ); sc = new Scanner(file);
		 * 
		 * String operation = ""; long operand = 0; int modValue = 999983; long result =
		 * 0; Long returnValue = null;
		 * 
		 * while (!((operation = sc.next()).equals("End"))) { switch (operation) { case
		 * "Add": { operand = sc.nextLong(); if (map.add((int) operand)) { result =
		 * (result + 1) % modValue; } break; } case "Remove": { operand = sc.nextLong();
		 * if (map.remove((int) operand) != null) { result = (result + 1) % modValue; }
		 * break; } case "Contains": { operand = sc.nextLong(); if (map.contains((int)
		 * operand)) { result = (result + 1) % modValue; } break; }
		 * 
		 * } }
		 * 
		 * ;
		 */
	}

}
