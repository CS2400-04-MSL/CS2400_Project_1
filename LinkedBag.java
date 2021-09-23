/**
A class of bags whose entries are stored in a chain of linked nodes.
The bag is never full.
*/

public final class LinkedBag<T> implements BagInterface<T>
{
	private Node firstNode; // Reference to first node
	private int numberOfEntries;

	public LinkedBag()
	{
		firstNode = null;
		numberOfEntries = 0;
	} // end default constructor


	/** Gets the current number of entries in this bag.
	 * @return  The integer number of entries currently in the bag. */
	public int getCurrentSize()
	{
		return numberOfEntries;
	}

	
	/** Sees whether this bag is empty.
	 * @return  True if the bag is empty, or false if not. */
	public boolean isEmpty()
	{
		return numberOfEntries == 0;
	}

	
	/** Adds a new entry to this bag. pg 183
	 * @param newEntry  The object to be added as a new entry.
	 * @return  True if the addition is successful, or false if not. */
	public boolean add(T newEntry)
	{
		Node newNode = new Node(newEntry);
		newNode.next = firstNode; 	// Make new node reference rest of chain
								  	// (firstNode is null if chain is empty)
		firstNode = newNode;		// New node is at beginning of chain
		numberOfEntries++;
		return true;
	}

	
	/** Removes one unspecified entry from this bag, if possible. Section 3.21
	 * @return  Either the removed entry, if the removal was successful, or null. */
	public T remove()
	{
		T result = null;
		if (firstNode != null)
		{
			result = firstNode.data;
			firstNode = firstNode.next; // Remove first node from chain
			numberOfEntries--;
		} // end if

		return result;
	}

	
	/** Removes one occurrence of a given entry from this bag, if possible. Section 3.23
	 * @param anEntry  The entry to be removed.
	 * @return  True if the removal was successful, or false if not. */
	public boolean remove(T anEntry)
	{
		boolean result = false;
		Node nodeN = getReferenceTo(anEntry);

		if (nodeN != null)
		{
			nodeN.data = firstNode.data; // Replace located entry with entry
			// in first node
			firstNode = firstNode.next; // Remove first node
			numberOfEntries--;
			result = true;
		} // end if

		return result;
	}

	// This private method needed for remove(T anEntry). Section 3.23
	// Locates a given entry within this bag.
	// Returns a reference to the node containing the entry, if located,
	// or null otherwise.
	private Node getReferenceTo(T anEntry)
	{
		boolean found = false;
		Node currentNode = firstNode;
		while (!found && (currentNode != null))
		{
			if (anEntry.equals(currentNode.data))
				found = true;
			else
				currentNode = currentNode.next;
		} // end while

		return currentNode;
	} // end getReferenceTo


	
	// Removes all entries from this bag. Section 3.24
	public void clear()
	{
		while (!isEmpty)
			remove();
	}

	
	/** Counts the number of times a given entry appears in this bag. Section 3.16
	 * @param anEntry  The entry to be counted.
	 * @return  The number of times anEntry appears in the bag. */
	public int getFrequencyOf(T anEntry)
	{
		int frequency = 0;
		int loopCounter = 0;
		Node currentNode = firstNode;

		while ((loopCounter < numberOfEntries) && (currentNode != null))
		{
			if (anEntry.equals(currentNode.data))
				frequency++;
			loopCounter++;
			currentNode = currentNode.next;
		} // end while

		return frequency;
	}

	
	/** Tests whether this bag contains a given entry. Section 3.17
	 * @param anEntry  The entry to find.
	 * @return  True if the bag contains anEntry, or false if not. */
	public boolean contains(T anEntry)
	{
		boolean found = false;
		Node currentNode = firstNode;

		while (!found && (currentNode != null))
		{
			if (anEntry.equals(currentNode.data))
				found = true;
			else
				currentNode = currentNode.next;
		} // end while

		return found;
	}

	
	/** Retrieves all entries that are in this bag. pg 184
	 * @return  A newly allocated array of all the entries in the bag.Note: If the bag is empty, the returned array is empty. */
	public T[] toArray()
	{
		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] result = (T[])new Object[numberOfEntries]; // Unchecked cast
		int index = 0;
		Node currentNode = firstNode;

		while ((index < numberOfEntries) && (currentNode != null))
		{
			result[index] = currentNode.data;
			index++;
			currentNode = currentNode.next;
		} // end while

		return result;

	} // end toArray

	


	/** Creates a new bag with everything from this bag and a second bag.
	 * @param bag2  The other bag to be added in.
	 * @return New combined bag. */
	public BagInterface<T> union(BagInterface<T> bag2)
	{
		// create a union bag
		// fill union bag w/ bag 1
		// iterate through bag 2 and add to union bag

		int index = 0;
		Node currentNode = firstNode;
		BagInterface unionBag = new LinkedBag;

		// fill union bag w/ bag 1
		// traverse through bag 1 and add them to unionBag
		while ((index < numberOfEntries) && (currentNode != null))
		{
			unionBag.add(currentNode.data);	// add entry to the linked list
			index++;
			currentNode = currentNode.next;	// traverse to next node
		} // end while

		// reset currentNode to point to bag2's first node
		index = 0;
		currentNode = bag2.firstNode;

		// fill union bag w/ bag 2
		while ((index < numberOfEntries) && (currentNode != null))
		{
			unionBag.add(currentNode.data);
			index++;
			currentNode = currentNode.next;
		} // end while

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

		int index = 0;
		Node currentNode = firstNode;
		BagInterface intersectionBag = new LinkedBag;

		// traverse through bag 1
		while ((index < numberOfEntries) && (currentNode != null))
		{	// check if entry exists in intersectionBag already
			if (!intersectionBag.contains(currentNode.data))
			{	// add # of entries based on lowest frequency between bag1 and bag2
				for (int freq = 0; freq < frequency(currentNode.data) && freq < bag2.frequency(currentNode.data); freq++)
				{
					intersectionBag.add(currentNode.data)
				}
			}
			index++;
			currentNode = currentNode.next;
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

		int index = 0;
		Node currentNode = firstNode;
		BagInterface differenceBag = new LinkedBag;

		// traverse through bag1
		while ((index < numberOfEntries) && (currentNode != null))
		{	// check if entry exists in differenceBag already
			if (!differenceBag.contains(currentNode.data))
			{	// add # of entries based on difference in frequency between bag1 and bag2
				for (int freqDiff = 0; freqDiff < (frequency(currentNode.data) - bag2.frequency(currentNode.data)); freqDiff++)
				{
					differenceBag.add(currentNode.data)
				}
			}
			index++;
			currentNode = currentNode.next;
		}

		return differenceBag;
	}




	private class Node // private inner class. Section 3.6
	{
		private T data; // Entry in bag
		private Node next; // Link to next node

		private Node(T dataPortion)
		{
			this(dataPortion, null);
		} // end constructor

		private Node(T dataPortion, Node nextNode)
		{
			data = dataPortion;
			next = nextNode;
		} // end constructor

	} // end Node

} // end LinkedBag