import java.util.Arrays;

public final class ResizeableArrayBag<T> implements BagInterface<T> {

	private T[] bag;
	private int numEntries;
	private static final int DEFAULT_CAPACITY = 25;

	/** Creates an empty bag w/ initial default capacity*/
	public ResizeableArrayBag()
	{
		this (DEFAULT_CAPACITY);
	} // end default constructor

	/** Creates an empty bag with given initial capacity
		@param desiredCapacity The integer capacity desired */
	public ResizeableArrayBag(int desiredCapacity)
	{
		// The cast is safe because the new array contains null entries.
		@SuppressWarnings("unchecked")
		T[] tempBag = (T[])new Object[desiredCapacity]; // Unchecked cast
		bag = tempBag;
		numEntries = 0;
	} // end constructor


	/** Gets the current number of entries in this bag.
	 * @return  The integer number of entries currently in the bag. */
	public int getCurrentSize() 
	{
		return numEntries;
	}
	
	/** Sees whether this bag is empty.
	 * @return  True if the bag is empty, or false if not. */
	public boolean isEmpty() 
	{
		return numEntries == 0;
	} // end isEmpty

	// Returns true if the ResizeableArrayBag is full, false if not.
	private boolean isArrayFull() 
	{
		return numEntries >= bag.length;
	} // end isArrayFull
	
	/** Adds a new entry to this bag.
	 * @param newEntry  The object to be added as a new entry.
	 * @return  True if the addition is successful, or false if not. */
	public boolean add(T newEntry)
	{
		boolean result = true; // NOT SURE WHAT TO DO WITH BOOLEAN HERE...

		if (isArrayFull())
		{	// resize array and double the capacity
			bag = Arrays.copyOf(bag, 2 * bag.length);
		}

		bag[numEntries] = newEntry; // add new entry to bag.
		numEntries++; // step counter by 1.

		return result;
	}
	
	/** Removes one unspecified entry from this bag, if possible.
	 * @return  Either the removed entry, if the removal was successful, or null. */
	public T remove()
	{
		T result = null;
		if (numEntries > 0)
		{
			result = bag[numEntries - 1];
			bag[numEntries - 1] = null;
			numEntries--;
		}

		return result;
	}	// end remove
	
	/** Removes one occurrence of a given entry from this bag, if possible.
	 * @param anEntry  The entry to be removed.
	 * @return  True if the removal was successful, or false if not. */
	public boolean remove(T anEntry)
	{
		boolean result = true;
		int index;

		if (!contains(anEntry)) 
			result = false; // return false if entry not found
		else
		{ // removes entry and moves the last entry in array to the empty spot
			index = getIndexOf(anEntry);
			bag[index] = bag[numEntries - 1];
			bag[numEntries - 1] = null;
			numEntries--;
		}

		return result;
	}

	// Locates a given entry within the array bag.
	// Returns the index of the entry, if located, or -1 otherwise
	private int getIndexOf(T anEntry)
	{
		boolean found = false;
		int index = 0;
		int where = -1;

		while (!found && (index < numEntries))
		{
			if (anEntry.equals(bag[index]))
			{
				found = true;
				where = index;
			}
			else
				index++;
		} // end while loop

		return where;
	}

	
	/** Removes all entries from this bag. */
	public void clear()
	{
		while (!isEmpty())
			remove();
	}
	
	/** Counts the number of times a given entry appears in this bag.
	 * @param anEntry  The entry to be counted.
	 * @return  The number of times anEntry appears in the bag. */
	public int getFrequencyOf(T anEntry)
	{
		int frequency = 0;

		for (int i = 0; i < numEntries; i++)
		{
			if (anEntry.equals(bag[i]))
				frequency++;
		}

		return frequency;
	}

	
	/** Tests whether this bag contains a given entry.
	 * @param anEntry  The entry to find.
	 * @return  True if the bag contains anEntry, or false if not. */
	public boolean contains(T anEntry)
	{
		boolean result = false;

		if (getIndexOf(anEntry) >= 0)
			result = true;

		return result;
	}
	
	/** Retrieves all entries that are in this bag.
	 * @return  A newly allocated array of all the entries in the bag.Note: If the bag is empty, the returned array is empty. */
	public T[] toArray()
	{
		// The cast is safe because the new array contains null entries.
		@SuppressWarnings("unchecked")
		T[] result = (T[])new Object[numEntries]; // Unchecked cast
		for (int index = 0; index < numEntries; index++)
		{
			result[index] = bag[index];
		} // end for

		return result;
	}

	
	/** Creates a new bag with everything from this bag and a second bag.
	 * @param bag2  The other bag to be added in.
	 * @return New combined bag. */
	public BagInterface<T> union(BagInterface<T> bag2)
	{
		// create a union bag
		// fill union bag w/ bag 1
		// iterate through bag 2 and add to union bag

		BagInterface unionBag = new ResizeableArrayBag;

		for (int index = 0; index < numEntries; index++)
		{
			unionBag.add(bag[index]);
		}

		T[] bag2Array = bag2.toArray();

		for (int index = 0; index < bag2Array.length; index++)
		{
			unionBag.add(bag2Array[index]);
		}

		return unionBag;
	}
	

	/** Creates a new bag with only things that this bag and a second bag share.
	 * @param bag2  The other bag to be added in.
	 * @return New combined bag. */
	public BagInterface<T> intersection(BagInterface<T> bag2)
	{

		// interate through bag 1
			//add matches to intersection bag if they don't already exist in the intersection bag
				// add # entries based on lowest frequency

		//{a, a, b, c, a}

		//{d, a, e, f, a}

		//{a, a}

		BagInterface intersectionBag = new ResizeableArrayBag;

		for (int index = 0; index < numEntries; index++)
		{
			if (!intersectionBag.contains(bag[index]))
			{
				for (int freq = 0; freq < getFrequencyOf(bag[index]) && freq < bag2.getFrequencyOf(bag[index]); freq++)
				{
					intersectionBag.add(bag[index]);
				}
			}
		}

		return intersectionBag;
	}
	

	/** Creates a new bag with only things that would be left in this bag after removing anything shared between this bag and a second bag.
	 * @param bag2  The other bag to be added in.
	 * @return New combined bag. */
	public BagInterface<T> difference(BagInterface<T> bag2)
	{
		// iterate through bag 1
			// add entry to differenceBag if entry doesn't already exist in differenceBag
				// add # entries based on difference in frequency bag1-bag2


		BagInterface differenceBag = new ResizeableArrayBag;

		for (int index = 0; index < numEntries; index++)
		{
			if (!differenceBag.contains(bag[index]))
			{
				for (int freqDiff = 0; freqDiff < (getFrequencyOf(bag[index]) - bag2.getFrequencyOf(bag[index])); freqDiff++)
				{
					differenceBag.add(bag[index]);
				}
			}
		}

		return differenceBag;
	}


} // end ResizeableArrayBag class
